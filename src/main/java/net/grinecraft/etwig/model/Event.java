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
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
	private Long id;
	
	@Column(name = "is_recurring")
	private boolean isRecurring;
	
	//@Column(name = "organizer")
	//private int organizerID;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "portfolio", referencedColumnName = "id", insertable = false, updatable = false)
    private Portfolio portfolio;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "organizer", referencedColumnName = "id", insertable = false, updatable = false)
    private User user;
	
	@Column(name = "portfolio")
	private Long portfolioId;
	
	@Column(name = "organizer")
	private Long organizerId;
}
