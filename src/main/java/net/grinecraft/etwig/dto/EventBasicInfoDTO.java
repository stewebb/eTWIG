package net.grinecraft.etwig.dto;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import lombok.*;
import net.grinecraft.etwig.model.Event;

@Getter
@ToString
public class EventBasicInfoDTO {

	private Long id;
	private String name;
	private Set<LocalDateTime> startTimeSet;
	private int duration;
	private boolean recurring;
	private String portfolioColor;
	

	public EventBasicInfoDTO(Event event) {
		
		this.id = event.getId();
		this.name = event.getName();
		this.startTimeSet = new HashSet<LocalDateTime>();
		this.duration = event.getDuration();
		this.recurring = event.isRecurring();
		this.portfolioColor = event.getUserRole().getPortfolio().getColor();
	}
	
	public void setStartTime(LocalDateTime startTime) {
		startTimeSet.add(startTime);
	}
	
	public void setStartTime(Set<LocalDateTime> startTimeSet) {
		this.startTimeSet.addAll(startTimeSet);
	}

}
