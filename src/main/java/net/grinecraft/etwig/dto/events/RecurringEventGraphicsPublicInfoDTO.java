package net.grinecraft.etwig.dto.events;

import lombok.Getter;
import lombok.ToString;
import net.grinecraft.etwig.model.Event;
import net.grinecraft.etwig.model.EventGraphics;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@ToString
public class RecurringEventGraphicsPublicInfoDTO {

	// Graphics info
	private Long id;
	private Long assetId;

	// Event info
	private Long eventId;
	private int duration;
	private boolean allDayEvent;

	// Timing
	private String time;
	private String rrule;
	private String excludedDates;

	public RecurringEventGraphicsPublicInfoDTO(EventGraphics eventGraphics) {

		if(eventGraphics == null){
			return;
		}

		// Graphics info
		this.id = eventGraphics.getId();
		this.assetId = eventGraphics.getAssetId();
		
		// Event info
		Event event = eventGraphics.getEvent();
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
