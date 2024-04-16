package net.etwig.webapp.dto.events;

import java.time.LocalDateTime;
import lombok.*;
import net.grinecraft.etwig.model.Event;

@Getter
@ToString
public class SingleTimeEventBasicInfoDTO {

	private Long id;
	private String name;
	private LocalDateTime startTime;
	private int duration;
	private boolean allDayEvent;
	private String portfolioColor;
	

	public SingleTimeEventBasicInfoDTO(Event event) {
		
		this.id = event.getId();
		this.name = event.getName();
		this.startTime = event.getStartTime();
		this.duration = event.getDuration();
		this.allDayEvent = event.isAllDayEvent();
		this.portfolioColor = event.getUserRole().getPortfolio().getColor();
	}


}
