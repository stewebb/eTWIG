package net.grinecraft.etwig.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;

import net.grinecraft.etwig.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.grinecraft.etwig.repository.EventGraphicsRepository;

@Service
public class EventGraphicsService {
	
	@Autowired
	private EventGraphicsRepository eventGraphicsRepository;
	
	public LinkedHashMap<LocalDate, Object> getTwigGraphics(Long portfolioId, LocalDate givenDate) {

		//System.out.println(portfolioId);
		//System.out.println(givenDate);

		LinkedHashMap<LocalDate, Object> eventsThisWeek = new LinkedHashMap<>();

		// Find this monday
		LocalDate thisMonday = DateUtils.findThisMonday(givenDate);

		for(int i=0; i<7; i++){

			LocalDate today = thisMonday.plusDays(i);
			LocalDateTime tomorrow = thisMonday.plusDays(i+1).atStartOfDay();

			//System.out.println(today + " " + tomorrow);
			//System.out.println(eventGraphicsRepository.getGraphicsList(today.atStartOfDay(), tomorrow, portfolioId));

			eventsThisWeek.put(today, eventGraphicsRepository.getGraphicsList(today.atStartOfDay(), tomorrow, portfolioId));
		}

		return eventsThisWeek;
		// Convert to LocalDateTime
		//LocalDateTime today = givenDate.atStartOfDay();
		//LocalDateTime tomorrow = givenDate.plusDays(1).atStartOfDay();

		//System.out.println(portfolioId);

		//return eventGraphicsRepository.getGraphicsList(today, tomorrow, portfolioId);
	}
}
