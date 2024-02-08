package net.grinecraft.etwig.services;

import java.time.LocalDate;
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
		
		LocalDate tomorrow = DateUtils.findTomorrow(givenDate);
		
		List<EventGraphics> graphicsList = eventGraphicsRepository.getGraphicsListByDateRange(givenDate, tomorrow);
		
		System.out.println(graphicsList);
	}
}
