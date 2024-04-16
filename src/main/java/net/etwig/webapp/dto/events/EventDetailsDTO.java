package net.etwig.webapp.dto.events;

import java.time.LocalDateTime;

import lombok.*;
import net.etwig.webapp.dto.PositionDTO;
import net.etwig.webapp.model.Event;
import net.etwig.webapp.model.Portfolio;
import net.etwig.webapp.model.UserRole;

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
	private String excluded;
	
	/**
	 * Portfolio, organizer and role.
	 */
	
	private String OrganizerName;
	private Long userRoleId;
	private String positionName;
	private Long portfolioId;
	private String portfolioName;
	
	//private PositionDTO position;
	

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
		this.excluded = event.getExcludedDates();
		
		//this.position = new PositionDTO(event.getUserRole());
		UserRole organizerRole = event.getUserRole();
		this.userRoleId = organizerRole.getId();
		this.OrganizerName = organizerRole.getUser().getFullName();
		this.positionName = organizerRole.getPosition();
		
		Portfolio portfolio = organizerRole.getPortfolio();
		this.portfolioId = portfolio.getId();
		this.portfolioName = portfolio.getName();
	}

}
