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
