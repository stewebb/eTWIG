/**
 	* eTWIG - The event management software for Griffin Hall.
 	* @copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
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
@Table(name = "option")
public class Option {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
	private Long id;
	
	@Column(name = "name")
	private String name;
	
	//@ManyToOne(cascade = CascadeType.ALL)
	//@JoinColumn(name = "belongs_to", referencedColumnName = "id", insertable = false, updatable = false)
    //private Property property;		// Read-only
	
	@Column(name = "belongs_to")
	private Long propertyId;
}
