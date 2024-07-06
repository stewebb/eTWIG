package net.etwig.webapp.repository;

import net.etwig.webapp.dto.events.RecurringEventGraphicsPublicInfoDTO;
import net.etwig.webapp.dto.events.SingleTimeEventGraphicsPublicInfoDTO;
import net.etwig.webapp.dto.graphics.EventGraphicsAPIForSummaryPageDTO;
import net.etwig.webapp.model.EventGraphics;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EventGraphicsRepository extends JpaRepository<EventGraphics, Long>, JpaSpecificationExecutor<EventGraphics> {

	/**
	 * Retrieves a paginated list of {@link EventGraphicsAPIForSummaryPageDTO} representing events with detailed graphics information.
	 * Each {@link EventGraphicsAPIForSummaryPageDTO} contains the event ID, name, start time, count of non-banner graphics,
	 * count of banner graphics, the latest upload time of any associated graphic, and the count of pending banner requests.
	 * <p>
	 * The data is extracted from the {@code Event} entities using a left join on the {@code graphics} associated with each event
	 * and a subsequent join on {@code BannerRequest} for additional banner request details. This method aggregates the number of
	 * graphics categorized as banners and non-banners, the most recent upload time for graphics, and the number of banner requests
	 * without approval (pending status).
	 * <p>
	 * Results are grouped by event ID and ordered by event ID in descending order to prioritize newer events.
	 *
	 * @param pageable a {@code Pageable} object to control pagination and sorting of the query results.
	 * @return a {@code Page<EventGraphicsListDTO>} containing paginated details of events with graphics information,
	 *         organized according to the provided {@code Pageable} object.
	 */

	@Query("SELECT new net.etwig.webapp.dto.graphics.EventGraphicsAPIForSummaryPageDTO(" +
			"e.id, e.name, e.startTime, " +																	// Events
			"(SELECT COUNT(g) FROM EventGraphics g WHERE g.event.id = e.id AND g.banner = FALSE), " +		// Count of TWIG Components
			"(SELECT COUNT(g) FROM EventGraphics g WHERE g.event.id = e.id AND g.banner = TRUE), " +		// Count of banners
			"(SELECT MAX(g.uploadTime) FROM EventGraphics g WHERE g.event.id = e.id), " +					// Most recent modification date
			"(SELECT COUNT(b) FROM BannerRequest b WHERE b.event.id = e.id AND b.approved IS NULL)) " +		// Count of pending banner requests
			"FROM Event e " +
			"GROUP BY e.id")
	Page<EventGraphicsAPIForSummaryPageDTO> eventGraphicsList(Pageable pageable);

	@Query("SELECT new net.etwig.webapp.dto.events.SingleTimeEventGraphicsPublicInfoDTO(e, g) FROM Event e " +
			"LEFT JOIN EventGraphics g WITH g.id = (" +
			"    SELECT MAX(g2.id) " +
			"    FROM EventGraphics g2 " +
			"    WHERE g2.eventId = e.id AND g2.banner = false" +
			") " +
			"WHERE FUNCTION('DATE', e.startTime) = :date AND e.recurring = false " +
			"AND (:portfolio < 0 OR e.userRole.portfolioId = :portfolio)" +
			"ORDER BY e.startTime ASC")
	List<SingleTimeEventGraphicsPublicInfoDTO> findSingleTimeEventsAndLatestGraphicByDateAndPortfolio(
			@Param("date") LocalDate date,
			@Param("portfolio") Long portfolio
	);

	@Query("SELECT new net.etwig.webapp.dto.events.RecurringEventGraphicsPublicInfoDTO(e, g) " +
			"FROM Event e " +
			"LEFT JOIN EventGraphics g " +
			"WITH g.id = (SELECT MAX(g2.id) FROM EventGraphics g2 WHERE g2.eventId = e.id AND g2.banner = false) " +
			"WHERE e.recurring = true " +
			"AND (:portfolio < 0 OR e.userRole.portfolioId = :portfolio)" +
			"ORDER BY e.startTime ASC")
	List<RecurringEventGraphicsPublicInfoDTO> findRecurringEventsAndLatestGraphicByPortfolio(@Param("portfolio") Long portfolio);

	@Transactional
	void deleteByEventId(Long eventId);
}
