package net.grinecraft.etwig.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.grinecraft.etwig.dto.GraphicsRequestDTO;
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
