package net.grinecraft.etwig.dto.events;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.ToString;
import net.grinecraft.etwig.dto.user.UserDTO;
import net.grinecraft.etwig.model.Event;
import net.grinecraft.etwig.model.Portfolio;
import net.grinecraft.etwig.model.UserRole;

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
	private UserDTO organizer;
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
		this.organizer = new UserDTO(userRole.getUser());
		this.portfolio = userRole.getPortfolio();
		this.userRoleId = userRole.getId();
		
	}
	
	
}
