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
import net.etwig.webapp.dto.graphics.NewRequestDTO;
import net.etwig.webapp.dto.user.CurrentUserDTOWrapper;
import net.etwig.webapp.dto.user.CurrentUserPermissionDTO;
import net.etwig.webapp.dto.user.CurrentUserPositionDTO;
import net.etwig.webapp.importer.EventImporter;
import net.etwig.webapp.importer.ExcelEventImporter;
import net.etwig.webapp.importer.ODSEventImporter;
import net.etwig.webapp.model.*;
import net.etwig.webapp.repository.EventOptionRepository;
import net.etwig.webapp.repository.EventRepository;
import net.etwig.webapp.repository.GraphicsRequestRepository;
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
	private GraphicsRequestRepository graphicsRequestRepository;
	
	@Autowired
	private UserSessionService userSessionService;

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
	 * Retrieves a paginated list of event details that match the given criteria.
	 * This method constructs a dynamic query based on the specified parameters and
	 * maps the resulting events to their DTO representation.
	 *
	 * @param startDate the earliest start date of the events to include (inclusive)
	 * @param endDate the latest start date of the events to include (inclusive)
	 * @param recurring whether the events are recurring or not
	 * @param portfolioId the portfolio identifier to which the events are associated
	 * @param pageable the pagination information and sorting criteria
	 * @return a paginated {@link Page} of {@link EventDetailsDTO} matching the criteria
	 */

	public Page<EventDetailsDTO> findByCriteria(
			LocalDate startDate,
			LocalDate endDate,
			Boolean recurring,
			Long portfolioId,
			Pageable pageable) {
		Specification<Event> spec = eventCriteria(startDate, endDate, recurring, portfolioId);
		return eventRepository.findAll(spec, pageable).map(EventDetailsDTO::new);
	}

	/**
	 * Creates a {@link Specification} for querying events based on provided criteria.
	 * This method constructs predicates based on the presence and validity of the input parameters,
	 * allowing for flexible and dynamic database queries.
	 *
	 * @param earliestStartDate the earliest start date for the events to filter (inclusive)
	 * @param latestStartDate the latest start date for the events to filter (inclusive)
	 * @param recurring true if only recurring events should be included, false otherwise
	 * @param portfolioId the identifier of the portfolio to which the events must be linked
	 * @return a {@link Specification} that can be used to filter events according to the provided criteria
	 */

	private Specification<Event> eventCriteria(
			LocalDate earliestStartDate,
			LocalDate latestStartDate,
			Boolean recurring,
			Long portfolioId) {
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

			if (portfolioId != null) {
				finalPredicate = criteriaBuilder.and(
						finalPredicate, criteriaBuilder.equal(root.get("userRole").get("portfolioId"), portfolioId)
				);
			}

			return finalPredicate;
		};
	}


	/**
	 * Edit an event to the database
	 * @param eventInfo The event details.
	 * @throws Exception 
	 */
	
	@SuppressWarnings("null")
	public void editEvent(Map<String, Object> eventInfo, EventDetailsDTO currentEvent){

		Long myPosition = userSessionService.validateSession().getPosition().getMyCurrentPositionId();

		// Add event
		AddEditEventDTO newEventDTO = new AddEditEventDTO(eventInfo, currentEvent, myPosition);
		//System.out.println(newEventDTO);
		Event addedEvent = eventRepository.save(newEventDTO.toEntity());
		
		// Add options
		Long eventId = addedEvent.getId();

		//String selectedOprions = eventInfo.get("properties").toString();
		//System.out.println(selectedOprions.length());
		ArrayList<Long> optionList = ListUtils.stringArrayToLongArray(ListUtils.stringToArrayList(eventInfo.get("properties").toString()));
		updateEventOptionBulky(eventId, optionList);
		
		// Optional graphics requests
		Map<String, Object> graphics = (Map<String, Object>) eventInfo.get("graphics");
		if(graphics == null) {
			return;
		}
		
		// Make a request
		NewRequestDTO newRequest = new NewRequestDTO();
		newRequest.fromParam(eventId, addedEvent.getUserRoleId(), graphics.get("comments").toString(), graphics.get("returningDate").toString());
		BannerRequest modifiedRequest = graphicsRequestRepository.save(newRequest.toEntity());
		//Long requestId = modifiedRequest.getId();
		
		// Send an email to graphics managers
		//emailService.graphicsRequestNotification(new NewRequestEmailNotificationDTO(graphicsRequestService.findById(requestId)));
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
	 * @return true if the current user has edit permissions for the specified portfolio, false otherwise
     */
	
	public boolean eventEditPermissionCheck(Portfolio portfolio) {
			
		// Get user authority

		CurrentUserDTOWrapper wrapper = userSessionService.validateSession();
		CurrentUserPermissionDTO access = wrapper.getPermission();
		CurrentUserPositionDTO position = wrapper.getPosition();

		// Case 1: System administrators have edit permission, regardless of which portfolio the user has.
		if(access.isAdminAccess()) {
			return true;
		}
		
		// Case 2: This user has events access, has edit view permission depends on the portfolio.
		else if (access.isEventsAccess()) {
			return portfolio.getName().equals(position.getMyCurrentPosition().getPortfolioName());
        }
		
		// Case 3: Other users have no edit permission.
		else {
			return false;
		}
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
