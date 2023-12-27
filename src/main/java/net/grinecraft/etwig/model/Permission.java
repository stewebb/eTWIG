/**
	 * eTWIG - The event and banner management software for residential halls and student unions.
	 * @copyright: Copyright (c) 2024 Steven Webb, eTWIG developers [etwig@grinecraft.net]
	 * @license: MIT
	 * @author: Steven Webb [xiaoancloud@outlook.com]
	 * @website: https://etwig.grinecraft.net
	 * @function: The Permission model, mapping the "permission" table in the database.
	 */

package net.grinecraft.etwig.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "permission")
public class Permission {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
	private int id;
	
	@Column(name = "name")
	private String name;
}
