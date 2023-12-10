package net.grinecraft.etwig.services.event;

import java.util.LinkedHashMap;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.grinecraft.etwig.model.Event;
import net.grinecraft.etwig.model.Portfolio;
import net.grinecraft.etwig.model.RecurringEvent;
import net.grinecraft.etwig.model.User;
import net.grinecraft.etwig.repository.RecurringEventRepository;
import net.grinecraft.etwig.util.DataIntegrityViolationException;
import net.grinecraft.etwig.util.NameUtils;

@Service
public class RecurringEventInfo implements EventInfo {
	
	@Autowired
	private RecurringEventRepository recurringEventRepository;

	/**
	 * Get all details related to a recurring event by it's id.
	 * @param id The id of that event.
	 * @param showAllDetails True to show all details, false to show brief information.
	 * @return A linkedHaskMap about the details of the event. If event doesn't exist, return null.
	 * @throws DataIntegrityViolationException If the violation of the data integrity is detected.
	 */
	
	@Override
	public LinkedHashMap<String, Object> getEventById(long id, boolean showAllDetails) {
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
			
		if(showAllDetails) {
			eventDetails.put("eventLocation", recurringEvent.getLocation());
			eventDetails.put("eventDescription", recurringEvent.getDescription());
			eventDetails.put("portfolioName", portfolio.getName());
			eventDetails.put("portfolioAbbreviation", portfolio.getAbbreviation());
			eventDetails.put("portfolioIcon", portfolio.getIcon());
			eventDetails.put("organizerName", NameUtils.nameMerger(user.getFirstName(), user.getMiddleName(), user.getLastName()));
		}		
		return eventDetails;
	}

}
