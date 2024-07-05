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
import net.etwig.webapp.dto.user.CurrentUserDTOWrapper;
import net.etwig.webapp.dto.user.CurrentUserPermissionDTO;
import net.etwig.webapp.dto.user.CurrentUserPositionDTO;
import net.etwig.webapp.model.*;
import net.etwig.webapp.repository.EventGraphicsRepository;
import net.etwig.webapp.repository.EventOptionRepository;
import net.etwig.webapp.repository.EventRepository;
import net.etwig.webapp.repository.GraphicsRequestRepository;
import net.etwig.webapp.util.DateUtils;
import net.etwig.webapp.util.ListUtils;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
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

	// TODO permission check

	/**
	 * Imports events from a provided CSV file and stores them in the database.
	 * The method parses each row from the CSV as an event record, validating and converting data as needed.
	 * If a row is successfully parsed and the event is saved, the status map is updated with a null value for that row number.
	 * If any error occurs during parsing or saving, the error message is stored in the status map against the row number.
	 *
	 * @param file The multipart file containing the CSV data to be imported.
	 * @return A map where keys are row numbers from the CSV file and values are error messages (null if the import was successful).
	 * @throws Exception If there is a failure in reading the file or parsing its content.
	 * <p>
	 * CSV File Requirements:
	 * - Header with columns: Name, Location, Description, AllDayEvent, StartDateTime, EndDateTime
	 * - Name: Non-empty string (Event name must not be empty).
	 * - Location, Description: Strings, no validation applied.
	 * - AllDayEvent: Boolean, "true" for all day events, adjusts event start and end times to start of the day.
	 * - StartDateTime, EndDateTime: Must be in "yyyy-MM-dd HH:mm:ss" format. End time must be after start time.
	 * <p>
	 * Examples of processing:
	 * - If 'AllDayEvent' is true, the times for 'StartDateTime' and 'EndDateTime' are set to the start of their respective days.
	 * - Validation ensures event duration is at least one minute.
	 * - Events are saved with additional properties like created time, updated time, and user role ID, derived from the user session.
	 * <p>
	 * Error Handling:
	 * - If the 'Name' is empty or the date times are invalid or if the event duration is too short (less than 1 minute),
	 * an IllegalArgumentException is thrown.
	 * - All exceptions are caught and their messages are returned in the map associated with the row number.
	 */

	public Map<Long, String> importEvents(MultipartFile file) throws Exception {
		Map<Long, String> status = new HashMap<>();

		// Attempt to read and parse the CSV file
		Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8));
		Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().withTrim().parse(reader);

		for (CSVRecord record : records) {
			long rowNumber = record.getRecordNumber();
			try {

				// Event name (must not empty)
				String name = record.get("Name");
				if (name == null || name.isEmpty()) {
					throw new IllegalArgumentException("Event name must not be empty.");
				}

				// Event location, description (no checks)
				String location = record.get("Location");
				String description = record.get("Description");

				// All Day Event option (convert to Boolean)
				boolean allDayEvent = BooleanUtils.toBoolean(record.get("AllDayEvent"));

				// Event start date time
				LocalDateTime startDateTime = DateUtils.safeParseDateTime(record.get("StartDateTime"), "yyyy-MM-dd HH:mm:ss");
				if (startDateTime == null) {
					throw new IllegalArgumentException("Event start datetime is invalid.");
				}

				// Event end date time
				LocalDateTime endDateTime = DateUtils.safeParseDateTime(record.get("EndDateTime"), "yyyy-MM-dd HH:mm:ss");
				if (endDateTime == null) {
					throw new IllegalArgumentException("Event end datetime is invalid.");
				}

				// Set time to midnight for all-day events.
				if (allDayEvent) {
					startDateTime = startDateTime.toLocalDate().atStartOfDay();
					endDateTime = endDateTime.toLocalDate().atStartOfDay();
				}

				// Event duration check (must be a positive number)
				long durationSeconds = Duration.between(startDateTime, endDateTime).getSeconds();
				if (durationSeconds < 60) {
					throw new IllegalArgumentException("The end time must after start time.");
				}

				// Create a new object for event entity
				Event event = new Event();
				event.setName(name);
				event.setLocation(location);
				event.setDescription(description);
				event.setUserRoleId(userSessionService.validateSession().getPosition().getMyCurrentPositionId());
				event.setCreatedTime(LocalDateTime.now());
				event.setUpdatedTime(LocalDateTime.now());
				event.setAllDayEvent(allDayEvent);
				event.setStartTime(startDateTime);
				event.setDuration((int) (durationSeconds / 60));
				event.setRecurring(false);

				// Save the new event into DB.
				eventRepository.save(event);
				status.put(rowNumber, null);
			}

			// Record any errors and return them to frontend.
			catch (Exception e) {
				status.put(rowNumber, e.getMessage());
			}
		}

		return status;
	}

}
