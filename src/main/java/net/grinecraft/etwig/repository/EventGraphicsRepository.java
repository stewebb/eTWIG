package net.grinecraft.etwig.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import net.grinecraft.etwig.dto.graphics.EventGraphicsListDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import net.grinecraft.etwig.dto.events.EventGraphicsPublicInfoDTO;
import net.grinecraft.etwig.model.EventGraphics;

@Repository
public interface EventGraphicsRepository extends JpaRepository<EventGraphics, Long>{

	//@Query("SELECT new net.grinecraft.etwig.dto.events.EventGraphicsPublicInfoDTO(g) " +
	//		"FROM EventGraphics g JOIN g.event e " +
	//		"WHERE e.startTime >= :dts AND e.startTime < :dte AND e.recurring = false " +
	//		"AND (:portfolio < 0 OR e.userRole.portfolioId = :portfolio)")
    //List<EventGraphicsPublicInfoDTO> getGraphicsList(
    //        @Param("dts") LocalDateTime start,
    //        @Param("dte") LocalDateTime tomorrow,
    //        @Param("portfolio") Long portfolioId
    //);

	@Query("SELECT new net.grinecraft.etwig.dto.graphics.EventGraphicsListDTO(" +
			"e.id, e.name, " +													// Events
			"SUM(CASE WHEN g.banner = FALSE THEN 1 ELSE 0 END), " + 			// Count of graphics
			"SUM(CASE WHEN g.banner = TRUE THEN 1 ELSE 0 END), " + 				// Count of banners
			"MAX(g.uploadTime)) " + 											// Most recent modification date
			"FROM Event e LEFT JOIN EventGraphics g ON e.id = g.eventId " +
			"GROUP BY e.id ORDER BY e.id DESC")
	Page<EventGraphicsListDTO> eventGraphicsList(Pageable pageable);

	//@Query("SELECT new net.grinecraft.etwig.dto.events.EventGraphicsPublicInfoDTO(g) " +
	//		"FROM Event e LEFT JOIN g " +
	//		"ON g.eventId = e.id" +
	//		"WHERE FUNCTION('DATE', e.startDateTime) = :date " +
	//		"AND (g.id IS NULL OR g.id IN (SELECT MAX(g2.id) FROM Graphic g2 WHERE g2.event = e GROUP BY g2.event)) " +
	//		"ORDER BY e.startDateTime ASC")

	@Query("SELECT new net.grinecraft.etwig.dto.events.EventGraphicsPublicInfoDTO(g) " +
			"FROM Event e LEFT JOIN EventGraphics g WITH g.id = (SELECT MAX(g2.id) FROM EventGraphics g2 WHERE g2.eventId = e.id AND g2.banner = false) " +
			"WHERE FUNCTION('DATE', e.startTime) = :date " +
			"ORDER BY e.startTime ASC")
	List<EventGraphicsPublicInfoDTO> findEventsAndLatestGraphicByDate(@Param("date") LocalDate date);

	List<EventGraphics> findByEventIdAndBannerOrderByIdDesc(Long eventId, boolean banner);
}
