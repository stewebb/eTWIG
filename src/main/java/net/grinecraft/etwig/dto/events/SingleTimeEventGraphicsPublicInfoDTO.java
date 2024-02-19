package net.grinecraft.etwig.dto.events;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import lombok.Getter;
import lombok.ToString;
import net.grinecraft.etwig.model.Event;
import net.grinecraft.etwig.model.EventGraphics;

@Getter
@ToString
public class SingleTimeEventGraphicsPublicInfoDTO {

	// Graphics info
	private Long id;
	private Long assetId;

	// Event info
	private Long eventId;
	private int duration;
	private boolean allDayEvent;
	private String name;
	private String portfolioColor;

	// Timing
	private String date;
	private String time;

	public SingleTimeEventGraphicsPublicInfoDTO(Event event, EventGraphics eventGraphics) {

		// Graphics info
		if(eventGraphics != null){
			this.id = eventGraphics.getId();
			this.assetId = eventGraphics.getAssetId();
		}

		// Event info
		this.eventId = event.getId();
		this.duration = event.getDuration();
		this.allDayEvent = event.isAllDayEvent();
		this.name = event.getName();
		this.portfolioColor = event.getUserRole().getPortfolio().getColor();

		// Timing
		LocalDateTime eventDateTime = event.getStartTime();
		this.date = eventDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		this.time = eventDateTime.format(DateTimeFormatter.ofPattern("HH:mm"));
	}
}
