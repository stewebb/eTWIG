/**
	 * eTWIG - The event and banner management software for residential halls and student unions.
	 * @copyright: Copyright (c) 2024 Steven Webb, eTWIG developers [etwig@grinecraft.net]
	 * @license: MIT
	 * @author: Steven Webb [xiaoancloud@outlook.com]
	 * @website: https://etwig.grinecraft.net
	 * @function: The UserRole model, mapping the "user_role" table in the database.
	 */

package net.grinecraft.etwig.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "user_role")
public class UserRole {

	@EmbeddedId
    private UserRoleKey id;
	
	@Column(name = "position")
	private String position;	
	
}
