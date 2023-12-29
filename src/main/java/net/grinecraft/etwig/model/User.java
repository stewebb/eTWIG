/**
	 * eTWIG - The event and banner management software for residential halls and student unions.
	 * @copyright: Copyright (c) 2024 Steven Webb, eTWIG developers [etwig@grinecraft.net]
	 * @license: MIT
	 * @author: Steven Webb [xiaoancloud@outlook.com]
	 * @website: https://etwig.grinecraft.net
	 * @function: The User model, mapping the "users" table in the database.
	 * This model contains users' basic information like name. 
	 * It doesn't have data like password, which is modeled by UserAuth model, as they are only used in authentication purposes.
	 */

package net.grinecraft.etwig.model;

import jakarta.persistence.*;
import lombok.*;
import net.grinecraft.etwig.util.NameUtils;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
	private long id;
	
	@Column(name = "first_name")
	private String firstName;
	
	@Column(name = "middle_name")
	private String middleName;
	
	@Column(name = "last_name")
	private String lastName;
	
	@Column(name = "email")
	private String email;
	
	public String getFullName() {
		return NameUtils.nameMerger(this.firstName, this.middleName, this.lastName);
	}
}
