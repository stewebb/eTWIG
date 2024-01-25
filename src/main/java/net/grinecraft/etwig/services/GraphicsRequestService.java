package net.grinecraft.etwig.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.grinecraft.etwig.dto.graphics.PendingRequestsBasicInfoDTO;
import net.grinecraft.etwig.dto.graphics.FinalizedRequestsBasicInfoDTO;
import net.grinecraft.etwig.dto.graphics.GraphicsRequestDTO;
import net.grinecraft.etwig.model.GraphicsRequest;
import net.grinecraft.etwig.repository.GraphicsRequestRepository;
import net.grinecraft.etwig.util.DateUtils;
import net.grinecraft.etwig.util.MapUtils;

@Service
public class GraphicsRequestService {

	@Autowired
	private GraphicsRequestRepository graphicsRequestRepository;
	
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
		Page<GraphicsRequest> requests =  graphicsRequestRepository.findByApprovedIsNotNullOrderByExpectDateDesc(pageable);
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
	
	public void addRequest(Map<String, Object> requestInfo) {
		
		GraphicsRequest request = new GraphicsRequest();
		request.setEventId(Long.parseLong(requestInfo.get("eventId").toString()));
		request.setRequesterRoleId(Long.parseLong(requestInfo.get("requesterRole").toString()));
		request.setRequestComment(requestInfo.get("requestComment").toString());
		request.setExpectDate(DateUtils.safeParseDate(requestInfo.get("returningDate").toString(), "yyyy-MM-dd"));
		request.setRequestTime(LocalDateTime.now());
		
		graphicsRequestRepository.save(request);
	}
}
