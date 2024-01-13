/**
 	* eTWIG - The event management software for Griffin Hall.
 	* @copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
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
	
	@Column(name = "original_name")
	private String originalName;
	
	@Column(name = "stored_name")
	private String storedName;
	
	@Column(name = "last_modified")
	private LocalDateTime lastModified;
	
	@Column(name = "size")
	private int fileSize;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "editor", referencedColumnName = "id", insertable = false, updatable = false)
    private User user;
}
