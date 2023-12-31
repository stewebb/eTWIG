/**
	 * eTWIG - The event and banner management software for residential halls and student unions.
	 * @copyright: Copyright (c) 2024 Steven Webb, eTWIG developers [etwig@grinecraft.net]
	 * @license: MIT
	 * @author: Steven Webb [xiaoancloud@outlook.com]
	 * @website: https://etwig.grinecraft.net
	 * @function: The TwigTemplate model, mapping the "twig_template" table in the database.
	 */

package net.grinecraft.etwig.model;

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
	private Long Id;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "portfolio_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Portfolio portfolio;
	
	@Column(name = "background_image")
	private Long backgroundImage;
}
