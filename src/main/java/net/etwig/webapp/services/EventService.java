/**
 	* eTWIG - The event management software for Griffin Hall.
 	* @copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
	* @license: MIT
	* @author: Steven Webb [xiaoancloud@outlook.com]
	* @website: https://etwig.grinecraft.net
	* @function: The services for all event-related options.
	*/

package net.etwig.webapp.services;

import jakarta.persistence.criteria.Predicate;
import net.etwig.webapp.dto.events.AddEditEventDTO;
import net.etwig.webapp.dto.events.EventDetailsDTO;
import net.etwig.webapp.dto.events.EventImportDTO;
import net.etwig.webapp.dto.user.CurrentUserDTOWrapper;
import net.etwig.webapp.dto.user.CurrentUserPermissionDTO;
import net.etwig.webapp.dto.user.CurrentUserPositionDTO;
import net.etwig.webapp.importer.EventImporter;
import net.etwig.webapp.importer.ExcelEventImporter;
import net.etwig.webapp.importer.ODSEventImporter;
import net.etwig.webapp.model.*;
import net.etwig.webapp.repository.EventGraphicsRepository;
import net.etwig.webapp.repository.EventOptionRepository;
import net.etwig.webapp.repository.EventRepository;
import net.etwig.webapp.repository.GraphicsRequestRepository;
import net.etwig.webapp.util.DateUtils;
import net.etwig.webapp.util.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EventService {

	@Autowired
	private EventRepository eventRepository;
	
	@Autowired
	private EventOptionRepository eventOptionRepository;

	@Autowired
	private BannerRequestService bannerRequestService;
	
	@Autowired
	private UserSessionService userSessionService;

	@Autowired
	private GraphicsRequestRepository graphicsRequestRepository;

	@Autowired
	private EventGraphicsRepository eventGraphicsRepository;

	/**
	 * Retrieves the details of an event by its ID and maps it to a DTO.
	 * <p>This method attempts to fetch an event from the {@code eventRepository} using the provided ID.
	 * If the event is found, it is transformed into an {@link EventDetailsDTO} using the DTO's constructor.
	 * If no event with the provided ID exists, the method returns {@code null}.</p>
	 *
	 * @param id The unique identifier of the event to be retrieved. Should be a positive number.
	 * @return An instance of {@link EventDetailsDTO} containing the event details, or {@code null} if no event is found.
	 */
	
	public EventDetailsDTO findById(long id) {
		return eventRepository.findById(id).map(EventDetailsDTO::new).orElse(null);
	}

	/**
	 * Retrieves a paginated list of event details based on a set of filtering criteria.
	 * This method constructs a dynamic query using the provided parameters and maps the resulting events
	 * to their Data Transfer Object (DTO) representation.
	 *
	 * @param startDate the earliest start date of the events to include (inclusive). This parameter defines
	 *                  the lower bound of the event start dates.
	 * @param endDate the latest start date of the events to include (inclusive). This parameter defines
	 *                the upper bound of the event start dates.
	 * @param recurring a Boolean indicating whether to filter by recurring events. If true, only recurring events are included.
	 * @param portfolioId the identifier for the portfolio to which the events are linked. This parameter is used to
	 *                    filter events associated with a specific portfolio.
	 * @param searchEvent a string used to perform a case-insensitive search by event name. This parameter allows
	 *                    for filtering events that contain the given substring in their names.
	 * @param pageable an object specifying the page request and sorting criteria. This parameter defines
	 *                 how results are paginated and sorted.
	 * @return a {@link Page} of {@link EventDetailsDTO} containing events that match the specified criteria,
	 *         sorted and paginated as requested.
	 */

	public Page<EventDetailsDTO> findByCriteria(
			LocalDate startDate,
			LocalDate endDate,
			Boolean recurring,
			Long portfolioId,
			String searchEvent,
			Pageable pageable
	) {
		Specification<Event> spec = eventCriteria(startDate, endDate, recurring, searchEvent, portfolioId);
		return eventRepository.findAll(spec, pageable).map(EventDetailsDTO::new);
	}

	/**
	 * Creates a {@link Specification<Event>} for querying events based on provided criteria.
	 * This method dynamically constructs predicates based on the presence and validity of the input parameters.
	 * It allows for flexible and dynamic database queries tailored to specific filtering needs.
	 *
	 * @param earliestStartDate the earliest start date for the events to filter (inclusive), setting the lower bound of the date range.
	 * @param latestStartDate the latest start date for the events to filter (inclusive), setting the upper bound of the date range.
	 * @param recurring specifies if only recurring events should be included. A value of true filters exclusively for recurring events.
	 * @param searchEvent a string to search within the event names. This is used for substring matching in a case-insensitive manner.
	 * @param portfolioId the identifier of the portfolio associated with the events. This filters the events linked to a specific portfolio.
	 * @return a {@link Specification<Event>} that can be used to filter events based on the provided criteria.
	 */

	private Specification<Event> eventCriteria(
			LocalDate earliestStartDate,
			LocalDate latestStartDate,
			Boolean recurring,
			String searchEvent,
			Long portfolioId
	) {
		return (root, query, criteriaBuilder) -> {
			Predicate finalPredicate = criteriaBuilder.conjunction();

			if (earliestStartDate != null && latestStartDate != null) {
				LocalDateTime earliestDateTime = earliestStartDate.atStartOfDay();
				LocalDateTime latestDateTime = latestStartDate.atTime(23, 59, 59);
				Predicate dateRangePredicate = criteriaBuilder.between(
						root.get("startTime"), earliestDateTime, latestDateTime);
				finalPredicate = criteriaBuilder.and(finalPredicate, dateRangePredicate);
			}

			if (recurring != null) {
				finalPredicate = criteriaBuilder.and(finalPredicate, criteriaBuilder.equal(root.get("recurring"), recurring));
			}

			if (portfolioId != null && portfolioId > 0) {
				finalPredicate = criteriaBuilder.and(
						finalPredicate, criteriaBuilder.equal(root.get("userRole").get("portfolioId"), portfolioId)
				);
			}

			if (searchEvent != null && !searchEvent.isEmpty()) {
				Predicate searchPredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + searchEvent.toLowerCase() + "%");
				finalPredicate = criteriaBuilder.and(finalPredicate, searchPredicate);
			}

			return finalPredicate;
		};
	}

	/**
	 * Adds or edits an event in the database.
	 * <p>
	 * This method handles both adding a new event and editing an existing event. It validates the user session,
	 * creates a new event or updates an existing one with the provided details, associates the selected options
	 * with the event, and optionally makes a banner request for the event.
	 * </p>
	 *
	 * @param eventInfo A {@link Map} containing the event details.
	 * @param currentEvent An {@link EventDetailsDTO} representing the current event details, if any.
	 */

	@SuppressWarnings("unchecked")
	public void editEvent(Map<String, Object> eventInfo, EventDetailsDTO currentEvent) throws Exception {

		// Add a new event to DB
		Long myPosition = userSessionService.validateSession().getPosition().getMyCurrentPositionId();
		AddEditEventDTO newEventDTO = new AddEditEventDTO(eventInfo, currentEvent, myPosition);
		Event addedEvent = eventRepository.save(newEventDTO.toEntity());
		
		// Add the selected options that associated with this new event.
		Long eventId = addedEvent.getId();
		ArrayList<Long> optionList = ListUtils.stringArrayToLongArray(
				ListUtils.stringToArrayList(eventInfo.get("properties").toString())
		);
		updateEventOptionBulky(eventId, optionList);
		
		// Make a banner request for this event (optional)
		Map<String, Object> graphics = (Map<String, Object>) eventInfo.get("graphics");
		if(graphics != null) {
			bannerRequestService.addRequest(
					eventId,
					addedEvent.getUserRoleId(),
					graphics.get("comments").toString(),
					DateUtils.safeParseDate(graphics.get("returningDate").toString(), "yyyy-MM-dd")
			);
		}
	}

	/**
	 * Updates the event options in bulk by first removing all existing options and then adding the new options.
	 *
	 * <p>This method is transactional, ensuring that either both the removal and addition of options are successful,
	 * or neither operation takes effect in case of an error.</p>
	 *
	 * @param eventId   The ID of the event whose options are to be updated.
	 * @param optionIds A list of option IDs to associate with the event.
	 */
	
	@Transactional
	private void updateEventOptionBulky(Long eventId, List<Long> optionIds) {

		// Remove all existing options associations for the event.
        List<EventOption> existingEventOptions = eventOptionRepository.findByIdEventId(eventId);
        eventOptionRepository.deleteAll(existingEventOptions);

        // Add new options associations for the event.
        List<EventOption> newEventOptions = optionIds.stream()
            .map(optionId -> new EventOption(new EventOptionKey(eventId, optionId)))
            .collect(Collectors.toList());
        eventOptionRepository.saveAll(newEventOptions);
    }

	/**
	 * Checks if the current user has edit permissions for a specified portfolio.
	 * <p>
	 * This method assesses the user's permissions based on their role and the portfolios they are associated with.
	 * There are three cases handled:
	 * <ol>
	 *     <li>System administrators: Always have edit permissions.</li>
	 *     <li>Users with events access: Have edit permissions if they are associated with the specified portfolio.</li>
	 *     <li>Other users: Do not have edit permissions.</li>
	 * </ol>
	 * </p>
	 *
	 * @param portfolio the portfolio for which edit permissions are being checked
	 * @return true if the current user has NO edit permissions for the specified portfolio, false otherwise
     */
	
	public boolean eventEditPermissionCheck(Portfolio portfolio) {
			
		// Get user authority
		CurrentUserDTOWrapper wrapper = userSessionService.validateSession();
		CurrentUserPermissionDTO access = wrapper.getPermission();
		CurrentUserPositionDTO position = wrapper.getPosition();

		// Case 1: System administrators have edit permission, regardless of which portfolio the user has.
		if(access.isAdminAccess()) {
			return false;
		}
		
		// Case 2: This user has events access, has edit view permission depends on the portfolio.
		else if (access.isEventsAccess()) {
			return !portfolio.getName().equals(position.getMyCurrentPosition().getPortfolioName());
        }
		
		// Case 3: Other users have no edit permission.
		else {
			return true;
		}
	}

	/**
	 * Deletes an event and all associated entities by the given event ID.
	 * <p>
	 * This method performs the following actions in order:
	 * <ol>
	 *     <li>Deletes all event options associated with the event ID.</li>
	 *     <li>Deletes all graphics requests associated with the event ID.</li>
	 *     <li>Deletes all event graphics associated with the event ID.</li>
	 *     <li>Deletes the event itself.</li>
	 * </ol>
	 * </p>
	 *
	 * @param eventId the ID of the event to be deleted
	 */

	public void deleteById (Long eventId) {
		eventOptionRepository.deleteAll(eventOptionRepository.findByIdEventId(eventId));
		graphicsRequestRepository.deleteByEventId(eventId);
		eventGraphicsRepository.deleteByEventId(eventId);
		eventRepository.deleteById(eventId);
	}






	public Map<Integer, String> importEvents(MultipartFile file, String fileType, Long role) throws Exception {

		// Decide the reader type (Factory pattern)
		InputStream inputStream = file.getInputStream();
		EventImporter eventImporter = "xlsx".equalsIgnoreCase(fileType) ? new ExcelEventImporter() : new ODSEventImporter();

		// Read file and add role
		List<EventImportDTO> importedEvents = eventImporter.read(inputStream);
		//importedEvents.forEach(obj -> obj.setOrganizerRole(role));

		for(EventImportDTO eventImportDTO : importedEvents){
			eventImportDTO.setOrganizerRole(role);

			Event event = eventImportDTO.toEntity();
			eventRepository.save(event);
		}
		//Event

		//System.out.println(importedEvents);
		return eventImporter.status();
    }

}
