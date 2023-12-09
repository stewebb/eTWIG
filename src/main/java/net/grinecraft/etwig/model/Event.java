package net.grinecraft.etwig.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "event")
public class Event {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
	private int id;
	
	@Column(name = "is_recurring")
	private boolean isRecurring;
	
	//@Column(name = "organizer")
	//private int organizerID;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "portfolio", referencedColumnName = "id")
    private Portfolio portfolio;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "organizer", referencedColumnName = "id")
    private User user;
}
