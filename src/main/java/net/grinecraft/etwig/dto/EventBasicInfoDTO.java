package net.grinecraft.etwig.dto;

import java.time.LocalDateTime;

import lombok.*;
import net.grinecraft.etwig.model.Event;
import net.grinecraft.etwig.model.Portfolio;

@NoArgsConstructor
@Getter
@ToString
public class EventBasicInfoDTO {

	/**
	 * Basic information.
	 */
	
	private Long id;
	private String name;
	private boolean recurring;
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private Portfolio portfolio;
	
	/**
	 * Detailed information;
	 */
	
	private String description;
	private String location;
	private boolean allDayEvent;
	private String rRule;
	private LocalDateTime createdTime;
	private LocalDateTime updatedTime;
	private Long organizerId;
	
	public void setBasicInfo(Event event) {
		this.id = event.getId();
		this.name = event.getName();
		this.recurring = event.isRecurring();
		this.portfolio = event.getPortfolio();
	}
	
	public void setDetailedInfo(Event event) {
		this.description = event.getDescription();
		this.location = event.getLocation();
		this.allDayEvent = event.isAllDayEvent();
		this.rRule = event.getRRule();
		this.createdTime = event.getCreatedTime();
		this.updatedTime = event.getUpdatedTime();
		this.organizerId = event.getOrganizerId();
	}
	
	/**
	 * Uses in single time event, where the times can be retrieved from the database directly.
	 * @param event
	 */
	
	public void automaticallySetTiming(Event event) {
		this.startTime = event.getStartTime();
		this.endTime = event.getEndTime();
	}
	
	public void manuallySetTiming(LocalDateTime startTime, LocalDateTime endTime) {
		
	}

}
