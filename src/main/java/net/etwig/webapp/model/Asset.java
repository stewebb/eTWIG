/**
 	* eTWIG - The event management software for Griffin Hall.
 	* @copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
	* @license: MIT
	* @author: Steven Webb [xiaoancloud@outlook.com]
	* @website: https://etwig.grinecraft.net
	* @function: The class that mapping to "asset" table in the database.
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
@Table(name = "etwig_asset")
public class Asset {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
	private Long id;
	
	@Column(name = "original_name")
	private String originalName;
	
	@Column(name = "stored_name")
	private String storedName;
	
	@Column(name = "size")
	private long size;
	
	@Column(name = "upload_time")
	private LocalDateTime uploadedTime;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "uploader", referencedColumnName = "id", insertable = false, updatable = false)
    private User uploader;
	
	@Column(name = "uploader")
	private Long uploaderId;
}
