/**
 	* eTWIG - The event management software for Griffin Hall.
 	* @copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
	* @license: MIT
	* @author: Steven Webb [xiaoancloud@outlook.com]
	* @website: https://etwig.grinecraft.net
	* @function: The class that mapping to "event_graphics" table in the database.
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
@Table(name = "etwig_event_graphics")
public class EventGraphics {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
	private Long id;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "event", referencedColumnName = "id", insertable = false, updatable = false)
    private Event event;
	
	@Column(name = "event")
	private Long eventId;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "operator_role", referencedColumnName = "id", insertable = false, updatable = false)
    private UserRole operatorRole;
	
	@Column(name = "operator_role")
	private Long operatorRoleId;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "asset", referencedColumnName = "id", insertable = false, updatable = false)
    private Asset asset;
	
	@Column(name = "asset")
	private Long assetId;
	
	@Column(name = "upload_time")
	private LocalDateTime uploadTime;
}
