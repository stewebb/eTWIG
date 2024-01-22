/**
 	* eTWIG - The event management software for Griffin Hall.
 	* @copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
	* @license: MIT
	* @author: Steven Webb [xiaoancloud@outlook.com]
	* @website: https://etwig.grinecraft.net
	* @function: The services for all event-related options.
	*/

package net.grinecraft.etwig.services;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;
import net.grinecraft.etwig.dto.GraphicsRequestEventInfoDTO;
import net.grinecraft.etwig.dto.SingleTimeEventBasicInfoDTO;
import net.grinecraft.etwig.dto.UserAccessDTO;
import net.grinecraft.etwig.dto.EventDetailsDTO;
import net.grinecraft.etwig.dto.RecurringEventBasicInfoDTO;
import net.grinecraft.etwig.model.Event;
import net.grinecraft.etwig.model.Portfolio;
import net.grinecraft.etwig.model.User;
import net.grinecraft.etwig.model.UserRole;
import net.grinecraft.etwig.repository.EventOptionRepository;
import net.grinecraft.etwig.repository.EventRepository;
import net.grinecraft.etwig.util.DateUtils;
import net.grinecraft.etwig.util.exception.DataException;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EventService {

	@Autowired
	private EventRepository eventRepository;
	
	@Autowired
	private UserRoleService userRoleService;
	
	@Autowired
	private EventOptionRepository eventOptionRepository;
	
	
	@Autowired
	private HttpSession session;
	
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
	
	public LinkedHashMap<Long, SingleTimeEventBasicInfoDTO> getMonthlySingleTimeEventsByDateRange(LocalDate givenDate) throws Exception{
		
		// Get the date boundary
		LocalDate last = DateUtils.findFirstDayOfThisMonth(givenDate);
		LocalDate next = DateUtils.findFirstDayOfNextMonth(givenDate);
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

		/*
		Set<RecurringEventBasicInfoDTO> recurringEventSet = new HashSet<RecurringEventBasicInfoDTO>();
		List<Event> events = eventRepository.findByRecurringTrue();
		
		for(Event event : events) {
			recurringEventSet.add(new RecurringEventBasicInfoDTO(event));
		}
		
		return recurringEventSet;
		*/
		
		
	}

	/**
	 * Add an event to the database
	 * @param eventInfo The event details.
	 */
	public void addEvent(LinkedHashMap<String, Object> eventInfo) {
		
		/*
		boolean isRecurrent = BooleanUtils.toBoolean(eventInfo.get("isRecurrent").toString());
		
		// Insert details into the general event table.
		Event newEvent = new Event();
		newEvent.setRecurring(isRecurrent);
		newEvent.setPortfolioId(Long.parseLong(eventInfo.get("portfolio").toString()));
		newEvent.setOrganizerId(Long.parseLong(eventInfo.get("organizer").toString()));
		
		Long newEventId = eventRepository.save(newEvent).getId();
		//System.out.println(newEventId);
		
		// Insert specific data (recurrent events)
		if(isRecurrent) {
			// TODO Recurrent Event
		}
		
		// Insert specific data (single time events)
		else {
			updateSingleTimeEvent(newEventId, eventInfo);
		}
		
		ArrayList<Long> optionList = ListUtils.stringArrayToLongArray(ListUtils.stringToArrayList(eventInfo.get("properties").toString()));
		updateEventOptionBulky(newEventId, optionList);
		
		*/
	}
	
	/**
	 * Edit an event to the database
	 * @param eventInfo The event details.
	 */
	
	public void editEvent(LinkedHashMap<String, Object> eventInfo) {
		
		/*
		boolean isRecurrent = BooleanUtils.toBoolean(eventInfo.get("isRecurrent").toString());
		Long eventId = Long.parseLong(eventInfo.get("eventId").toString());
		
		// Update specific data (recurrent events)
		if(isRecurrent) {
			// TODO Recurrent Event
		}
				
		// Insert specific data (single time events)
		else {
			updateSingleTimeEvent(eventId, eventInfo);
		}
		
		ArrayList<Long> optionList = ListUtils.stringArrayToLongArray(ListUtils.stringToArrayList(eventInfo.get("properties").toString()));
		updateEventOptionBulky(eventId, optionList);
		
		*/
	}
	
	/**
	 * Get and set resources, they are only used in this class.
	 * The access control modifiers are "private".
	 */
	
	/**
	 * Get all details of a single time event by it's id.
	 * @param id The id of that event.
	 * @return The event object. If event doesn't exist, return null.
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 * @throws DataException If the violation of the data integrity is detected.
	 */
	
	private LinkedHashMap<String, Object> getSingleTimeEventById(long id) throws Exception {
		return null;		
		
		/*
		if(singleTimeEventRepository == null) {
			return null;
		}
		
		// Find the event table (join with portfolio and users table).
		Optional<SingleTimeEvent> singleTimeEventOptional = singleTimeEventRepository.findById(id);
		if (!singleTimeEventOptional.isPresent()){
			return null;
		}
		
		// Gather required objects and data integrity check
		SingleTimeEvent singleTimeEvent = singleTimeEventOptional.get();
		dataIntegrityCheck(singleTimeEvent);
		
		LinkedHashMap<String, Object> details = new LinkedHashMap<String, Object>();
		
		// Add event info
		LinkedHashMap<String, Object> eventInfo = new LinkedHashMap<String, Object>();
		eventInfo.put("Id", singleTimeEvent.getId());
		eventInfo.put("name", singleTimeEvent.getName());
		eventInfo.put("description", singleTimeEvent.getDescription());
		eventInfo.put("location", singleTimeEvent.getLocation());
		eventInfo.put("startDateTime", singleTimeEvent.getStartDateTime());
		eventInfo.put("endDateTime", singleTimeEvent.getEndDateTime());
		eventInfo.put("duration", singleTimeEvent.getDuration());
		eventInfo.put("unit", singleTimeEvent.getUnitAbbr());
		details.put("details", eventInfo);
		
		// Add portfolio and user info
		details.put("portfolio", this.portfolioToMap(singleTimeEvent.getEvent().getPortfolio()));
		details.put("user", this.userInfo(singleTimeEvent.getEvent().getUser()));
		return details;
		
		*/
	}
	
	/**
	 * Get all details related to a recurring event by it's id.
	 * @param id The id of that event.
	 * @param showAllDetails True to show all details, false to show brief information.
	 * @return A linkedHashMap about the details of the event. If event doesn't exist, return null.
	 * @throws DataException If the violation of the data integrity is detected.
	 */
	
	private LinkedHashMap<String, Object> getRecurringEventById(long id) {
		// TODO
		return null;
	}
	
	/**
	 * Add a single time event to database
	 * @param eventId The id of the event
	 * @param eventInfo The event details
	 */
	
	private void updateSingleTimeEvent(Long eventId, LinkedHashMap<String, Object> eventInfo) {
		
		/*
		SingleTimeEvent newSingleTimeEvent = new SingleTimeEvent();
		
		newSingleTimeEvent.setId(eventId);
		newSingleTimeEvent.setName(eventInfo.get("name").toString());
		newSingleTimeEvent.setDescription(eventInfo.get("description").toString());
		newSingleTimeEvent.setLocation(eventInfo.get("location").toString());
		
		String eventStartTimeStr = eventInfo.get("startTime").toString();
		newSingleTimeEvent.setStartDateTime(DateUtils.safeParseDateTime(eventStartTimeStr, "yyyy-MM-dd hh:mm a"));
		newSingleTimeEvent.setDuration(Integer.parseInt(eventInfo.get("duration").toString()));
		
		// TODO Float number duration.
		newSingleTimeEvent.setUnit(eventInfo.get("timeUnit").toString());
		
		singleTimeEventRepository.save(newSingleTimeEvent);
		
		*/
	}
	
	/**
	 * Update event options bulky, by removing all all existing options first, and then add all new options.
	 * @param eventId The id of the event.
	 * @param optionIds A list with all options that associated for the event.
	 */
	
	@Transactional
	private void updateEventOptionBulky(Long eventId, List<Long> optionIds) {
		
		/*
		
		// Remove all existing options associations for the event.
        List<EventOption> existingEventOptions = eventOptionRepository.findByIdEventId(eventId);
        eventOptionRepository.deleteAll(existingEventOptions);

        // Add new options associations for the event.
        List<EventOption> newEventOptions = optionIds.stream()
            .map(optionId -> new EventOption(new EventOptionKey(eventId, optionId)))
            .collect(Collectors.toList());
        eventOptionRepository.saveAll(newEventOptions);
        
        */
    }
	
	/**
	 * Helper methods below, they are only used in this class.
	 * The access control modifiers are "private".
	 */
	
	/**
	 * Check the data integrity
	 * @param singleTimeEvent The singleTimeEvent object
	 * @throws DataException When data integrity has been violated.
	 */
	
	//private void dataIntegrityCheck(SingleTimeEvent singleTimeEvent) throws DataException {
		
		/**
		Event event = singleTimeEvent.getEvent();
		if(event == null) {
			throw new DataException("The event id=" + singleTimeEvent.getId() + " exists in event_single_time table but doesn't exist in event table.");
		}
		
		if(event.getPortfolio() == null) {
			throw new DataException("The portfolio of event id=" + singleTimeEvent.getId() + " doesn't exist. PLease check the portfolio table.");
		}
		
		if(event.getUser() == null) {
			throw new DataException("The organizer of event id=" + singleTimeEvent.getId() + " doesn't exist. PLease check the user table.");
		}
		*/
	//}
	
	/**
	 * Convert portfolio object to a LinkedHashMap
	 * @param portfolio The portfolio object
	 * @return The LinkedHashMap form of portfolio object.
	 * @throws IllegalAccessException | IllegalArgumentException
	 */
	
	private LinkedHashMap<String, Object> portfolioToMap(Portfolio portfolio) throws Exception{
		
		/*
		LinkedHashMap<String, Object> portfolioInfo = new LinkedHashMap<String, Object>();
		
		for (Field field : portfolio.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            portfolioInfo.put(field.getName(), field.get(portfolio));
        }

        return portfolioInfo;		
        
        */
		
		return null;
	}
	
	/**
	 * Convert user object to a LinkedHashMap, however, only the id and full name are picked.
	 * @param user The user object
	 * @return The LinkedHashMap form of user object.
	 */
	
	private LinkedHashMap<String, Object> userInfo(User user){
		LinkedHashMap<String, Object> userInfo = new LinkedHashMap<String, Object>();
		userInfo.put("Id", user.getId());
		userInfo.put("fullName", user.getFullName());
		return userInfo;
	}
	
	public boolean eventEditPermissionCheck(Portfolio portfolio) throws Exception {
			
		// Get user authority
		UserAccessDTO access = (UserAccessDTO) session.getAttribute("access");
		
		// Case 1: System administrators have edit permission, regardless of which portfolio the user has.
		if(access.isAdminAccess()) {
			return true;
		}
		
		// Case 2: This user has events access, has edit view permission depends on the portfolio.
		else if (access.isEventsAccess()) {

			// All portfolios that I have.
			Set<Portfolio> myPortfolios = userRoleService.getMyPortfolios();
			
			// Iterate my portfolios, if any of them matches the given portfolio, I have edit permission.
			for(Portfolio myPortfolio : myPortfolios) {
				if(myPortfolio.equals(portfolio)) {
					return true;
				}
			}
			
			// Otherwise I don't have edit permission.
			return false;
		}
		
		// Case 3: Other users have no edit permission.
		else {
			return false;
		}
	}

}
