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

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
	private int userId;
	
	//@ManyToOne(cascade = CascadeType.ALL)
	//@JoinColumn(name = "user_id", referencedColumnName = "id")
    //private User user;
	
	//@ManyToOne(cascade = CascadeType.ALL)
	//@JoinColumn(name = "portfolio_id", referencedColumnName = "id")
    //private Portfolio portfolio;
	
	@Column(name = "position")
	private String position;	
	
}
