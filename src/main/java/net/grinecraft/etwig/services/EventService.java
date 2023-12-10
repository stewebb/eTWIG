package net.grinecraft.etwig.services;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.grinecraft.etwig.repository.EventRepository;
import net.grinecraft.etwig.repository.SingleTimeEventRepository;
import net.grinecraft.etwig.services.event.EventInfo;
import net.grinecraft.etwig.services.event.EventInfoFactory;
import net.grinecraft.etwig.util.DataIntegrityViolationException;
import net.grinecraft.etwig.util.DateUtils;
import net.grinecraft.etwig.util.type.DateRange;

@Service
public class EventService {

	//@Autowired
	//private EventRepository eventRepository;
	
	//@Autowired
	//private SingleTimeEventRepository singleTimeEventRepository;
	
	/**
	 * Get all events that happens in the month/week/day of the given date.
	 * @param givenDate
	 * @return The hashmap of all required events.
	 */
	
	public LinkedHashMap<Long,Object> findByDateRange(LocalDate givenDate, DateRange dateRange){
		
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
		
		// Step 2 
		
		/*
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
        }*/
        
        return null;
		
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
		EventInfoFactory eventInfoFactory = new EventInfoFactory();
		
		LinkedHashMap<String, Object> eventInfoRecurring = (eventInfoFactory.selectEvent(true)).getEventById(id, showAllDetails);
		LinkedHashMap<String,Object> eventInfoSingleTime = (eventInfoFactory.selectEvent(false)).getEventById(id, showAllDetails);
		System.out.println(eventInfoRecurring);
		System.out.println(eventInfoSingleTime);
		
		// The event is recurring
		if(eventInfoRecurring != null && eventInfoSingleTime == null) {
			event.put("id", id);
			event.put("isRecurring", true);
			event.put("detail", eventInfoRecurring); 
		}
		
		// The event is single time
		else if (eventInfoRecurring == null && eventInfoSingleTime != null) {
			event.put("id", id);
			event.put("isRecurring", false);
			event.put("detail", eventInfoSingleTime); 
		}
		
		// The event is both recurring and single time
		else if (eventInfoRecurring != null && eventInfoSingleTime != null) {
			throw new DataIntegrityViolationException("The event id=" + id + " is both recurring and single time. However, it must be either recurring or single time. ");
		}
		
		// The event is neither recurring nor single time. i.e., It doesn't exist at all!
		// The hidden else (do nothing).
		
		return event;
	}
	
}
