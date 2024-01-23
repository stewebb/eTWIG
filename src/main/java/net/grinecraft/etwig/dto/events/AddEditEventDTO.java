package net.grinecraft.etwig.dto.events;

import java.time.LocalDateTime;
import java.util.Map;

import lombok.Getter;
import lombok.ToString;
import net.grinecraft.etwig.model.Event;
import net.grinecraft.etwig.util.BooleanUtils;
import net.grinecraft.etwig.util.DateUtils;

@Getter
@ToString
public class AddEditEventDTO {
	
	// Mode
	private boolean isEdit;
	
	// Basic info
	private Long id;
	private String name;
	private String location;
	private String description;
	private Long userRoleId;
	private LocalDateTime createdTime;
	private LocalDateTime updatedTime;
	
	// Timing
	private boolean recurring;
	private boolean allDayEvent;
	private LocalDateTime startTime;
	private int duration;
	private String rRule;
	
	@SuppressWarnings("unchecked")
	public AddEditEventDTO(Map<String, Object> eventInfo) {
		
		// Mode
		this.isEdit = BooleanUtils.toBoolean(eventInfo.get("isEdit").toString());
		
		// Basic info
		this.id = Long.parseLong(eventInfo.get("id").toString());
		this.name = eventInfo.get("name").toString();
		this.location = eventInfo.get("location").toString();
		this.description = eventInfo.get("description").toString();
		this.userRoleId = Long.parseLong(eventInfo.get("eventRole").toString());
		
		// Edit mode, only update updated time.
		this.updatedTime = LocalDateTime.now();
		
		// Add mode, update both created time and updated time.
		if(!this.isEdit) {
			this.createdTime = LocalDateTime.now();
		}
		
		// Timing
		this.recurring = BooleanUtils.toBoolean(eventInfo.get("recurring").toString());
		this.allDayEvent = BooleanUtils.toBoolean(eventInfo.get("allDayEvent").toString());
		this.duration = Integer.parseInt(eventInfo.get("duration").toString());

		// Recurring events
		if(this.recurring) {
			Map<String, Object> recurring = (Map<String, Object>) eventInfo.get("recurring");
			String eventStartTimeStr = recurring.get("recurringTime").toString();
			this.startTime = DateUtils.safeParseDateTime(eventStartTimeStr, "yyyy-MM-dd hh:mm a");
			this.rRule = recurring.get("rrule").toString();
		}
		
		// Single time events
		else {
			Map<String, Object> singleTime = (Map<String, Object>) eventInfo.get("singleTime");
			String eventStartTimeStr = singleTime.get("startDateTime").toString();
			this.startTime = DateUtils.safeParseDateTime(eventStartTimeStr, "yyyy-MM-dd hh:mm a");
		}

	}
	
	public Event toEntity() {
		Event event = new Event();
		
		// Basic info
		event.setId(this.id);
		event.setName(this.name);
		event.setLocation(this.location);
		event.setDescription(this.description);
		event.setUserRoleId(this.userRoleId);
		event.setUpdatedTime(this.updatedTime);
				
		if(!this.isEdit) {
			event.setCreatedTime(this.createdTime);
		}
		
		// Timing
		event.setRecurring(this.recurring);
		event.setAllDayEvent(this.allDayEvent);
		event.setStartTime(this.startTime);
		event.setDuration(this.duration);
		event.setRRule(this.rRule);
		
		return event;
	}
}
