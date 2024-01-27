/**
 	* eTWIG - The event management software for Griffin Hall.
 	* @copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
	* @license: MIT
	* @author: Steven Webb [xiaoancloud@outlook.com]
	* @website: https://etwig.grinecraft.net
	* @function: The class that mapping to "twig_template" table in the database.
	*/

package net.grinecraft.etwig.model;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "etwig_twig_template")
public class TwigTemplate {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
	private Long id;
	
	@Column(name = "name")
	private String name;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "portfolio", referencedColumnName = "id", insertable = false, updatable = false)
    private Portfolio portfolio;
	
	@Column(name = "portfolio")
	private Long portfolioId;
	 
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "creator_role", referencedColumnName = "id", insertable = false, updatable = false)
	private UserRole creatorRole;
		
	@Column(name = "creator_role")
	private Long creatorRoleId;
	
	/**
	 * The "background" column stores the style and content of the TWIG background in JSON format.
	 * {
	 * 		"enabled" : ${ENABLED},
	 * 		"mode" : "${MODE}",
	 * 		"value" : "${VALUE}"
	 * }
	 * 
	 * Where:
	 * ${ENABLED} is a boolean value.
	 * If ${ENABLED} is true, the background in this record will be displayed. 
	 * Otherwise, the default background will be displayed.
	 * 
	 * ${MODE} := color | image
	 * color means that the background is a solid color.
	 * image means that the background is an image.
	 * 
	 * If ${MODE} is color, ${VALUE} is the hexadecimal color code #xxxxxx. e.g., #FF0000 stands for red.
	 * Otherwise, ${VALUE} is the assetId in the asset table.
	 */
	
	@Column(name = "design")
	private String design;
	
	@Column(name = "available_from")
	private LocalDate availableFrom;
	
	@Column(name = "available_to")
	private LocalDate availableTo;
}
