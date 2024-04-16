package net.etwig.webapp.dto.events;

import lombok.Getter;
import lombok.ToString;
import net.etwig.webapp.model.Event;
import net.etwig.webapp.model.EventGraphics;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@ToString
public class RecurringEventGraphicsPublicInfoDTO {

	// Graphics info
	private Long graphicsId;
	private Long assetId;

	// Event info
	private Long eventId;
	private int duration;
	private boolean allDayEvent;

	// Timing
	private String time;
	private String rrule;
	private String excludedDates;

	public RecurringEventGraphicsPublicInfoDTO(Event event, EventGraphics eventGraphics) {

		// Graphics info
		if(eventGraphics != null){
			this.graphicsId = eventGraphics.getId();
			this.assetId = eventGraphics.getAssetId();
		}
		
		// Event info
		this.eventId = event.getId();
		this.duration = event.getDuration();
		this.allDayEvent = event.isAllDayEvent();

		// Timing
		LocalDateTime eventDateTime = event.getStartTime();
		this.time = eventDateTime.format(DateTimeFormatter.ofPattern("HH:mm"));
		this.rrule = event.getRRule();
		this.excludedDates = event.getExcludedDates();

	}
}
