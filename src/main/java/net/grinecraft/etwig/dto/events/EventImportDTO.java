package net.grinecraft.etwig.dto.events;

import lombok.Data;
import lombok.ToString;
import net.grinecraft.etwig.model.Event;

import java.util.Date;

@Data
@ToString
public class EventImportDTO {

	private String name;
	private String location;
	private String description;
	private boolean allDayEvent;
	private Date startDateTime;
	private int duration;

	public void setName(String name){
		if(name == null || name.isEmpty()){
			throw new IllegalArgumentException("Event name must not be empty.");
		}
		this.name = name;
	}

	public void setDuration(Date startDateTime, Date endDateTime){
		this.duration = (int) ((endDateTime.getTime() - startDateTime.getTime()) / 60000);

		// Negative duration check
		if(this.duration <= 0){
			throw new IllegalArgumentException("The end time must after start time.");
		}
	}

	public Event toEntity(){
		Event event = new Event();
		event.setName(this.name);
		event.setLocation(this.location);
		event.setDuration(this.duration);
		//event.se
		return event;
	}

}
