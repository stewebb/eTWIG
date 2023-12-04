package net.grinecraft.etwig.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "events")
public class Events {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
	private int eventID;
	
	@Column(name = "is_recurring")
	private String isRecurring;
	
	@Column(name = "organizer")
	private int organizerID;
	
	//@Column(name = "portfolio")
	//private int portfolioID;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "portfolio", referencedColumnName = "id")
    private Portfolio portfolio;
}
