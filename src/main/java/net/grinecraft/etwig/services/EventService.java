package net.grinecraft.etwig.services;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.grinecraft.etwig.model.Event;
import net.grinecraft.etwig.model.User;
import net.grinecraft.etwig.model.Portfolio;
import net.grinecraft.etwig.model.SingleTimeEvent;
import net.grinecraft.etwig.repository.EventRepository;
import net.grinecraft.etwig.repository.SingleTimeEventRepository;
import net.grinecraft.etwig.util.DataIntegrityViolationException;
import net.grinecraft.etwig.util.DateUtils;
import net.grinecraft.etwig.util.NameUtils;

@Service
public class EventService {

	@Autowired
	private EventRepository eventRepository;
	
	@Autowired
	private SingleTimeEventRepository singleTimeEventRepository;
	
	/**
	 * Get all events that happens in the week of the given date.
	 * @param givenDate
	 * @return The hashmap of all required events.
	 */
	
	public LinkedHashMap<Long,Object> findByDateRange(LocalDate givenDate){
		
		LocalDate currentMonday = DateUtils.findThisMonday(givenDate);
		LocalDate nextMonday = DateUtils.findNextMonday(givenDate);
		
		if(singleTimeEventRepository == null) {
			return new LinkedHashMap<Long, Object>();
		}
		
        List<SingleTimeEvent> singleTimeEventsList = singleTimeEventRepository.findByDateRange(currentMonday, nextMonday);
      
        LinkedHashMap<Long, Object> allEvents = new LinkedHashMap<>();
        for(SingleTimeEvent singleTimeEvents : singleTimeEventsList) {      	
        	//allPortfolios.put(portfolio.getPortfolioID(), portfolioObjectToMap(portfolio));
        	
        	Long eventId = Long.valueOf(singleTimeEvents.getId());
        	LinkedHashMap<String, Object> info = new LinkedHashMap<String, Object>();
        	info.put("eventId", eventId);
        	info.put("eventName", singleTimeEvents.getName());
        	info.put("eventLocation", singleTimeEvents.getLocation());
        	info.put("eventStartTime", singleTimeEvents.getStartDateTime());
        	info.put("eventDuration", singleTimeEvents.getDuration());
        	info.putAll(getEventDetailsById(eventId, false));
        	//System.out.println(info);
        	
        	allEvents.put(eventId, info);
        }
        
        return allEvents;
		
	}
	
	/*
	 * Get event portfolio and organizer by it's id.
	 */
	
	/*
	private LinkedHashMap<String, Object> getEventDetailsById(long id) {
		if(eventRepository == null) {
			return new LinkedHashMap<String, Object>();
		}
		Optional<Event> eventsOpt = eventRepository.findById(id);
		
		// Only proceed when event exists
		if (eventsOpt.isPresent()){
			Event events = eventsOpt.get();
			Portfolio portfolio = events.getPortfolio();
			User leader = events.getUser();
			
			// Cannot get portfolio and organizer. It shouldn't happen!
			if(portfolio == null) {
				throw new DataIntegrityViolationException("The portfolio of event id=" + events.getId() + " doesn't exist. PLease check the portfolio table.");
			}
			
			if(leader == null) {
				throw new DataIntegrityViolationException("The organizer of event id=" + events.getId() + " doesn't exist. PLease check the leader table.");
			}
			
			LinkedHashMap<String, Object> eventInfo = new LinkedHashMap<String, Object>();
			eventInfo.put("isRecurring", events.isRecurring());
			//eventInfo.put("portfolioId", portfolio.getPortfolioID());
			eventInfo.put("portfolioName", portfolio.getName());
			eventInfo.put("portfolioColor", portfolio.getColor());
			// TODO More portfolio details.
			
			//eventInfo.put("organizerId", leader.getUserID());
			eventInfo.put("organizerName", NameUtils.nameMerger(leader.getFirstName(), leader.getMiddleName(), leader.getLastName()));
			return eventInfo;
		}
		
		// Portfolio exists in event_single_time table, but doesn't exist in events table. It shouldn't happen!
		else {
			throw new DataIntegrityViolationException("The event of id=" + id + " exists in event_single_time table, but doesn't exist in events table. PLease check those two tables.");
		}
	}*/
	
	/**
	 * Get all details related to an event by it's id.
	 * @param id The id of that event.
	 * @param showAllDetails True to show all details, false to show brief information.
	 * @return A linkedHaskMap about the details of the event. If event doesn't exist, return null.
	 * @throws DataIntegrityViolationException If the violation of the data integrity is detected.
	 */
	
	public LinkedHashMap<String, Object> getEventDetailsById(long id, boolean showAllDetails) {
		LinkedHashMap<String, Object> eventDetails = new LinkedHashMap<String, Object>();
		
		// Step 1: Null check (to avoid NullPointerException on findById)
		if(eventRepository == null || singleTimeEventRepository == null) {
			return null;
		}
		
		// Step 2: Find in the event table (join with portfolio and users table).
		Optional<Event> eventsOpt = eventRepository.findById(id);
		
		// Step 2.1: Null check
		if (!eventsOpt.isPresent()){
			return null;
		}
		
		// Step 2.2: Gather required objects.
		Event events = eventsOpt.get();
		Portfolio portfolio = events.getPortfolio();
		User user = events.getUser();
			
		// Step 2.3: Date Integrity check
		if(portfolio == null) {
			throw new DataIntegrityViolationException("The portfolio of event id=" + events.getId() + " doesn't exist. PLease check the portfolio table.");
		}
		
		if(user == null) {
			throw new DataIntegrityViolationException("The organizer of event id=" + events.getId() + " doesn't exist. PLease check the leader table.");
		}
			
		// Step 2.4: Add all necessary data
		boolean isRecurring = events.isRecurring();
		eventDetails.put("isRecurring", isRecurring);
		eventDetails.put("portfolioColor", portfolio.getColor());
			
		// Step 2.5: Add all detailed data
		if(showAllDetails) {
			eventDetails.put("portfolioName", portfolio.getName());
			eventDetails.put("portfolioAbbreviation", portfolio.getAbbreviation());
			eventDetails.put("portfolioIcon", portfolio.getIcon());
			eventDetails.put("organizerName", NameUtils.nameMerger(user.getFirstName(), user.getMiddleName(), user.getLastName()));
		}
		// TODO Find all parents
		
		// Step 3: Find in the event_single_time or event recurring table.
		// Step 3(a): Recurring events
		if(isRecurring) {
			
		}
		
		// Step 3(b): Single time events
		else {
			Optional<SingleTimeEvent> singleTimeEventsOpt = singleTimeEventRepository.findById(id);
			if (!singleTimeEventsOpt.isPresent()){
				throw new DataIntegrityViolationException("The event id=" + events.getId() + " exists in event table but doesn't exist in event_single_time table.");
			}
			SingleTimeEvent singleTimeEvent = singleTimeEventsOpt.get();
			eventDetails.put("eventName", singleTimeEvent.getName());
			eventDetails.put("eventStartTime", singleTimeEvent.getStartDateTime());
			eventDetails.put("eventDuration", singleTimeEvent.getDuration());
			
			// Just output the overriding event id. Let the front-end to handle this.
			eventDetails.put("overRideRecurringEvent", singleTimeEvent.getOverrideRecurringEvent());
			
			if(showAllDetails) {
				eventDetails.put("eventLocation", singleTimeEvent.getLocation());
				eventDetails.put("eventDescription", singleTimeEvent.getDescription());
			}
		}
		
		
		return eventDetails;
		
		
	}
}
