package net.grinecraft.etwig.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import net.grinecraft.etwig.model.Event;

@NoArgsConstructor
@Getter
@ToString
public class EventDetailsDTO extends EventBasicInfoDTO{

	private String description;
	
	private String location;
	
	private boolean allDayEvent;
	
	private String rRule;
	
	private LocalDateTime createdTime;
	
	private LocalDateTime updatedTime;
	
	private Long organizerId;
	
	@Override
	public void setBasicInfo(Event event) {
		super.setBasicInfo(event);
		
		this.description = event.getDescription();
		this.location = event.getLocation();
		this.allDayEvent = event.isAllDayEvent();
		this.rRule = event.getRRule();
		this.createdTime = event.getCreatedTime();
		this.updatedTime = event.getUpdatedTime();
		this.organizerId = event.getOrganizerId();
		 
	}
}
