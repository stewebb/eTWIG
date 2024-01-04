/**
	 * eTWIG - The event and banner management software for residential halls and student unions.
	 * @copyright: Copyright (c) 2024 Steven Webb, eTWIG developers [etwig@grinecraft.net]
	 * @license: MIT
	 * @author: Steven Webb [xiaoancloud@outlook.com]
	 * @website: https://etwig.grinecraft.net
	 * @function: The TwigTemplate model, mapping the "twig_template" table in the database.
	 */

package net.grinecraft.etwig.model;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.*;
import net.grinecraft.etwig.util.JSONUtils;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "twig_template")
public class TwigTemplate {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
	private Long id;
	
	//@OneToOne(cascade = CascadeType.ALL)
	//@JoinColumn(name = "portfolio_id", referencedColumnName = "id", insertable = false, updatable = false)
    //private Portfolio portfolio;
	
	 @Column(name = "portfolio_id")
	private Long portfolioId;
	 
	/**
	 * The "background" column stores the style and content of the TWIG background in JSON format.
	 * {
	 * 		"mode" : "${MODE}",
	 * 		"value" : "${VALUE}"
	 * }
	 * Where:
	 * ${MODE} := color | image
	 * color means that the background is a solid color.
	 * image means that the background is an image.
	 * 
	 * If ${MODE} is color, ${VALUE} is the hexadecimal color code #xxxxxx. e.g., #FF0000 stands for red.
	 * Otherwise, ${VALUE} is the assetId in the asset table.
	 */
	
	@Column(name = "background")
	private String background;
	
	@Column(name = "available_from")
	private LocalDate availableFrom;
	
	@Column(name = "available_to")
	private LocalDate availableTo;
}
