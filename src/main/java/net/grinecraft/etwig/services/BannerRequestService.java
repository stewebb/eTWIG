package net.grinecraft.etwig.services;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.grinecraft.etwig.dto.BannerRequestDTO;
import net.grinecraft.etwig.model.BannerRequest;
import net.grinecraft.etwig.repository.BannerRequestRepository;
import net.grinecraft.etwig.util.MapUtils;

@Service
public class BannerRequestService {

	@Autowired
	private BannerRequestRepository  bannerRequestRepository;
	
	public Long countByEventId(Long eventId) {
		return bannerRequestRepository.countByEventId(eventId);
	}
	
	public LinkedHashMap<Long, BannerRequestDTO> getRequestsByEvent(Long eventId) { 
		
		// Get the original data.
		List<BannerRequest> requestList = bannerRequestRepository.findByRequestsByEventDescending(eventId, 10);
		
		// Apply the DTO.
		List<BannerRequestDTO> requestDTOList = new ArrayList<BannerRequestDTO>();
		for (BannerRequest request : requestList) {
			requestDTOList.add(new BannerRequestDTO(request));
		}	 
		
		// Convert to LinkedHashMap
		MapUtils mapUtils = new MapUtils();
		return mapUtils.listToLinkedHashMap(requestDTOList, BannerRequestDTO::getId);
	}
}
