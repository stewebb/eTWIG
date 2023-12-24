package net.grinecraft.etwig.model;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.*;

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
	
	//@Column(name = "password")
	//private String password;
	
	@Column(name = "last_login")
	private LocalDateTime lastLogin;
	
	//@ManyToOne(cascade = CascadeType.ALL)
	//@JoinColumn(name = "permission", referencedColumnName = "id")
    //private Permission permission;
}
