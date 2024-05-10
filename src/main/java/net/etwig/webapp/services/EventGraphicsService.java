package net.etwig.webapp.services;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import jakarta.persistence.criteria.Predicate;
import net.etwig.webapp.dto.BannerRequestAPIForEventPageDTO;
import net.etwig.webapp.dto.EventGraphicsAPIForDetailsPageDTO;
import net.etwig.webapp.dto.events.RecurringEventGraphicsPublicInfoDTO;
import net.etwig.webapp.dto.events.SingleTimeEventGraphicsPublicInfoDTO;
import net.etwig.webapp.dto.graphics.EventGraphicsDetailsDTO;
import net.etwig.webapp.dto.graphics.EventGraphicsListDTO;
import net.etwig.webapp.dto.graphics.NewGraphicsDTO;
import net.etwig.webapp.model.EventGraphics;
import net.etwig.webapp.model.GraphicsRequest;
import net.etwig.webapp.model.Option;
import net.etwig.webapp.util.DateUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import net.etwig.webapp.repository.EventGraphicsRepository;

@Service
public class EventGraphicsService {
	
	@Autowired
	private EventGraphicsRepository eventGraphicsRepository;

	/**
	 * Finds an event's graphics details by the event's unique identifier.
	 *
	 * This method retrieves an {@link EventGraphics} object by its ID using the {@link EventGraphicsRepository}.
	 * If the event graphics data is found, it is converted into an {@link EventGraphicsAPIForDetailsPageDTO} object.
	 * If no data is found, this method returns {@code null}.
	 *
	 * @param graphicsId The unique identifier of the event to find graphics for. This should be a non-null {@link Long} value.
	 * @return An {@link EventGraphicsAPIForDetailsPageDTO} containing the graphics details of the event, or {@code null} if no event graphics are found.
	 * @throws IllegalArgumentException if {@code eventId} is {@code null}.
	 */

	public EventGraphicsAPIForDetailsPageDTO findById(Long graphicsId){
		Optional<EventGraphics> eventGraphicsOptional = eventGraphicsRepository.findById(graphicsId);
        return eventGraphicsOptional.map(EventGraphicsAPIForDetailsPageDTO::new).orElse(null);
    }

	public Page<EventGraphicsAPIForDetailsPageDTO> findByCriteria(Long eventId, Boolean isBanner, Pageable pageable) {
		Specification<EventGraphics> spec = eventGraphicsCriteria(eventId, isBanner);
		return eventGraphicsRepository.findAll(spec, pageable).map(EventGraphicsAPIForDetailsPageDTO::new);
	}

	private Specification<EventGraphics> eventGraphicsCriteria(Long eventId, Boolean isBanner) {
		return (root, query, criteriaBuilder) -> {
			Predicate finalPredicate = criteriaBuilder.conjunction();

			// Add condition for eventId if it is not null
			if (eventId != null) {
				finalPredicate = criteriaBuilder.and(finalPredicate, criteriaBuilder.equal(root.get("event").get("id"), eventId));
			}

			// If isApproved=null, ignore this filter.
			if(isBanner != null){
				finalPredicate = criteriaBuilder.and(finalPredicate, criteriaBuilder.equal(root.get("banner"), isBanner));
			}

			return finalPredicate;
		};
	}





	
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
