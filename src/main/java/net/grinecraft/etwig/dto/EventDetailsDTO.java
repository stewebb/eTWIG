package net.grinecraft.etwig.dto;

import java.time.LocalDateTime;

import lombok.*;
import net.grinecraft.etwig.model.Event;

@Getter
@ToString
public class EventDetailsDTO {

	/**
	 * Event basic information.
	 */
	
	private Long id;
	private String name;
	private String location;
	private String description;
	private LocalDateTime createdTime;
	private LocalDateTime updatedTime;
	
	/**
	 * Timing
	 */
	
	private boolean recurring;
	private boolean allDayEvent;
	private LocalDateTime startTime;
	private int duration;
	private String rRule;
	
	
	/**
	 * Portfolio, organizer and role.
	 */
	
	private PositionDTO position;
	

	public EventDetailsDTO(Event event) {
		
		// Event basic information.
		this.id = event.getId();
		this.name = event.getName();
		this.location = event.getLocation();
		this.description = event.getDescription();
		this.createdTime = event.getCreatedTime();
		this.updatedTime = event.getUpdatedTime();
		
		// Timing
		this.recurring = event.isRecurring();
		this.allDayEvent = event.isAllDayEvent();
		this.startTime = event.getStartTime();
		this.duration = event.getDuration();
		this.rRule = event.getRRule();
		
		this.position = new PositionDTO(event.getUserRole());
	}

}
