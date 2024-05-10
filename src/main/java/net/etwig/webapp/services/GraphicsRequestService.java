package net.etwig.webapp.services;

import java.util.Map;
import java.util.Optional;

import jakarta.persistence.criteria.Predicate;
import net.etwig.webapp.dto.BannerRequestAPIForEventPageDTO;
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
	
	public GraphicsRequest findById(Long requestId) {
		return graphicsRequestRepository.findById(requestId).orElse(null);
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



	//public Page<BannerRequestForEventPageDTO> findRequestsByEvent(Long eventId, Pageable pageable) {
	//	return graphicsRequestRepository.findByRequestsByEvent(eventId, pageable).map(BannerRequestForEventPageDTO::new);
	//}

	/**
	 * Retrieves a paginated list of banner requests based on the specified criteria.
	 *
	 * This method constructs a {@link Specification} using the given event ID and approval status to filter the results.
	 * It then queries the {@link GraphicsRequestRepository} with this specification and the provided {@link Pageable} object
	 * to obtain a paginated result. Each {@link GraphicsRequest} found is then transformed into a {@link BannerRequestAPIForEventPageDTO} object.
	 *
	 * @param eventId The unique identifier of the event to filter by; can be {@code null} if filtering by event ID is not required.
	 * @param isApproved A {@link String} representing the approval status to filter the graphics requests;
	 *                   can be {@code null} if this filter is not required. Possible values are "approved", "pending", "rejected".
	 * @param pageable A {@link Pageable} instance containing pagination information.
	 * @return A {@link Page} of {@link BannerRequestAPIForEventPageDTO} objects representing the filtered list of graphics requests.
	 *         This can be empty if no matching requests are found, but never {@code null}.
	 */

	public Page<BannerRequestAPIForEventPageDTO> findRequestsByCriteria(Long eventId, String isApproved, Pageable pageable) {
		Specification<GraphicsRequest> spec = bannerRequestCriteria(eventId, isApproved);
		return graphicsRequestRepository.findAll(spec, pageable).map(BannerRequestAPIForEventPageDTO::new);
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
