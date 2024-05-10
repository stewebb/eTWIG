package net.etwig.webapp.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import io.micrometer.observation.ObservationFilter;
import net.etwig.webapp.dto.events.RecurringEventGraphicsPublicInfoDTO;
import net.etwig.webapp.dto.graphics.EventGraphicsListDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import net.etwig.webapp.dto.events.SingleTimeEventGraphicsPublicInfoDTO;
import net.etwig.webapp.model.EventGraphics;

@Repository
public interface EventGraphicsRepository extends JpaRepository<EventGraphics, Long>, JpaSpecificationExecutor<EventGraphics> {

	@Query("SELECT new net.etwig.webapp.dto.graphics.EventGraphicsListDTO(" +
			"e.id, e.name, e.startTime, " +										// Events
			"SUM(CASE WHEN g.banner = FALSE THEN 1 ELSE 0 END), " + 			// Count of graphics
			"SUM(CASE WHEN g.banner = TRUE THEN 1 ELSE 0 END), " + 				// Count of banners
			"MAX(g.uploadTime)) " + 											// Most recent modification date
			"FROM Event e LEFT JOIN EventGraphics g ON e.id = g.eventId " +
			"GROUP BY e.id ORDER BY e.id DESC")
	Page<EventGraphicsListDTO> eventGraphicsList(Pageable pageable);

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

	List<EventGraphics> findByEventIdAndBannerOrderByIdDesc(Long eventId, boolean banner);

	Optional<EventGraphics> findById(Long id);

}
