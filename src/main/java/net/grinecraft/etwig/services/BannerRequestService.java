package net.grinecraft.etwig.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.grinecraft.etwig.dto.BannerRequestDTO;
import net.grinecraft.etwig.model.GraphicsRequest;
import net.grinecraft.etwig.repository.BannerRequestRepository;
import net.grinecraft.etwig.util.DateUtils;
import net.grinecraft.etwig.util.MapUtils;

@Service
public class BannerRequestService {

	@Autowired
	private BannerRequestRepository bannerRequestRepository;
	
	public Long countByEventId(Long eventId) {
		return bannerRequestRepository.countByEventId(eventId);
	}
	
	public boolean hasPendingRequests(Long eventId) {
		return bannerRequestRepository.countByApprovedIsNullAndEventId(eventId) > 0;
	}
	
	public LinkedHashMap<Long, BannerRequestDTO> getRequestsByEvent(Long eventId) { 
		
		// Get the original data.
		List<GraphicsRequest> requestList = bannerRequestRepository.findByRequestsByEventDescending(eventId, 10);
		
		// Apply the DTO.
		List<BannerRequestDTO> requestDTOList = new ArrayList<BannerRequestDTO>();
		for (GraphicsRequest request : requestList) {
			requestDTOList.add(new BannerRequestDTO(request));
		}	 
		
		// Convert to LinkedHashMap
		MapUtils mapUtils = new MapUtils();
		return mapUtils.listToLinkedHashMap(requestDTOList, BannerRequestDTO::getId);
	}
	
	public void addRequest(Map<String, Object> requestInfo) {
		
		GraphicsRequest request = new GraphicsRequest();
		request.setEventId(Long.parseLong(requestInfo.get("eventId").toString()));
		request.setRequestorId(Long.parseLong(requestInfo.get("requester").toString()));
		request.setRequestComment(requestInfo.get("requestComment").toString());
		request.setExpectDate(DateUtils.safeParseDate(requestInfo.get("returningDate").toString(), "yyyy-MM-dd"));
		request.setRequestTime(LocalDateTime.now());
		
		bannerRequestRepository.save(request);
	}
}
