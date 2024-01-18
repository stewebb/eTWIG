package net.grinecraft.etwig.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.ToString;
import net.grinecraft.etwig.model.Event;
import net.grinecraft.etwig.model.Portfolio;
import net.grinecraft.etwig.model.User;
import net.grinecraft.etwig.util.DataException;

@Getter
@ToString
public class BannerRequestEventInfoDTO {

	/*
	private long id;
	
	private boolean recurrent;
	
	private String name;
	
	private String location;
	
	private String description;
	
	private String organizer;
	
	private Portfolio portfolio;
	
	private LocalDateTime startTime;
	
	public BannerRequestEventInfoDTO(Event event, SingleTimeEvent singleTimeEvent, RecurringEvent recurringEvent) throws Exception {
		
		this.id = event.getId();
		this.recurrent = event.isRecurring();
		
		//  Event type check.
		if(recurrent && recurringEvent == null) {
			throw new DataException("The type of event with id=" + this.id + " is recurring. However, the event information cannot been found in the event_recurrent table.");
		}
		
		if(!recurrent && singleTimeEvent == null) {
			throw new DataException("The type of event with id=" + this.id + " is single time. However, the event information cannot been found in the event_single_time table.");
		}
		
		User organizer = event.getUser();
		this.organizer = NameUtils.nameMerger(organizer.getFirstName(), organizer.getMiddleName(), organizer.getLastName());
		this.portfolio = event.getPortfolio();
		
		//this.organizer = event.getUser().
		// Recurring events
		if(recurrent) {
			// TODO add attributes
		}
		
		
		// Single time events
		else {
			this.name = singleTimeEvent.getName();
			this.location = singleTimeEvent.getLocation();
			this.description = singleTimeEvent.getDescription();
			this.startTime = singleTimeEvent.getStartDateTime();
		}
	}
	
	*/
}
