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
	 * 	The "logo" column stores the style and content of the customer logo in JSON format.
	 * {
	 * 		"enabled" : ${ENABLED},
	 * 		"image" : "${IMAGE}",
	 * 		"size" : "${SIZE}",
	 * 		"position" : "${POSX}, ${POSY}"
	 * }
	 * 
	 * Where:
	 * ${ENABLED} is a boolean value.
	 * ${IMAGE} is the assetId in the asset table. It MUST be an image file.
	 * 
	 * ${SIZE} is the size of the logo, which is a float number between 0 and 100.
	 * It is a proportion of the short side of the TWIG canvas.
	 * If ${SIZE} <= 0 || ${SIZE} > 100, the TWIG will fails to load.
	 * 
	 * ${POSX}, ${POSY} are the position of the logo, which are float numbers between 0 and 100.
	 * They are the proportion of the long side and short side of the TWIG canvas respectively.
	 * If ${POSX}, ${POSY} <= 0 || ${POSX}, ${POSY} > 100, the TWIG will fails to load.
	 */
	
	@Column(name = "logo")
	private String logo;
	
	@Column(name = "available_from")
	private LocalDate availableFrom;
	
	@Column(name = "available_to")
	private LocalDate availableTo;
}
