package net.grinecraft.etwig.dto;

import java.time.LocalTime;

import lombok.*;
import net.grinecraft.etwig.model.Event;

@Getter
@ToString
public class RecurringEventBasicInfoDTO {

	private Long id;
	private String name;
	private LocalTime eventTime;
	private int duration;
	private String portfolioColor;
	private String rRule;
	

	public RecurringEventBasicInfoDTO(Event event) {
		
		this.id = event.getId();
		this.name = event.getName();
		this.eventTime = event.getStartTime().toLocalTime();
		this.duration = event.getDuration();
		this.portfolioColor = event.getUserRole().getPortfolio().getColor();
		this.rRule = event.getRRule().trim();
	}


}
