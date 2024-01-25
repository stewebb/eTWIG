package net.grinecraft.etwig.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import net.grinecraft.etwig.dto.graphics.PendingRequestsBasicInfoDTO;
import net.grinecraft.etwig.dto.graphics.PendingRequestsDetailsDTO;
import net.grinecraft.etwig.dto.graphics.ApproveRequestsDTO;
import net.grinecraft.etwig.dto.graphics.FinalizedRequestsBasicInfoDTO;
import net.grinecraft.etwig.dto.graphics.FinalizedRequestsDetailsDTO;
import net.grinecraft.etwig.dto.graphics.GraphicsRequestDTO;
import net.grinecraft.etwig.model.GraphicsRequest;
import net.grinecraft.etwig.repository.GraphicsRequestRepository;
import net.grinecraft.etwig.util.DateUtils;
import net.grinecraft.etwig.util.MapUtils;

@Service
public class GraphicsRequestService {

	@Autowired
	private GraphicsRequestRepository graphicsRequestRepository;
	
	@Autowired
	private EmailService emailService;
	
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
	
	public void addRequest(Map<String, Object> requestInfo) {
		
		GraphicsRequest request = new GraphicsRequest();
		request.setEventId(Long.parseLong(requestInfo.get("eventId").toString()));
		request.setRequesterRoleId(Long.parseLong(requestInfo.get("requesterRole").toString()));
		request.setRequestComment(requestInfo.get("requestComment").toString());
		request.setExpectDate(DateUtils.safeParseDate(requestInfo.get("returningDate").toString(), "yyyy-MM-dd"));
		request.setRequestTime(LocalDateTime.now());
		
		graphicsRequestRepository.save(request);
	}
	
	@SuppressWarnings("null")
	public void approveRequest(GraphicsRequest currentRequest, Map<String, Object> decisionInfo) throws Exception {
		ApproveRequestsDTO request = new ApproveRequestsDTO(currentRequest, decisionInfo);
		
		// Update request info
		GraphicsRequest updatedRequest = request.toEntity();
		graphicsRequestRepository.save(updatedRequest);
		
		// Re-query the request data (to avoid some null values)
		updatedRequest = this.findById(updatedRequest.getId());
		//System.out.println(updatedRequest);
		
		// Need to set the approver info manually
		FinalizedRequestsDetailsDTO detail = new FinalizedRequestsDetailsDTO(updatedRequest);
		detail.setApprover(userRoleService.findById(updatedRequest.getApproverRoleId()));
		
		//System.out.println(updatedRequest.getApproverRoleId());
		//System.out.println(userRoleService.findById(updatedRequest.getApproverRoleId()));
		
		// Send email
		emailService.graphicsApprovalNotification(detail);
	}
}
