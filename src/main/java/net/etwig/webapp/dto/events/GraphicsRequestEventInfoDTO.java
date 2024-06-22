package net.etwig.webapp.dto.events;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.ToString;
import net.etwig.webapp.dto.user.CurrentUserBasicInfoDTO;
import net.etwig.webapp.model.Event;
import net.etwig.webapp.model.Portfolio;
import net.etwig.webapp.model.UserRole;

@Getter
@ToString
public class GraphicsRequestEventInfoDTO {

	// Event-related info
	private long id;
	private boolean recurrent;
	private String name;
	private String location;
	private String description;
	private LocalDateTime startTime;
	private int duration;
	
	// Organizer-related info
	private CurrentUserBasicInfoDTO organizer;
	private String organizerPosition;
	private Portfolio portfolio;
	private Long userRoleId;
	
	public GraphicsRequestEventInfoDTO(Event event) {
		
		// Event-related info
		this.id = event.getId();
		this.recurrent = event.isRecurring();
		this.name = event.getName();
		this.location = event.getLocation();
		this.description = event.getDescription();
		this.startTime = event.getStartTime();
		this.duration = event.getDuration();
		
		// Organizer-related info
		UserRole userRole = event.getUserRole();
		
		this.organizerPosition = userRole.getPosition();
		this.organizer = new CurrentUserBasicInfoDTO(userRole.getUser());
		this.portfolio = userRole.getPortfolio();
		this.userRoleId = userRole.getId();
		
	}
	
	
}
