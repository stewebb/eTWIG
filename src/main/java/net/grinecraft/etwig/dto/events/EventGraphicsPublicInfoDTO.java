package net.grinecraft.etwig.dto.events;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.ToString;
import net.grinecraft.etwig.model.Event;
import net.grinecraft.etwig.model.EventGraphics;

@Getter
@ToString
public class EventGraphicsPublicInfoDTO {

	private Long id;
	private Long assetId;
	
	// Event info
	private Long eventId;
	private LocalDateTime startTime;
	private int duration;
	private boolean allDayEvent;
	
	public EventGraphicsPublicInfoDTO(EventGraphics eventGraphics) {
		
		this.id = eventGraphics.getId();
		this.assetId = eventGraphics.getAssetId();
		
		Event event = eventGraphics.getEvent();
		this.eventId = event.getId();
		this.startTime = event.getStartTime();
		this.duration = event.getDuration();
		this.allDayEvent = event.isAllDayEvent();

	}
}
