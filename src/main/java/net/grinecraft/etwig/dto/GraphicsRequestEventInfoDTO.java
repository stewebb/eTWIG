package net.grinecraft.etwig.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.ToString;
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
	private String organizerName;
	private String organizerPosition;
	private String portfolioName;
	private String portfolioColor;
	
	public GraphicsRequestEventInfoDTO(Event event) throws Exception {
		
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
		Portfolio portfolio = userRole.getPortfolio();
		
		this.organizerPosition = userRole.getPosition();
		this.organizerName = userRole.getUser().getFullName();
		this.portfolioName = portfolio.getName();
		this.portfolioColor = portfolio.getColor();
		
	}
	
	
}
