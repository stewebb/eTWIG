package net.grinecraft.etwig.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.grinecraft.etwig.model.EventGraphics;
import net.grinecraft.etwig.repository.EventGraphicsRepository;
import net.grinecraft.etwig.util.DateUtils;

@Service
public class EventGraphicsService {
	
	@Autowired
	private EventGraphicsRepository eventGraphicsRepository;
	
	public void getGraphicsByDate(LocalDate givenDate) {
		
		//LocalDate tomorrow = DateUtils.findTomorrow(givenDate);
		
		 LocalDateTime today = givenDate.atStartOfDay();
		 LocalDateTime tomorrow = givenDate.plusDays(1).atStartOfDay();
		
		List<EventGraphics> graphicsList = eventGraphicsRepository.getGraphicsListByDateRange(today, tomorrow);
		
		System.out.println(graphicsList);
	}
}
