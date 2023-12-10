package net.grinecraft.etwig.model;

import java.time.LocalDate;
import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "event_recurring")
public class RecurringEvent {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
	private int id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "location")
	private String location;
	
	@Column(name = "frequency")
	private String frequency;
	
	@Column(name = "duration")
	private int duration;
	
	@Column(name = "available_from")
	private LocalDate availableFrom;
	
	@Column(name = "available_to")
	private LocalDate availableTo;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id", referencedColumnName = "id")
    private Event event;
}
