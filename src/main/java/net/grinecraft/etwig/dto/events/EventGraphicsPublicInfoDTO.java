package net.grinecraft.etwig.dto.events;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import lombok.Getter;
import lombok.ToString;
import net.grinecraft.etwig.model.Event;
import net.grinecraft.etwig.model.EventGraphics;

@Getter
@ToString
public class EventGraphicsPublicInfoDTO {

	// Graphics info
	private Long id;
	private Long assetId;
	
	// Event info
	private Long eventId;
	private String time;
	private int duration;
	private boolean allDayEvent;
	
	public EventGraphicsPublicInfoDTO(EventGraphics eventGraphics) {
		
		// Graphics info
		this.id = eventGraphics.getId();
		this.assetId = eventGraphics.getAssetId();
		
		// Event info
		Event event = eventGraphics.getEvent();
		this.eventId = event.getId();
		this.time = event.getStartTime().format(DateTimeFormatter.ofPattern("HH:mm"));
		this.duration = event.getDuration();
		this.allDayEvent = event.isAllDayEvent();

	}
}
