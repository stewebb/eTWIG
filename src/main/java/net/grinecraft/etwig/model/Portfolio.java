/**
	 * eTWIG - The event and banner management software for residential halls and student unions.
	 * @copyright: Copyright (c) 2024 Steven Webb, eTWIG developers [etwig@grinecraft.net]
	 * @license: MIT
	 * @author: Steven Webb [xiaoancloud@outlook.com]
	 * @website: https://etwig.grinecraft.net
	 * @function: The Portfolio model, mapping the "portfolio" table in the database.
	 */

package net.grinecraft.etwig.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "portfolio")
public class Portfolio {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
	private Long Id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "color")
	private String color;
	
	@Column(name = "abbreviation")
	private String abbreviation;	
	
	@Column(name = "icon")
	private String icon;	
	
	@Column(name = "is_separated_calendar")
	private boolean isSeparatedCalendar;	
	
	@Column(name = "parent")
	private Integer parent;
}
