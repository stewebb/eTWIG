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
@Table(name = "banner_request")
public class BannerRequest {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
	private Long id;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "event_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Event event;
	
	@Column(name = "event_id")
	private Long eventId;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "requestor", referencedColumnName = "id", insertable = false, updatable = false)
    private User requestor;
	
	@Column(name = "requestor")
	private Long requestorId;
	
	@Column(name = "request_comment")
	private String requestComment;
	
	@Column(name = "request_time")
	private LocalDateTime requestTime;
	
	@Column(name = "approved")
	private Boolean approved;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "approver", referencedColumnName = "id", insertable = false, updatable = false)
    private User approver;
	
	@Column(name = "approver")
	private Long approverId;
	
	@Column(name = "response_comment")
	private String responseComment;
	
	@Column(name = "response_time")
	private LocalDateTime responseTime;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "asset_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Asset asset;
	
	@Column(name = "asset_id")
	private Long assetId;
	
	@Column(name = "expect_date")
	private LocalDate expectDate;
}
