package net.grinecraft.etwig.dto.events;

import lombok.Data;
import lombok.ToString;
import net.grinecraft.etwig.util.BooleanUtils;
import net.grinecraft.etwig.util.DateUtils;

import java.time.LocalDateTime;

@Data
@ToString
public class EventImportDTO {

	private String name;
	private String location;
	private String description;
	private boolean allDayEvent;
	private LocalDateTime startDateTime;
	private LocalDateTime endDateTime;

	public void setName(String name){
		if(name == null || name.isEmpty()){
			throw new IllegalArgumentException("Event name must not be empty.");
		}
		this.name = name;
	}

	public void setAllDayEvent(String allDayEvent){
		this.allDayEvent = BooleanUtils.toBoolean(allDayEvent);
	}

	public void setStartDateTime(String startDateTime){
		this.startDateTime = DateUtils.safeParseDateTime(startDateTime, "yyyy-MM-dd HH:mm");
	}

	public void setEndDateTime(String endDateTime){
		this.endDateTime = DateUtils.safeParseDateTime(endDateTime, "yyyy-MM-dd HH:mm");
	}

}
