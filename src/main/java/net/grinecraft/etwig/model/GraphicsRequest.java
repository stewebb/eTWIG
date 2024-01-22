/**
 	* eTWIG - The event management software for Griffin Hall.
 	* @copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
	* @license: MIT
	* @author: Steven Webb [xiaoancloud@outlook.com]
	* @website: https://etwig.grinecraft.net
	* @function: The class that mapping to "graphics_request" table in the database.
	*/

package net.grinecraft.etwig.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "etwig_graphics_request")
public class GraphicsRequest {
	
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
	@JoinColumn(name = "requester_role", referencedColumnName = "id", insertable = false, updatable = false)
    private UserRole requesterRole;
	
	@Column(name = "requester_role")
	private Long requesterRoleId;
	
	@Column(name = "request_comment")
	private String requestComment;
	
	@Column(name = "request_time")
	private LocalDateTime requestTime;
	
	@Column(name = "expect_date")
	private LocalDate expectDate;
	
	@Column(name = "approved")
	private Boolean approved;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "approver_role", referencedColumnName = "id", insertable = false, updatable = false)
    private UserRole approverRole;
	
	@Column(name = "approver_role")
	private Long approverRoleId;
	
	@Column(name = "response_comment")
	private String responseComment;
	
	@Column(name = "response_time")
	private LocalDateTime responseTime;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "asset", referencedColumnName = "id", insertable = false, updatable = false)
    private Asset asset;
	
	@Column(name = "asset")
	private Long assetId;
	

}
