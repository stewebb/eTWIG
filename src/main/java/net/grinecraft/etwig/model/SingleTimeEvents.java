package net.grinecraft.etwig.model;

import jakarta.persistence.*;

@Entity
@Table(name = "events")
public class SingleTimeEvents {
	
	

	public SingleTimeEvents(int eventID, String name, String description, String location, String startDateTime, int duration) {
		super();
		this.eventID = eventID;
		this.name = name;
		this.description = description;
		this.location = location;
		this.startDateTime = startDateTime;
		this.duration = duration;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
	private int eventID;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "location")
	private String location;
	
	@Column(name = "start_datetime")
	private String startDateTime;
	
	@Column(name = "duration")
	private int duration;

	public int getEventID() {
		return eventID;
	}

	public void setEventID(int eventID) {
		this.eventID = eventID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getStartDateTime() {
		return startDateTime;
	}

	public void setStartDateTime(String startDateTime) {
		this.startDateTime = startDateTime;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	@Override
	public String toString() {
		return "SingleTimeEvents [eventID=" + eventID + ", name=" + name + ", description=" + description + ", location=" + location + ", startDateTime=" + startDateTime + ", duration=" + duration + "]";
	}
}
