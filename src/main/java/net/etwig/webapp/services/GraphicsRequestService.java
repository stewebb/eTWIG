package net.etwig.webapp.services;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import net.grinecraft.etwig.dto.graphics.*;
import net.grinecraft.etwig.model.EventGraphics;
import net.grinecraft.etwig.repository.EventGraphicsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import net.grinecraft.etwig.model.GraphicsRequest;
import net.grinecraft.etwig.repository.GraphicsRequestRepository;
import net.grinecraft.etwig.util.MapUtils;

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
	
	public LinkedHashMap<Long, GraphicsRequestDTO> getRequestsByEvent(Long eventId) { 
		
		// Get the original data.
		List<GraphicsRequest> requestList = graphicsRequestRepository.findByRequestsByEventDescending(eventId, 10);
		
		// Apply the DTO.
		List<GraphicsRequestDTO> requestDTOList = new ArrayList<GraphicsRequestDTO>();
		for (GraphicsRequest request : requestList) {
			requestDTOList.add(new GraphicsRequestDTO(request));
		}	 
		
		// Convert to LinkedHashMap
		MapUtils mapUtils = new MapUtils();
		return mapUtils.listToLinkedHashMap(requestDTOList, GraphicsRequestDTO::getId);
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
