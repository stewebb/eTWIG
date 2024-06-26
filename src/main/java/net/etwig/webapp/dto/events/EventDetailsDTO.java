package net.etwig.webapp.dto.events;

import java.time.LocalDateTime;

import lombok.*;
import net.etwig.webapp.model.Event;
import net.etwig.webapp.model.Portfolio;
import net.etwig.webapp.model.UserRole;

@Getter
@ToString
public class EventDetailsDTO {

	// Basic information.
	private final Long id;
	private final String name;
	private final String location;
	private final String description;
	private final LocalDateTime createdTime;
	private final LocalDateTime updatedTime;

	// Timing
	private final boolean recurring;
	private final boolean allDayEvent;
	private final LocalDateTime startTime;
	private final int duration;
	private final String rRule;
	private final String excluded;

	// Portfolio, organizer and position.
	private final String organizerName;
	private final Long userRoleId;
	private final String organizerPosition;
	private final Long portfolioId;
	private final String portfolioName;
	private final String portfolioColor;

	public EventDetailsDTO(Event event) {
		
		// Basic information.
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
		
		UserRole organizerRole = event.getUserRole();
		this.userRoleId = organizerRole.getId();
		this.organizerName = organizerRole.getUser().getFullName();
		this.organizerPosition = organizerRole.getPosition();
		
		Portfolio portfolio = organizerRole.getPortfolio();
		this.portfolioId = portfolio.getId();
		this.portfolioName = portfolio.getName();
		this.portfolioColor = portfolio.getColor();
	}
}
