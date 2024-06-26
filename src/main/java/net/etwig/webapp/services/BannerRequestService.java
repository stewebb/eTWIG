package net.etwig.webapp.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import net.etwig.webapp.dto.graphics.BannerRequestDetailsDTO;
import net.etwig.webapp.dto.graphics.*;
import net.etwig.webapp.model.Asset;
import net.etwig.webapp.model.Event;
import net.etwig.webapp.model.UserRole;
import net.etwig.webapp.repository.EventGraphicsRepository;
import net.etwig.webapp.repository.EventRepository;
import net.etwig.webapp.repository.UserRoleRepository;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import net.etwig.webapp.model.BannerRequest;
import net.etwig.webapp.repository.GraphicsRequestRepository;

@Service
public class BannerRequestService {

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
	private AssetService assetService;

	@Autowired
	private EventRepository eventRepository;

	@Autowired
	private UserRoleRepository userRoleRepository;

	/**
	 * Retrieves a {@link BannerRequest} by its ID.
	 * <p>
	 * This method searches for a graphics request in the repository using the provided ID.
	 * If the request is found, it is returned; otherwise, the method returns {@code null}.
	 * This allows for straightforward handling of non-existent IDs by calling methods.
	 * </p>
	 *
	 * @param requestId The ID of the graphics request to find.
	 * @return The found {@link BannerRequest} or {@code null} if no request is found with the given ID.
	 */

	public BannerRequest findById(Long requestId) {
		return graphicsRequestRepository.findById(requestId).orElse(null);
	}

	/**
	 * Retrieves a {@link BannerRequestDetailsDTO} by its ID.
	 * <p>
	 * This method fetches a graphics request by ID and constructs a DTO with the details of the found request.
	 * The method leverages the {@link #findById(Long)} method to retrieve the underlying {@link BannerRequest}.
	 * If the graphics request is found, a new DTO is created and returned; otherwise, this method returns {@code null}.
	 * </p>
	 *
	 * @param requestId The ID of the graphics request to find.
	 * @return A {@link BannerRequestDetailsDTO} containing the details of the found graphics request,
	 *         or {@code null} if no request is found with the given ID.
	 */

	public BannerRequestDetailsDTO findByIdWithDTO(Long requestId) {
		BannerRequest bannerRequest = this.findById(requestId);

		// Null check!
		if (bannerRequest == null) {
			return null;
		}
		return new BannerRequestDetailsDTO(bannerRequest);
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
		Root<BannerRequest> root = query.from(BannerRequest.class);
		query.select(cb.count(root));
		query.where(cb.equal(root.get(column), value));
		return entityManager.createQuery(query).getSingleResult();
	}

	/**
	 * Retrieves a paginated list of banner requests based on the specified criteria.
	 * <p>
	 * This method constructs a {@link Specification} using the given event ID and approval status to filter the results.
	 * It then queries the {@link GraphicsRequestRepository} with this specification and the provided {@link Pageable} object
	 * to obtain a paginated result. Each {@link BannerRequest} found is then transformed into a {@link BannerRequestDetailsDTO} object.
	 *
	 * @param eventId The unique identifier of the event to filter by; can be {@code null} if filtering by event ID is not required.
	 * @param isApproved A {@link String} representing the approval status to filter the graphics requests;
	 *                   can be {@code null} if this filter is not required. Possible values are "approved", "pending", "rejected".
	 * @param pageable A {@link Pageable} instance containing pagination information.
	 * @return A {@link Page} of {@link BannerRequestDetailsDTO} objects representing the filtered list of graphics requests.
	 *         This can be empty if no matching requests are found, but never {@code null}.
	 */

	public Page<BannerRequestDetailsDTO> findRequestsByCriteria(Long eventId, String isApproved, Pageable pageable) {
		Specification<BannerRequest> spec = bannerRequestCriteria(eventId, isApproved);
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

	public Specification<BannerRequest> bannerRequestCriteria(Long eventId, String isApproved) {
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

	/**
	 * Creates a new banner request with the provided information.
	 * <p>
	 * This method constructs a new banner request, saves it to the database, and sends a notification email
	 * about the request. The email includes details about the request, requester, and the event.
	 * </p>
	 *
	 * @param eventId The ID of the event for which the banner request is made.
	 * @param requesterRole The ID of the requester's role.
	 * @param requestComment Comments related to the banner request.
	 * @param expectDate The expected date for the banner to be completed.
	 * @throws Exception if there is an error during the request creation or email sending process.
	 */
	
	public void addRequest (Long eventId, Long requesterRole, String requestComment, LocalDate expectDate) throws Exception {

		// Make a new request with essential information
		BannerRequest request = new BannerRequest();
		request.setEventId(eventId);
		request.setRequesterRoleId(requesterRole);
		request.setRequestComment(requestComment);
		request.setExpectDate(expectDate);
		request.setRequestTime(LocalDateTime.now());

		// Get the submitted request back with full information
		BannerRequest submittedRequest = graphicsRequestRepository.save(request);
		Event event = eventRepository.findById(eventId).orElse(null);
		UserRole userRole = userRoleRepository.findById(requesterRole).orElse(null);

		emailService.bannerRequestNotification(
				submittedRequest.getId(),
				(userRole == null) ? "Unknown user" : userRole.getUser().getFullName(),
				(event == null) ? "Unknown event" : event.getName(),
				submittedRequest.getRequestTime()
		);
	}

	/**
	 * Approves a graphics request and updates associated records.
	 * <p>
	 * This method processes the approval of a graphics request by updating its status,
	 * copying approved graphics to the event-specific graphics table, and sending a
	 * notification email to relevant stakeholders. It performs several operations:
	 * 1. Validates the user session and incorporates decision information into the approval.
	 * 2. Saves the updated request details to the repository.
	 * 3. Retrieves the updated request data to ensure freshness and avoid null values.
	 * 4. Copies the approved graphics into the 'event_graphics' table if the request was approved.
	 * 5. Sends an email notification about the approval.
	 *
	 * @param currentRequest The {@link BannerRequest} currently being processed.
	 * @param decisionInfo A map containing additional decision-making information used during the approval process.
	 * @throws Exception If there are issues during processing, such as database access failures, session validation failures, or email sending errors.
	 */

	public void approveRequest(BannerRequest currentRequest, Map<String, Object> decisionInfo) throws Exception {

		BannerRequestsApprovalDTO request = new BannerRequestsApprovalDTO(
				currentRequest,
				decisionInfo,
				userSessionService.validateSession().getPosition().getMyCurrentPositionId()
		);

		// Update request info
		BannerRequest updatedRequest = request.toEntity();
		graphicsRequestRepository.save(updatedRequest);
		
		// Re-query the request data (to avoid some null values)
		updatedRequest = this.findById(updatedRequest.getId());

		// "Copy" the graphics to the "event_graphics" table only when the request is approved.
		if(updatedRequest.getApproved()) {
			NewGraphicsDTO newGraphicsDTO = new NewGraphicsDTO();
			newGraphicsDTO.fromApproval(updatedRequest);
			eventGraphicsRepository.save(newGraphicsDTO.toEntity());
		}
		
		// Send email
		UserRole requesterRole = updatedRequest.getRequesterRole();
		Asset asset = assetService.getAssetDetailsById(updatedRequest.getAssetId());

		emailService.bannerApprovalNotification(
				requesterRole.getEmail(),
				requesterRole.getUser().getEmail(),
				updatedRequest.getApproved(),
				updatedRequest.getEventId(),
				updatedRequest.getEvent().getName(),
				updatedRequest.getResponseTime(),
				(asset == null) ? null : asset.getOriginalName(),
				assetService.getAssetContent(asset)
		);
	}

	/**
	 * Deletes a graphics request from the repository using the specified request ID.
	 * This method directly interacts with the {@code graphicsRequestRepository} to remove the specified entity.
	 * It is intended to be a straightforward, efficient way to eliminate records that are no longer needed.
	 * <p>
	 * Calling this method results in the immediate deletion of the graphics request identified by the {@code requestId}.
	 * It does not perform any additional checks or operations, making it crucial that this method is called
	 * in appropriate scenarios and is adequately secured within the application to prevent unauthorized deletions.
	 *
	 * @param requestId the unique identifier of the graphics request to be deleted.
	 *                  This ID must correspond to an existing record in the database.
	 * @throws IllegalArgumentException if {@code requestId} is {@code null}.
	 * @throws org.springframework.dao.EmptyResultDataAccessException if no entity with the given ID is found.
	 */

	public void deleteById(Long requestId) {
		graphicsRequestRepository.deleteById(requestId);
	}
}
