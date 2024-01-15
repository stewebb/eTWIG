/**
 	* eTWIG - The event management software for Griffin Hall.
 	* @copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
	* @license: MIT
	* @author: Steven Webb [xiaoancloud@outlook.com]
	* @website: https://etwig.grinecraft.net
	* @function: The TwigTemplate model, mapping the "twig_template" table in the database.
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
@Table(name = "twig_template")
public class TwigTemplate {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
	private Long id;
	
	@Column(name = "name")
	private String name;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "portfolio_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Portfolio portfolio;
	
	 @Column(name = "portfolio_id")
	private Long portfolioId;
	 
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
	
	@Column(name = "background")
	private String background;
	
	/**
	 * 	The common element for multiple fields and widgets.
	 * The accepted widgets: logo, title, week
	 * {
	 * 		"enabled" : ${ENABLED},
	 * 		"image" : ${IMAGE},
	 * 		"size" : ${SIZE},
	 * 		"position" : "${POSX}, ${POSY}"
	 * }
	 * 
	 * Where:
	 * ${ENABLED} is a boolean value.
	 * ${IMAGE} is the assetId in the asset table. It MUST be an image file.
	 * 
	 * ${SIZE} is the size of the widget, which is an integer between two numbers (user defined).
	 * The short side of the widget is a proportion of the short side of the TWIG canvas.
	 * Actual size = constrain(${SIZE}, MIN, MAX)
	 * If ${SIZE} < MIN,  ${SIZE} = MIN.
	 * If ${SIZE} > MAX, ${SIZE} = MAX.
	 * 
	 * ${POSX}, ${POSY} are the position of the widget, which are integers between 0 and 100.
	 * They are the proportion of the long side and short side of the TWIG canvas respectively.
	 * Actual poses = constrain(${POX}, 0, 100)
	 */
	
	@Column(name = "logo")
	private String logo;
	
	@Column(name = "title")
	private String title;
	
	@Column(name = "week")
	private String week;
	
	@Column(name = "available_from")
	private LocalDate availableFrom;
	
	@Column(name = "available_to")
	private LocalDate availableTo;
}
