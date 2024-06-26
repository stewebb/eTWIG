package net.etwig.webapp.services;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import jakarta.persistence.criteria.Predicate;
import net.etwig.webapp.dto.events.RecurringEventGraphicsPublicInfoDTO;
import net.etwig.webapp.dto.events.SingleTimeEventGraphicsPublicInfoDTO;
import net.etwig.webapp.dto.graphics.EventGraphicsAPIForDetailsPageDTO;
import net.etwig.webapp.dto.graphics.EventGraphicsDetailsDTO;
import net.etwig.webapp.dto.graphics.EventGraphicsAPIForSummaryPageDTO;
import net.etwig.webapp.dto.graphics.NewGraphicsDTO;
import net.etwig.webapp.model.EventGraphics;
import net.etwig.webapp.util.DateUtils;
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

	@Autowired
	private UserSessionService userSessionService;

	/**
	 * Finds an event's graphics details by the event's unique identifier.
	 * <p>
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

	/**
	 * Retrieves a paginated list of event graphics based on the specified criteria.
	 * <p>
	 * This method constructs a {@link Specification} using the given event ID and banner status to filter the results.
	 * It then queries the {@link EventGraphicsRepository} with this specification and the provided {@link Pageable} object
	 * to obtain a paginated result. Each {@link EventGraphics} found is then transformed into an
	 * {@link EventGraphicsAPIForDetailsPageDTO} object.
	 *
	 * @param eventId The unique identifier of the event; can be {@code null} if filtering by event ID is not required.
	 * @param isBanner A {@link Boolean} indicating whether to filter the graphics as banners; can be {@code null} if this filter is not required.
	 * @param pageable A {@link Pageable} instance containing pagination information.
	 * @return A {@link Page} of {@link EventGraphicsAPIForDetailsPageDTO} objects representing the filtered list of event graphics.
	 *         This can be empty if no matching graphics are found, but never {@code null}.
	 */

	public Page<EventGraphicsAPIForDetailsPageDTO> findByCriteriaForDetails(Long eventId, Boolean isBanner, Pageable pageable) {
		Specification<EventGraphics> spec = eventGraphicsCriteriaForDetails(eventId, isBanner);
		return eventGraphicsRepository.findAll(spec, pageable).map(EventGraphicsAPIForDetailsPageDTO::new);
	}

	/**
	 * Constructs a {@link Specification} for querying {@link EventGraphics} based on the provided event ID and banner status.
	 * <p>
	 * This method builds a dynamic query specification that can filter {@link EventGraphics} entities based on the event ID
	 * and whether the graphics are considered banners. The specification constructs a {@link Predicate} that accumulates
	 * all conditions for the query.
	 *
	 * @param eventId The unique identifier of the event to filter by; may be {@code null}, in which case the filter for event ID is not applied.
	 * @param isBanner A {@link Boolean} indicating whether the graphics should be filtered by banner status; may be {@code null},
	 *                 in which case the filter for banner status is not applied.
	 * @return A {@link Specification<EventGraphics>} that can be used to query the database with the specified filters.
	 */

	private Specification<EventGraphics> eventGraphicsCriteriaForDetails(Long eventId, Boolean isBanner) {
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

	/**
	 * Fetches a paginated list of event graphics summaries encapsulated within {@link EventGraphicsAPIForSummaryPageDTO} objects.
	 * This method delegates the data retrieval to {@code eventGraphicsRepository.eventGraphicsList(pageable)},
	 * which executes a complex query to gather detailed graphics data associated with each event.
	 * <p>
	 * The resulting page includes details such as event ID, name, start time, counts of banner and non-banner graphics,
	 * the latest graphic upload time, and the count of pending banner requests. It utilizes the specified {@code Pageable}
	 * to control pagination and sorting, ensuring that the data is presented in a manageable and ordered format.
	 *
	 * @param pageable a {@code Pageable} object that specifies the pagination and sorting criteria.
	 * @return a {@code Page<EventGraphicsAPIForSummaryPageDTO>} containing the paginated event graphics summaries.
	 */

	public Page<EventGraphicsAPIForSummaryPageDTO> findBySummary(Pageable pageable) {
		return eventGraphicsRepository.eventGraphicsList(pageable);
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

	public List<EventGraphicsDetailsDTO> getGraphicsDetailsByEventId(Long eventId, Boolean banner){
		List<EventGraphics> eventGraphicsList = eventGraphicsRepository.findByEventIdAndBannerOrderByIdDesc(eventId, banner);
		return eventGraphicsList.stream()
				.map(EventGraphicsDetailsDTO::new)
				.collect(Collectors.toList());
	}

	public void addGraphics(Map<String, Object> newGraphicsInfo){
		NewGraphicsDTO newGraphicsDTO = new NewGraphicsDTO();
		newGraphicsDTO.addDirectly(newGraphicsInfo, userSessionService.validateSession().getPosition().getMyCurrentPositionId());
		eventGraphicsRepository.save(newGraphicsDTO.toEntity());
	}
}
