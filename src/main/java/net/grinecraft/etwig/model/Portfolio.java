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
	private Long id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "color")
	private String color;
	
	@Column(name = "abbreviation")
	private String abbreviation;	
	
	@Column(name = "icon")
	private String icon;	
	
	@Column(name = "is_seperated_calendar")
	private boolean isSeperatedCalendar;	
	
	@Column(name = "parent")
	private Integer parent;
	
	//@ManyToOne
	//@JoinColumn(name="portfolio")
	//private Events events;

}
