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
import net.grinecraft.etwig.repository.SingTimeEventRepository;
import net.grinecraft.etwig.util.DataIntegrityViolationException;
import net.grinecraft.etwig.util.DateUtils;
import net.grinecraft.etwig.util.NameUtils;

@Service
public class EventService {
	
	@Autowired
	private SingTimeEventRepository singleTimeEventsRepository;
	
	@Autowired
	private EventRepository eventRepository;
	
	/**
	 * Get all events that happens in the week of the given date.
	 * @param givenDate
	 * @return The hashmap of all required events.
	 */
	
	public LinkedHashMap<Long,Object> findByDateRange(LocalDate givenDate){
		
		LocalDate currentMonday = DateUtils.findThisMonday(givenDate);
		LocalDate nextMonday = DateUtils.findNextMonday(givenDate);
		
		if(singleTimeEventsRepository == null) {
			return new LinkedHashMap<Long, Object>();
		}
		
        List<SingleTimeEvent> singleTimeEventsList = singleTimeEventsRepository.findByDateRange(currentMonday, nextMonday);
      
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
        	info.putAll(getEventDetailsById(eventId));
        	//System.out.println(info);
        	
        	allEvents.put(eventId, info);
        }
        
        return allEvents;
		
	}
	
	/*
	 * Get event portfolio and organizer by it's id.
	 */
	
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
	}
}
