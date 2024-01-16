package net.grinecraft.etwig.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.grinecraft.etwig.repository.BannerRequestRepository;

@Service
public class BannerRequestService {

	@Autowired
	private BannerRequestRepository  bannerRequestRepository;
	
	public Long countByEventId(Long eventId) {
		return bannerRequestRepository.countByEventId(eventId);
	}
}
