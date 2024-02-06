package net.grinecraft.etwig.dto.events;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class EventImportDTO {

	private Long eventId;
	private String name;
	private String location;
	private String description;
	private boolean allDayEvent;
	
}
