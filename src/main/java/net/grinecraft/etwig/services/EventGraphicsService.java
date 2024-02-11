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
	
	public LinkedHashMap<Integer, Object> getTwigGraphics(Long portfolioId, LocalDate givenDate) {
		LinkedHashMap<Integer, Object> eventsThisWeek = new LinkedHashMap<>();

		// Find this monday
		LocalDate thisMonday = DateUtils.findThisMonday(givenDate);

		// Get the event list of the whole week.
		for(int i=0; i<7; i++){

			// Current day
			LocalDate today = thisMonday.plusDays(i);
			int dayOfWeek = today.getDayOfWeek().getValue();

			// Adjust to have Sunday as 0, Monday as 1, ..., Saturday as 6.
			dayOfWeek = (dayOfWeek % 7);

			// Next day
			LocalDateTime tomorrow = thisMonday.plusDays(i+1).atStartOfDay();
			eventsThisWeek.put(dayOfWeek, eventGraphicsRepository.getGraphicsList(today.atStartOfDay(), tomorrow, portfolioId));

			//System.out.println(today.atStartOfDay() + " " + tomorrow);
			//System.out.println(eventGraphicsRepository.getGraphicsList(today.atStartOfDay(), tomorrow, portfolioId));

		}

		return eventsThisWeek;
	}
}
