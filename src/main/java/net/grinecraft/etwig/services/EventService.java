/**
	 * eTWIG - The event and banner management software for residential halls and student unions.
	 * @copyright: Copyright (c) 2024 Steven Webb, eTWIG developers [etwig@grinecraft.net]
	 * @license: MIT
	 * @author: Steven Webb [xiaoancloud@outlook.com]
	 * @website: https://etwig.grinecraft.net
	 * @function: The services for all event-related options..
	 */

package net.grinecraft.etwig.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

import net.grinecraft.etwig.util.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.grinecraft.etwig.model.Event;
import net.grinecraft.etwig.model.Portfolio;
import net.grinecraft.etwig.model.RecurringEvent;
import net.grinecraft.etwig.model.SingleTimeEvent;
import net.grinecraft.etwig.model.User;
import net.grinecraft.etwig.repository.EventRepository;
import net.grinecraft.etwig.repository.RecurringEventRepository;
import net.grinecraft.etwig.repository.SingleTimeEventRepository;
import net.grinecraft.etwig.util.DataIntegrityViolationException;
import net.grinecraft.etwig.util.DateUtils;
import net.grinecraft.etwig.util.NameUtils;
import net.grinecraft.etwig.util.type.DateRange;
import net.grinecraft.etwig.util.type.EventTimeUnit;

@Service
public class EventService {

	@Autowired
	private EventRepository eventRepository;
	
	@Autowired
	private SingleTimeEventRepository singleTimeEventRepository;
	
	@Autowired
	private RecurringEventRepository recurringEventRepository;
	
	/**
	 * Get all details related to a single time event by it's id.
	 * @param id The id of that event.
	 * @return A linkedHaskMap about the details of the event. If event doesn't exist, return null.
	 * @throws DataIntegrityViolationException If the violation of the data integrity is detected.
	 */
	
	private SingleTimeEvent getSingleTimeEventById(long id) {
		//LinkedHashMap<String, Object> eventDetails = new LinkedHashMap<String, Object>();
		
		// Step 1: Null check (to avoid NullPointerException on findById)
		if(singleTimeEventRepository == null) {
			return null;
		}
		
		// Step 2: Find in the event table (join with portfolio and users table).
		Optional<SingleTimeEvent> singleTimeEventOptional = singleTimeEventRepository.findById(id);
		
		// Step 2.1: Null check
		if (!singleTimeEventOptional.isPresent()){
			return null;
		}
		
		// Step 2.2: Gather required objects and date integrity check
		SingleTimeEvent singleTimeEvent = singleTimeEventOptional.get();
		Event event = singleTimeEvent.getEvent();
		
		if(event == null) {
			throw new DataIntegrityViolationException("The event id=" + singleTimeEvent.getId() + " exists in event_single_time table but doesn't exist in event table.");
		}
		
		Portfolio portfolio = event.getPortfolio();
		User user = event.getUser();
		
		if(portfolio == null) {
			throw new DataIntegrityViolationException("The portfolio of event id=" + singleTimeEvent.getId() + " doesn't exist. PLease check the portfolio table.");
		}
		
		if(user == null) {
			throw new DataIntegrityViolationException("The organizer of event id=" + singleTimeEvent.getId() + " doesn't exist. PLease check the user table.");
		}
		
		//eventDetails.put("eventName", singleTimeEvent.getName());
		
		// Get time unit, and calculate end time
		//EventTimeUnit timeUnit = EventTimeUnit.fromString(singleTimeEvent.getUnit());
		//LocalDateTime startTime = singleTimeEvent.getStartDateTime();
		//int duration = singleTimeEvent.getDuration();
		//LocalDateTime endTime = DateUtils.calculateEndTime(timeUnit, startTime, duration);
		
		// Step 2.4: Add data
		
		/*
		eventDetails.put("eventName", singleTimeEvent.getName());
		eventDetails.put("eventStartTime", startTime);
		eventDetails.put("eventEndTime", endTime);
		eventDetails.put("timeUnit", timeUnit.toString());
		eventDetails.put("eventDuration", duration);
		eventDetails.put("eventLocation", singleTimeEvent.getLocation());

		// Just output the overriding event id. Let the front-end to handle this.
		eventDetails.put("overRideRecurringEvent", singleTimeEvent.getOverrideRecurringEvent());
		eventDetails.put("portfolioId", portfolio.getId());
		eventDetails.put("portfolioColor", portfolio.getColor());
			
		// Step 2.5: Add all detailed data
		if(true) {
			eventDetails.put("eventDescription", singleTimeEvent.getDescription());
			eventDetails.put("portfolioName", portfolio.getName());
			//eventDetails.put("portfolioAbbreviation", portfolio.getAbbreviation());
			eventDetails.put("portfolioIcon", portfolio.getIcon());
			//eventDetails.put("organizerId", portfolio.getIcon());
			eventDetails.put("organizerName", NameUtils.nameMerger(user.getFirstName(), user.getMiddleName(), user.getLastName()));
		}
		// TODO Find all parents
		*/
		return singleTimeEvent;
	}
	
	
	/**
	 * Get all details related to a recurring event by it's id.
	 * @param id The id of that event.
	 * @param showAllDetails True to show all details, false to show brief information.
	 * @return A linkedHaskMap about the details of the event. If event doesn't exist, return null.
	 * @throws DataIntegrityViolationException If the violation of the data integrity is detected.
	 */
	
	private LinkedHashMap<String, Object> getRecurringEventById(long id, boolean showAllDetails) {
		LinkedHashMap<String, Object> eventDetails = new LinkedHashMap<String, Object>();
		if(recurringEventRepository == null) {
			return null;
		}
		
		Optional<RecurringEvent> recurringEventOptional = recurringEventRepository.findById(id);
		if (!recurringEventOptional.isPresent()){
			return null;
		}
		
		RecurringEvent recurringEvent = recurringEventOptional.get();
		Event event = recurringEvent.getEvent();
		
		if(event == null) {
			throw new DataIntegrityViolationException("The event id=" + recurringEvent.getId() + " exists in event_recurring table but doesn't exist in event table.");
		}
		
		Portfolio portfolio = event.getPortfolio();
		User user = event.getUser();
		
		if(portfolio == null) {
			throw new DataIntegrityViolationException("The portfolio of event id=" + recurringEvent.getId() + " doesn't exist. PLease check the portfolio table.");
		}
		
		if(user == null) {
			throw new DataIntegrityViolationException("The organizer of event id=" + recurringEvent.getId() + " doesn't exist. PLease check the leader table.");
		}
		
		eventDetails.put("eventName", recurringEvent.getName());
		eventDetails.put("frequency", recurringEvent.getFrequency());
		eventDetails.put("eventAvailableFrom", recurringEvent.getAvailableFrom());
		eventDetails.put("eventAvailableTo", recurringEvent.getAvailableTo());
		eventDetails.put("eventDuration", recurringEvent.getDuration());
		eventDetails.put("portfolioColor", portfolio.getColor());
		eventDetails.put("eventLocation", recurringEvent.getLocation());

		if(showAllDetails) {
			eventDetails.put("eventDescription", recurringEvent.getDescription());
			eventDetails.put("portfolioName", portfolio.getName());
			eventDetails.put("portfolioAbbreviation", portfolio.getAbbreviation());
			eventDetails.put("portfolioIcon", portfolio.getIcon());
			eventDetails.put("organizerName", NameUtils.nameMerger(user.getFirstName(), user.getMiddleName(), user.getLastName()));
		}		
		return eventDetails;
	}
	
	/**
	 * Find the event detail by it's id.
	 * @param id The event id
	 * @param showAllDetails True show all event details. False show only the essential details.
	 * @return The event information, or empty LinkedHashMap if the event doesn't exist.
	 * @throws DataIntegrityViolationException when event is both both recurring and single time.
	 */
	
	public LinkedHashMap<String, Object> findById(long id, boolean showAllDetails) {
			
		LinkedHashMap<String, Object> event = new LinkedHashMap<String, Object>();
		SingleTimeEvent eventInfoSingleTime = getSingleTimeEventById(id);
		LinkedHashMap<String,Object> eventInfoRecurring = getRecurringEventById(id, showAllDetails);
		
		// The event is recurring
		if(eventInfoRecurring != null && eventInfoSingleTime == null) {
			event.put("exists", true);
			event.put("isRecurring", true);
			event.put("detail", eventInfoRecurring); 
		}
		
		// The event is single time
		else if (eventInfoRecurring == null && eventInfoSingleTime != null) {
			event.put("exists", true);
			event.put("isRecurring", false);
			event.put("detail", eventInfoSingleTime); 
		}
		
		// The event is both recurring and single time
		else if (eventInfoRecurring != null && eventInfoSingleTime != null) {
			throw new DataIntegrityViolationException("The event id=" + id + " is both recurring and single time. However, it must be either recurring or single time. ");
		}
		
		// The event is neither recurring nor single time. i.e., It doesn't exist at all!
		else {
			event.put("exists", false);
		}
		
		return event;
	}
	
	/**
	 * Get all events that happens in the month/week/day of the given date.
	 * @param givenDate A given date in LocalDate format
	 * @param dateRange Am enum that specifies what kinds of data range will be chosen. [Month, Week, Day]
	 * @return The hashmap of all required events.
	 */
	
	public LinkedHashMap<Long, Object> findByDateRange(LocalDate givenDate, DateRange dateRange){
		
		// Step 1: Date check
		LocalDate last = null;
		LocalDate next = null;
		
		if(dateRange == DateRange.MONTH) {
			last = DateUtils.findFirstDayOfThisMonth(givenDate);
			next = DateUtils.findFirstDayOfNextMonth(givenDate);
		}
		
		else if(dateRange == DateRange.WEEK) {
			last = DateUtils.findThisMonday(givenDate);
			next = DateUtils.findNextMonday(givenDate);
		}
		
		else {
			last = givenDate;
			next = DateUtils.findTomorrow(givenDate);
		}
		
		//if(singleTimeEventRepository == null) {
		//	return new LinkedHashMap<Long, Object>();
		//}
		
		// Step 2: Check all single time events in the given date range.
        List<SingleTimeEvent> singleTimeEventsList = singleTimeEventRepository.findByDateRange(last, next);
        LinkedHashMap<Long, Object> allEvents = new LinkedHashMap<>();
        for(SingleTimeEvent singleTimeEvents : singleTimeEventsList) {      	
        	Long id = singleTimeEvents.getId();
        	allEvents.put(id, getSingleTimeEventById(id));
        }
        
        return allEvents;
		
	}
	
	private void addSingleTimeEvent(Long eventId, LinkedHashMap<String, Object> eventInfo) {
		SingleTimeEvent newSingleTimeEvent = new SingleTimeEvent();
		System.out.println(eventId);
		
		newSingleTimeEvent.setId(eventId);
		newSingleTimeEvent.setName(eventInfo.get("name").toString());
		newSingleTimeEvent.setDescription(eventInfo.get("description").toString());
		newSingleTimeEvent.setLocation(eventInfo.get("location").toString());
		
		String eventStartTimeStr = eventInfo.get("startTime").toString();
		newSingleTimeEvent.setStartDateTime(DateUtils.safeParseDateTime(eventStartTimeStr, "yyyy-MM-dd hh:mm a"));
		newSingleTimeEvent.setDuration(Integer.parseInt(eventInfo.get("duration").toString()));
		newSingleTimeEvent.setUnit(eventInfo.get("timeUnit").toString());
		singleTimeEventRepository.save(newSingleTimeEvent);
	}
	
	public void addEvent(LinkedHashMap<String, Object> eventInfo) {
		
		boolean isRecurrment = BooleanUtils.toBoolean(eventInfo.get("isRecurrment").toString());
		
		// Insert details into the general event table.
		Event newEvent = new Event();
		newEvent.setRecurring(isRecurrment);
		newEvent.setPortfolioId(Long.parseLong(eventInfo.get("portfolio").toString()));
		newEvent.setOrganizerId(Long.parseLong(eventInfo.get("organizer").toString()));
		
		Long newEventId = eventRepository.save(newEvent).getId();
		System.out.println(newEventId);
		
		// Insert specific data (recurrent events)
		if(isRecurrment) {
			// TODO Recurrent Event
		}
		
		// Insert specific data (single time events)
		else {
			addSingleTimeEvent(newEventId, eventInfo);
		}
		
	}
	
}
