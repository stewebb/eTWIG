package net.etwig.webapp.services;

import java.util.Map;
import java.util.Optional;

import jakarta.persistence.criteria.Predicate;
import net.etwig.webapp.dto.BannerRequestAPIDetailsDTO;
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
	
	//@Autowired
	//private EventService eventService;
	
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
	 * Retrieves a {@link BannerRequestAPIDetailsDTO} by its ID.
	 * <p>
	 * This method fetches a graphics request by ID and constructs a DTO with the details of the found request.
	 * The method leverages the {@link #findById(Long)} method to retrieve the underlying {@link GraphicsRequest}.
	 * If the graphics request is found, a new DTO is created and returned; otherwise, this method returns {@code null}.
	 * </p>
	 *
	 * @param requestId The ID of the graphics request to find.
	 * @return A {@link BannerRequestAPIDetailsDTO} containing the details of the found graphics request,
	 *         or {@code null} if no request is found with the given ID.
	 */

	public BannerRequestAPIDetailsDTO findByIdWithDTO(Long requestId) {
		GraphicsRequest graphicsRequest = this.findById(requestId);

		// Null check!
		if (graphicsRequest == null) {
			return null;
		}
		return new BannerRequestAPIDetailsDTO(graphicsRequest);
	}


	public Long countByEventId(Long eventId) {
		return graphicsRequestRepository.countByEventId(eventId);
	}
	
	public boolean hasPendingRequests(Long eventId) {
		return graphicsRequestRepository.countByApprovedIsNullAndEventId(eventId) > 0;
	}
	
	public Page<PendingRequestsBasicInfoDTO> getPendingRequests(int page, int size) {
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
	 * to obtain a paginated result. Each {@link GraphicsRequest} found is then transformed into a {@link BannerRequestAPIDetailsDTO} object.
	 *
	 * @param eventId The unique identifier of the event to filter by; can be {@code null} if filtering by event ID is not required.
	 * @param isApproved A {@link String} representing the approval status to filter the graphics requests;
	 *                   can be {@code null} if this filter is not required. Possible values are "approved", "pending", "rejected".
	 * @param pageable A {@link Pageable} instance containing pagination information.
	 * @return A {@link Page} of {@link BannerRequestAPIDetailsDTO} objects representing the filtered list of graphics requests.
	 *         This can be empty if no matching requests are found, but never {@code null}.
	 */

	public Page<BannerRequestAPIDetailsDTO> findRequestsByCriteria(Long eventId, String isApproved, Pageable pageable) {
		Specification<GraphicsRequest> spec = bannerRequestCriteria(eventId, isApproved);
		return graphicsRequestRepository.findAll(spec, pageable).map(BannerRequestAPIDetailsDTO::new);
	}

	public Specification<GraphicsRequest> bannerRequestCriteria(Long eventId, String isApproved) {
		return (root, query, criteriaBuilder) -> {
			Predicate finalPredicate = criteriaBuilder.conjunction(); // Start with a conjunction (true).

			// Add condition for eventId if it is not null
			if (eventId != null) {
				finalPredicate = criteriaBuilder.and(finalPredicate, criteriaBuilder.equal(root.get("event").get("id"), eventId));
			}

			// If isApproved="na", return all results regardless of the approval status.
			//isApproved.equalsIgnoreCase("na");
			if(!"na".equalsIgnoreCase(isApproved)){
				finalPredicate = criteriaBuilder.and(finalPredicate, criteriaBuilder.equal(root.get("approved"), BooleanUtils.toBooleanObject(isApproved)));
			}

			// Add condition for isApproved if it is not null
			//if (isApproved) {
			//}

			return finalPredicate;
		};
	}

	//public LinkedHashMap<Long, GraphicsRequestDTO> getRequestsByEvent(Long eventId) {
		
		// Get the original data.
	//	List<GraphicsRequest> requestList = graphicsRequestRepository.findByRequestsByEventDescending(eventId, 10);
		
		// Apply the DTO.
	//	List<GraphicsRequestDTO> requestDTOList = new ArrayList<GraphicsRequestDTO>();
	//	for (GraphicsRequest request : requestList) {
	//		requestDTOList.add(new GraphicsRequestDTO(request));
	//	}
		
		// Convert to LinkedHashMap
	//	MapUtils mapUtils = new MapUtils();
	//	return mapUtils.listToLinkedHashMap(requestDTOList, GraphicsRequestDTO::getId);
	//}
	
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
		ApproveRequestsDTO request = new ApproveRequestsDTO(currentRequest, decisionInfo);
		
		// Update request info
		GraphicsRequest updatedRequest = request.toEntity();
		graphicsRequestRepository.save(updatedRequest);
		
		// Re-query the request data (to avoid some null values)
		updatedRequest = this.findById(updatedRequest.getId());

		// Need to set the approver info manually
		FinalizedRequestsDetailsDTO detail = new FinalizedRequestsDetailsDTO(updatedRequest);
		detail.setApprover(userRoleService.findById(updatedRequest.getApproverRoleId()));

		// "Copy" the graphics to the "event_graphics" table.
		NewGraphicsDTO newGraphicsDTO = new NewGraphicsDTO();
		newGraphicsDTO.fromApproval(updatedRequest);
		EventGraphics eventGraphics = newGraphicsDTO.toEntity();
		eventGraphicsRepository.save(eventGraphics);
		
		// Send email
		emailService.graphicsApprovalNotification(detail);
	}
}
