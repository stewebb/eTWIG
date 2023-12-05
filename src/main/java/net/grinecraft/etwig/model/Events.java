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
	private boolean isRecurring;
	
	//@Column(name = "organizer")
	//private int organizerID;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "portfolio", referencedColumnName = "id")
    private Portfolio portfolio;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "organizer", referencedColumnName = "id")
    private Leader leader;
}
