/**
 	* eTWIG - The event management software for Griffin Hall.
 	* @copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
	* @license: MIT
	* @author: Steven Webb [xiaoancloud@outlook.com]
	* @website: https://etwig.grinecraft.net
	* @function: The class that mapping to "portfolio" table in the database.
	*/

package net.grinecraft.etwig.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "etwig_portfolio")
public class Portfolio {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
	private Long id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "color")
	private String color;
	
	@Column(name = "abbreviation")
	private String abbreviation;	
	
	@Column(name = "icon")
	private String icon;	
	
	@Column(name = "separated_calendar")
	private boolean separatedCalendar;	
}
