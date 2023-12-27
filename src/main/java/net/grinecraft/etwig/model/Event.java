/**
	 * eTWIG - The event and banner management software for residential halls and student unions.
	 * @copyright: Copyright (c) 2024 Steven Webb, eTWIG developers [etwig@grinecraft.net]
	 * @license: MIT
	 * @author: Steven Webb [xiaoancloud@outlook.com]
	 * @website: https://etwig.grinecraft.net
	 * @function: The Event model, mapping the "event" table in the database.
	 */

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
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "portfolio", referencedColumnName = "id", insertable = false, updatable = false)
    private Portfolio portfolio;		// Read-only
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "organizer", referencedColumnName = "id", insertable = false, updatable = false)
    private User user;					// Read-only
	
	@Column(name = "portfolio")
	private Long portfolioId;
	
	@Column(name = "organizer")
	private Long organizerId;
}
