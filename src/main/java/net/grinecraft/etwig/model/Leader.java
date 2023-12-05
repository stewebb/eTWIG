package net.grinecraft.etwig.model;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "leader")
public class Leader {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
	private int userID;
	
	@Column(name = "first_name")
	private String firstName;
	
	@Column(name = "middle_name")
	private String middleName;
	
	@Column(name = "last_name")
	private String lastName;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "last_login")
	private LocalDateTime lastLogin;
	
	@Column(name = "position")
	private String position;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "portfolio", referencedColumnName = "id")
    private Portfolio portfolio;
}
