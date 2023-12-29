/**
	 * eTWIG - The event and banner management software for residential halls and student unions.
	 * @copyright: Copyright (c) 2024 Steven Webb, eTWIG developers [etwig@grinecraft.net]
	 * @license: MIT
	 * @author: Steven Webb [xiaoancloud@outlook.com]
	 * @website: https://etwig.grinecraft.net
	 * @function: The SingleTimeEvent model, mapping the "event_single_time" table in the database.
	 */

package net.grinecraft.etwig.model;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.*;
import net.grinecraft.etwig.util.DateUtils;
import net.grinecraft.etwig.util.type.EventTimeUnit;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "event_single_time")
public class SingleTimeEvent {
	
	@Id
    @Column(name = "id")
	private Long id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "location")
	private String location;
	
	@Column(name = "start_datetime")
	private LocalDateTime startDateTime;
	
	@Column(name = "duration")
	private int duration;
	
	@Column(name = "unit")
	private String unit;
	
	@Column(name = "override_recurring_event")
	private Integer overrideRecurringEvent;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id", referencedColumnName = "id")
    private Event event;
	
	public EventTimeUnit getUnit() {
		return  EventTimeUnit.fromString(this.unit);
	}
	
	public String getUnitAbbr() {
		return this.getUnit().toString();
	}
	
	public LocalDateTime getEndDateTime() {
		return DateUtils.calculateEndTime(this.getUnit(), this.startDateTime, this.duration);
	}
}
