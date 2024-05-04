package net.etwig.webapp.services;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import net.etwig.webapp.dto.events.RecurringEventGraphicsPublicInfoDTO;
import net.etwig.webapp.dto.events.SingleTimeEventGraphicsPublicInfoDTO;
import net.etwig.webapp.dto.graphics.EventGraphicsDetailsDTO;
import net.etwig.webapp.dto.graphics.EventGraphicsListDTO;
import net.etwig.webapp.dto.graphics.NewGraphicsDTO;
import net.etwig.webapp.model.EventGraphics;
import net.etwig.webapp.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.etwig.webapp.repository.EventGraphicsRepository;

@Service
public class EventGraphicsService {
	
	@Autowired
	private EventGraphicsRepository eventGraphicsRepository;
	
	public LinkedHashMap<Integer, Object> getTwigGraphicsSingleTimeEvents(Long portfolioId, LocalDate givenDate) {
		LinkedHashMap<Integer, Object> eventsThisWeek = new LinkedHashMap<>();

		// Find this monday
		LocalDate thisMonday = DateUtils.findThisMonday(givenDate);

		// Get the event list of the whole week.
		for(int i=0; i<7; i++){

			// Current day
			LocalDate today = thisMonday.plusDays(i);
			int dayOfWeek = today.getDayOfWeek().getValue();

			// Get results and remove elements with null id.
			List<SingleTimeEventGraphicsPublicInfoDTO> result = eventGraphicsRepository.findSingleTimeEventsAndLatestGraphicByDateAndPortfolio(today, portfolioId);
			result.removeIf(obj -> Objects.isNull(obj.getEventId()));

			// Adjust to have Sunday as 0, Monday as 1, ..., Saturday as 6.
			eventsThisWeek.put((dayOfWeek % 7), result);
		}

		return eventsThisWeek;
	}

	public List<RecurringEventGraphicsPublicInfoDTO> getTwigGraphicsRecurringEvents(Long portfolioId) {
		return eventGraphicsRepository.findRecurringEventsAndLatestGraphicByPortfolio(portfolioId);
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
