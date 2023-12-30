/**
	 * eTWIG - The event and banner management software for residential halls and student unions.
	 * @copyright: Copyright (c) 2024 Steven Webb, eTWIG developers [etwig@grinecraft.net]
	 * @license: MIT
	 * @author: Steven Webb [xiaoancloud@outlook.com]
	 * @website: https://etwig.grinecraft.net
	 * @function: The Asset model, mapping the "asset" table in the database.
	 */

package net.grinecraft.etwig.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "asset")
public class Asset {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
	private Long id;
	
	@Column(name = "file_name")
	private String fileName;
	
	@Column(name = "last_modified")
	private LocalDateTime lastModified;
	
	//@Column(name = "editor")
	//private Long editorId;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "editor", referencedColumnName = "id", insertable = false, updatable = false)
    private User user;
}
