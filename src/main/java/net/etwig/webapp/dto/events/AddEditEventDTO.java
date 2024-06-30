package net.etwig.webapp.dto.events;

import java.time.LocalDateTime;
import java.util.Map;

import lombok.Getter;
import lombok.ToString;
import net.etwig.webapp.model.Event;
import net.etwig.webapp.util.DateUtils;
import org.apache.commons.lang3.BooleanUtils;

@Getter
@ToString
public class AddEditEventDTO {
	
	// Mode
	private final EventDetailsDTO currentEvent;
	
	// Basic info
	private final Long id;
	private final String name;
	private final String location;
	private final String description;
	private final Long userRoleId;
	private final LocalDateTime createdTime;
	private final LocalDateTime updatedTime;
	
	// Timing
	private final boolean recurring;
	private final boolean allDayEvent;
	private final LocalDateTime startTime;
	private final int duration;
	private String rRule;
	private String excluded;
	
	@SuppressWarnings("unchecked")
	public AddEditEventDTO(Map<String, Object> eventInfo, EventDetailsDTO currentEvent, Long myPosition) {

		// Mode
		this.currentEvent = currentEvent;
		
		// Basic info
		this.id = Long.parseLong(eventInfo.get("id").toString());
		this.name = eventInfo.get("name").toString();
		this.location = eventInfo.get("location").toString();
		this.description = eventInfo.get("description").toString();
		this.userRoleId = myPosition;
		
		// Edit mode, only update updated time.
		this.updatedTime = LocalDateTime.now();
		
		// Add mode, update both created time and updated time.
		if(currentEvent == null) {
			this.createdTime = LocalDateTime.now();
		}else {
			this.createdTime = currentEvent.getCreatedTime();
		}
		
		// Timing
		this.recurring = BooleanUtils.toBoolean(eventInfo.get("isRecurring").toString());
		this.allDayEvent = BooleanUtils.toBoolean(eventInfo.get("allDayEvent").toString());
		this.duration = Integer.parseInt(eventInfo.get("duration").toString());

		// Recurring events
		if(this.recurring) {
			Map<String, Object> recurring = (Map<String, Object>) eventInfo.get("recurring");
			String eventStartTimeStr = recurring.get("recurringTime").toString();
			
			this.startTime = DateUtils.safeParseDateTime(eventStartTimeStr, "yyyy-MM-dd'T'HH:mm:ss.SSSX");
			this.rRule = recurring.get("rrule").toString();
			
			Object eventExcluded = recurring.get("excluded");
			this.excluded = (eventExcluded == null) ? null : eventExcluded.toString();
		}
		
		// Single time events
		else {
			Map<String, Object> singleTime = (Map<String, Object>) eventInfo.get("singleTime");
			String eventStartTimeStr = singleTime.get("startDateTime").toString();
			this.startTime = DateUtils.safeParseDateTime(eventStartTimeStr, "yyyy-MM-dd'T'HH:mm:ss.SSSX");
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
		event.setCreatedTime(this.createdTime);

		// Timing
		event.setRecurring(this.recurring);
		event.setAllDayEvent(this.allDayEvent);
		event.setStartTime(this.startTime);
		event.setDuration(this.duration);
		event.setRRule(this.rRule);
		event.setExcludedDates(excluded);
		
		return event;
	}
}
