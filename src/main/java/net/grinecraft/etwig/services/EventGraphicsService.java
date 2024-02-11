package net.grinecraft.etwig.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import net.grinecraft.etwig.dto.graphics.EventGraphicsDetailsDTO;
import net.grinecraft.etwig.dto.graphics.EventGraphicsListDTO;
import net.grinecraft.etwig.dto.graphics.NewGraphicsDTO;
import net.grinecraft.etwig.model.EventGraphics;
import net.grinecraft.etwig.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

	public Page<EventGraphicsListDTO> eventGraphicsList(int page, int size){
		Pageable pageable = PageRequest.of(page, size);
		return eventGraphicsRepository.eventGraphicsList(pageable);
	}

	public List<EventGraphicsDetailsDTO> getGraphicsDetailsByEventId(Long eventId, Boolean banner){
		List<EventGraphics> eventGraphicsList = eventGraphicsRepository.findByEventIdAndBannerOrderByIdDesc(eventId, banner);
		return eventGraphicsList.stream()
				.map(EventGraphicsDetailsDTO::new)
				.collect(Collectors.toList());
	}

	public void addGraphics(Map<String, Object> newGraphicsInfo){
		NewGraphicsDTO newGraphicsDTO = new NewGraphicsDTO();
		newGraphicsDTO.addDirectly(newGraphicsInfo);
		eventGraphicsRepository.save(newGraphicsDTO.toEntity());
	}
}
