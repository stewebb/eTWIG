/**
 	* eTWIG - The event management software for Griffin Hall.
 	* @copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
	* @license: MIT
	* @author: Steven Webb [xiaoancloud@outlook.com]
	* @website: https://etwig.grinecraft.net
	* @function: The class that mapping to "user_role" table in the database.
	*/

package net.etwig.webapp.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "etwig_user_role")
public class UserRole {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
	private long id;
	
	@Column(name = "position")
	private String position;	
	
	@Column(name = "email")
	private String email;	
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "_user", referencedColumnName = "id", insertable = false, updatable = false)
	private User user;
		
	@Column(name = "_user")
	private Long userId;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "portfolio", referencedColumnName = "id", insertable = false, updatable = false)
    private Portfolio portfolio;
	
	@Column(name = "portfolio")
	private Long portfolioId;
	 
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "_role", referencedColumnName = "id", insertable = false, updatable = false)
	private Role role;
		
	@Column(name = "_role")
	private Long roleId;
}
