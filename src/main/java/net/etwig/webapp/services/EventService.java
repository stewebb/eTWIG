/**
 	* eTWIG - The event management software for Griffin Hall.
 	* @copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
	* @license: MIT
	* @author: Steven Webb [xiaoancloud@outlook.com]
	* @website: https://etwig.grinecraft.net
	* @function: The services for all event-related options.
	*/

package net.etwig.webapp.services;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import net.etwig.webapp.dto.events.*;
import net.etwig.webapp.dto.user.CurrentUserDTOWrapper;
import net.etwig.webapp.dto.user.CurrentUserPositionDTO;
import net.etwig.webapp.importer.EventImporter;
import net.etwig.webapp.importer.ExcelEventImporter;
import net.etwig.webapp.importer.ODSEventImporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.etwig.webapp.dto.graphics.NewRequestDTO;
import net.etwig.webapp.dto.user.CurrentUserPermissionDTO;
import net.etwig.webapp.model.Event;
import net.etwig.webapp.model.EventOption;
import net.etwig.webapp.model.EventOptionKey;
import net.etwig.webapp.model.GraphicsRequest;
import net.etwig.webapp.model.Portfolio;
import net.etwig.webapp.repository.EventOptionRepository;
import net.etwig.webapp.repository.EventRepository;
import net.etwig.webapp.repository.GraphicsRequestRepository;
import net.etwig.webapp.util.DateUtils;
import net.etwig.webapp.util.ListUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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
	 * Public entry of the event services.
	 * The access control modifiers are "public".
	 * @throws Exception 
	 */
	
	public GraphicsRequestEventInfoDTO findEventsForGraphicsRequestById(long eventId) throws Exception {
		Event event = (eventRepository == null) ? null :eventRepository.findById(eventId).orElse(null);
		return (event == null) ?  null : new GraphicsRequestEventInfoDTO(event);
	}
	
	/**
	 * Find the event detail by it's id.
	 * @param id
	 * @return EventDetailsDTO
	 */	
	
	public EventDetailsDTO findById(long id) {
		return eventRepository.findById(id).map(EventDetailsDTO::new).orElse(null);
	}
	
	public LinkedHashMap<Long, SingleTimeEventBasicInfoDTO> getSingleTimeEventsByDateRange(LocalDate givenDate, int calendarView) throws Exception{
		LocalDate last;
		LocalDate next;
		
		// Date boundary (monthly)
		if(calendarView > 0) {
			last = DateUtils.findFirstDayOfThisMonth(givenDate);
			next = DateUtils.findFirstDayOfNextMonth(givenDate);
		}
		
		// Date boundary (weekly)
		else if (calendarView == 0) {
			last = DateUtils.findThisMonday(givenDate);
			next = DateUtils.findNextMonday(givenDate);
		}
		
		// Date boundary (daily)
		else {
			last = givenDate;
			next = DateUtils.findTomorrow(givenDate);
		}
		
        LinkedHashMap<Long, SingleTimeEventBasicInfoDTO> allEvents = new LinkedHashMap<>();

		// Get all single time events in the given date range.
		List<Event> singleTimeEventList = eventRepository.findSingleTimeEventByDateRange(last, next);
        for(Event event : singleTimeEventList) {     
        	allEvents.put(event.getId(), new SingleTimeEventBasicInfoDTO(event));
        }
        return allEvents;
	}
	
	public List<RecurringEventBasicInfoDTO> getAllRecurringEvents(){
		if(eventRepository == null) {
			return null;
		}
		return eventRepository.findByRecurringTrue().stream().map(RecurringEventBasicInfoDTO::new).collect(Collectors.toList());
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
		GraphicsRequest modifiedRequest = graphicsRequestRepository.save(newRequest.toEntity());
		//Long requestId = modifiedRequest.getId();
		
		// Send an email to graphics managers
		//emailService.graphicsRequestNotification(new NewRequestEmailNotificationDTO(graphicsRequestService.findById(requestId)));
	}


    /**
	 * Update event options bulky, by removing all all existing options first, and then add all new options.
	 * @param eventId The id of the event.
	 * @param optionIds A list with all options that associated for the event.
	 */
	
	@SuppressWarnings("null")
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

			// All portfolios that I have.
			// Set<Portfolio> myPortfolios = userRoleService.getMyPortfolios();

			//Long eventPortfolioId = portfolio.getId();
			
			// Iterate my portfolios, if any of them matches the given portfolio, I have edit permission.
			//for(Portfolio myPortfolio : myPortfolios) {
			//	if(myPortfolio.equals(portfolio)) {
			//		return true;
			//	}
			//}

			//for(int p : position.){
			//
			//}

			// Check if the portfolio of an event match my current position.
            return portfolio.getName().equals(position.getMyCurrentPosition().getPortfolioName());
			
			// Otherwise I don't have edit permission.
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
