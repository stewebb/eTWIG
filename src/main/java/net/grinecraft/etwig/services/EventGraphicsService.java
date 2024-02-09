package net.grinecraft.etwig.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.grinecraft.etwig.dto.events.EventGraphicsPublicInfoDTO;
import net.grinecraft.etwig.repository.EventGraphicsRepository;

@Service
public class EventGraphicsService {
	
	@Autowired
	private EventGraphicsRepository eventGraphicsRepository;
	
	public List<EventGraphicsPublicInfoDTO> getGraphicsByDate(LocalDate givenDate) {
		
		// Convert to LocalDateTime 
		 LocalDateTime today = givenDate.atStartOfDay();
		 LocalDateTime tomorrow = givenDate.plusDays(1).atStartOfDay();
		
		List<EventGraphicsPublicInfoDTO> graphicsList = eventGraphicsRepository.getGraphicsListByDateRange(today, tomorrow);
		
		//System.out.println(graphicsList);
		return graphicsList;
	}
}
