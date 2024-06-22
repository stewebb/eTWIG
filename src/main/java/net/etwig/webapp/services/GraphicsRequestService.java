package net.etwig.webapp.services;

import java.util.Map;
import java.util.Optional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import net.etwig.webapp.dto.BannerRequestDetailsDTO;
import net.etwig.webapp.dto.graphics.*;
import net.etwig.webapp.model.EventGraphics;
import net.etwig.webapp.repository.EventGraphicsRepository;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import net.etwig.webapp.model.GraphicsRequest;
import net.etwig.webapp.repository.GraphicsRequestRepository;

@Service
public class GraphicsRequestService {

	@Autowired
	private GraphicsRequestRepository graphicsRequestRepository;

	@Autowired
	private EventGraphicsRepository eventGraphicsRepository;
	
	@Autowired
	private EmailService emailService;

	@Autowired
	private EntityManager entityManager;


	@Autowired
	private UserSessionService userSessionService;
	
	@Autowired
	private UserRoleService userRoleService;

	/**
	 * Retrieves a {@link GraphicsRequest} by its ID.
	 * <p>
	 * This method searches for a graphics request in the repository using the provided ID.
	 * If the request is found, it is returned; otherwise, the method returns {@code null}.
	 * This allows for straightforward handling of non-existent IDs by calling methods.
	 * </p>
	 *
	 * @param requestId The ID of the graphics request to find.
	 * @return The found {@link GraphicsRequest} or {@code null} if no request is found with the given ID.
	 */

	public GraphicsRequest findById(Long requestId) {
		return graphicsRequestRepository.findById(requestId).orElse(null);
	}

	/**
	 * Retrieves a {@link BannerRequestDetailsDTO} by its ID.
	 * <p>
	 * This method fetches a graphics request by ID and constructs a DTO with the details of the found request.
	 * The method leverages the {@link #findById(Long)} method to retrieve the underlying {@link GraphicsRequest}.
	 * If the graphics request is found, a new DTO is created and returned; otherwise, this method returns {@code null}.
	 * </p>
	 *
	 * @param requestId The ID of the graphics request to find.
	 * @return A {@link BannerRequestDetailsDTO} containing the details of the found graphics request,
	 *         or {@code null} if no request is found with the given ID.
	 */

	public BannerRequestDetailsDTO findByIdWithDTO(Long requestId) {
		GraphicsRequest graphicsRequest = this.findById(requestId);

		// Null check!
		if (graphicsRequest == null) {
			return null;
		}
		return new BannerRequestDetailsDTO(graphicsRequest);
	}

	/**
	 * Counts the number of entities in the database table associated with this User entity,
	 * based on the provided column and value.
	 *
	 * @param column the name of the column in the database table to be used for counting
	 * @param value the value to be matched in the specified column for counting entities
	 * @return the count of entities that match the provided column and value
	 * @throws IllegalArgumentException if the column name is null or empty, or if the value is null
	 */

	public Long countByColumn(String column, Object value) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> query = cb.createQuery(Long.class);
		Root<GraphicsRequest> root = query.from(GraphicsRequest.class);
		query.select(cb.count(root));
		query.where(cb.equal(root.get(column), value));
		return entityManager.createQuery(query).getSingleResult();
	}
	
	//public boolean hasPendingRequests(Long eventId) {
	//	return graphicsRequestRepository.countByApprovedIsNullAndEventId(eventId) > 0;
	//}
	
	public Page<PendingRequestsBasicInfoDTO> getPendingRequests(int page, int size) {

		// TODO USE NEW LIST API
		Pageable pageable = PageRequest.of(page, size);
		Page<GraphicsRequest> requests =  graphicsRequestRepository.findByApprovedIsNullOrderByExpectDateDesc(pageable);
		return requests.map(PendingRequestsBasicInfoDTO::new);
	}
	
	public Page<FinalizedRequestsBasicInfoDTO> getFinalizedRequests(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		Page<GraphicsRequest> requests =  graphicsRequestRepository.findByApprovedIsNotNullOrderByResponseTimeDesc(pageable);
		return requests.map(FinalizedRequestsBasicInfoDTO::new);
	}


	/**
	 * Retrieves a paginated list of banner requests based on the specified criteria.
	 * <p>
	 * This method constructs a {@link Specification} using the given event ID and approval status to filter the results.
	 * It then queries the {@link GraphicsRequestRepository} with this specification and the provided {@link Pageable} object
	 * to obtain a paginated result. Each {@link GraphicsRequest} found is then transformed into a {@link BannerRequestDetailsDTO} object.
	 *
	 * @param eventId The unique identifier of the event to filter by; can be {@code null} if filtering by event ID is not required.
	 * @param isApproved A {@link String} representing the approval status to filter the graphics requests;
	 *                   can be {@code null} if this filter is not required. Possible values are "approved", "pending", "rejected".
	 * @param pageable A {@link Pageable} instance containing pagination information.
	 * @return A {@link Page} of {@link BannerRequestDetailsDTO} objects representing the filtered list of graphics requests.
	 *         This can be empty if no matching requests are found, but never {@code null}.
	 */

	public Page<BannerRequestDetailsDTO> findRequestsByCriteria(Long eventId, String isApproved, Pageable pageable) {
		Specification<GraphicsRequest> spec = bannerRequestCriteria(eventId, isApproved);
		return graphicsRequestRepository.findAll(spec, pageable).map(BannerRequestDetailsDTO::new);
	}

	/**
	 * Generates a Specification object to define criteria for querying GraphicsRequest entities
	 * based on the provided eventId and approval status.
	 *
	 * @param eventId the ID of the event to filter the GraphicsRequest entities by, or null to ignore
	 * @param isApproved the approval status ("na" to ignore approval status, "true" for approved requests,
	 *                   "false" for unapproved requests)
	 * @return a Specification object representing the criteria for querying GraphicsRequest entities
	 */

	public Specification<GraphicsRequest> bannerRequestCriteria(Long eventId, String isApproved) {
		return (root, query, criteriaBuilder) -> {
			Predicate finalPredicate = criteriaBuilder.conjunction(); // Start with a conjunction (true).

			// Add condition for eventId if it is not null
			if (eventId != null) {
				finalPredicate = criteriaBuilder.and(finalPredicate, criteriaBuilder.equal(root.get("event").get("id"), eventId));
			}

			// If isApproved="na", return all results regardless of the approval status.
			if(!"na".equalsIgnoreCase(isApproved)){
				finalPredicate = criteriaBuilder.and(finalPredicate, criteriaBuilder.equal(root.get("approved"), BooleanUtils.toBooleanObject(isApproved)));
			}

			return finalPredicate;
		};
	}

	public PendingRequestsDetailsDTO getPendingRequestsById(@NonNull Long requestId) {
		
		// Get a specific request
		Optional<GraphicsRequest> requestOpt = graphicsRequestRepository.findById(requestId);
		if(requestOpt.isEmpty()) {
			return null;
		}
		
		// Only get **pending** requests.
		GraphicsRequest request = requestOpt.get();
		if(request.getApproved() != null) {
			return null;
		}

		return new PendingRequestsDetailsDTO(request);
	}
	
	/**
	 * Make a new graphics request.
	 * @param requestInfo
	 * @throws Exception 
	 */
	
	@SuppressWarnings("null")
	public Long addRequest(Map<String, Object> requestInfo) throws Exception {
		
		// New request
		NewRequestDTO newRequest = new NewRequestDTO();
		newRequest.fromMap(requestInfo);
		
		// Get the information back
		GraphicsRequest modifiedRequest = graphicsRequestRepository.save(newRequest.toEntity());
		return modifiedRequest.getId();
		
		
		//System.out.println(this.findById(requestId));
		
		// Send an email to graphics managers
		//emailService.graphicsRequestNotification(new NewRequestEmailNotificationDTO(this.findById(requestId)));
	}
	
	//public void addRequest 

	/**
	 * Make a decision for a graphics request, and store the information in the DB.
	 * @param currentRequest
	 * @param decisionInfo
	 * @throws Exception
	 */
	public void approveRequest(GraphicsRequest currentRequest, Map<String, Object> decisionInfo) throws Exception {

		ApproveRequestsDTO request = new ApproveRequestsDTO(
				currentRequest,
				decisionInfo,
				userSessionService.validateSession().getPosition().getMyCurrentPositionId()
		);

		// Update request info
		GraphicsRequest updatedRequest = request.toEntity();
		graphicsRequestRepository.save(updatedRequest);
		
		// Re-query the request data (to avoid some null values)
		updatedRequest = this.findById(updatedRequest.getId());

		// Need to set the approver info manually
		FinalizedRequestsDetailsDTO detail = new FinalizedRequestsDetailsDTO(updatedRequest);
		//detail.setApprover(userRoleService.findById(updatedRequest.getApproverRoleId()));

		// "Copy" the graphics to the "event_graphics" table.
		NewGraphicsDTO newGraphicsDTO = new NewGraphicsDTO();
		newGraphicsDTO.fromApproval(updatedRequest);
		EventGraphics eventGraphics = newGraphicsDTO.toEntity();
		eventGraphicsRepository.save(eventGraphics);
		
		// Send email
		//emailService.graphicsApprovalNotification(detail);

		emailService.bannerApprovalNotification(
				updatedRequest.getRequesterRole().getEmail(),
				updatedRequest.getRequesterRole().getUser().getEmail(),
		);
	}
}
