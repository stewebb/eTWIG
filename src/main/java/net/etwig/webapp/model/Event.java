/**
 	* eTWIG - The event management software for Griffin Hall.
 	* @copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
	* @license: MIT
	* @author: Steven Webb [xiaoancloud@outlook.com]
	* @website: https://etwig.grinecraft.net
	* @function: The class that mapping to "event" table in the database.
	*/

package net.etwig.webapp.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "etwig_event")
public class Event {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
	private Long id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "location")
	private String location;
	
	@Column(name = "start_time")
	private LocalDateTime startTime;
	
	@Column(name = "duration")
	private int duration;
	
	@Column(name = "all_day_event")
	private boolean allDayEvent;
	
	@Column(name = "recurring")
	private boolean recurring;
	
	@Column(name = "rrule")
	private String rRule;
	
	@Column(name = "created_time")
	private LocalDateTime createdTime;
	
	@Column(name = "updated_time")
	private LocalDateTime updatedTime;
	
	@Column(name = "excluded_dates")
	private String excludedDates;
	
	@OneToOne
	@JoinColumn(name = "user_role", referencedColumnName = "id", insertable = false, updatable = false)
    private UserRole userRole;
	
	@Column(name = "user_role")
	private Long userRoleId;
}
