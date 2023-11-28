package net.grinecraft.etwig.Model;
import jakarta.persistence.*;

@Entity
@Table(name = "events")
public class Events {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "event_id")
	private int eventID;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "location")
	private String location;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "start_time")
	private String startTime;
	
	@Column(name = "end_time")
	private String endTime;
	
	@Column(name = "user_id")
	private int userID;
	


	public Events() {
		
	}
	
	public Events(int eventID, String name, String location, String description, String startTime, String endTime, int userID) {
		this.eventID = eventID;
		this.name = name;
		this.location = location;
		this.description = description;
		this.startTime = startTime;
		this.endTime = endTime;
		this.userID = userID;
	}

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

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	@Override
	public String toString() {
		return "Events [eventID=" + eventID + ", name=" + name + ", location=" + location + ", description="
				+ description + ", startTime=" + startTime + ", endTime=" + endTime + ", userID=" + userID + "]";
	}

}
