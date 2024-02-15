package net.grinecraft.etwig.repository;

import java.time.LocalDate;
import java.util.List;

import net.grinecraft.etwig.dto.events.RecurringEventGraphicsPublicInfoDTO;
import net.grinecraft.etwig.dto.graphics.EventGraphicsListDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import net.grinecraft.etwig.dto.events.SingleTimeEventGraphicsPublicInfoDTO;
import net.grinecraft.etwig.model.EventGraphics;

@Repository
public interface EventGraphicsRepository extends JpaRepository<EventGraphics, Long>{

	@Query("SELECT new net.grinecraft.etwig.dto.graphics.EventGraphicsListDTO(" +
			"e.id, e.name, " +													// Events
			"SUM(CASE WHEN g.banner = FALSE THEN 1 ELSE 0 END), " + 			// Count of graphics
			"SUM(CASE WHEN g.banner = TRUE THEN 1 ELSE 0 END), " + 				// Count of banners
			"MAX(g.uploadTime)) " + 											// Most recent modification date
			"FROM Event e LEFT JOIN EventGraphics g ON e.id = g.eventId " +
			"GROUP BY e.id ORDER BY e.id DESC")
	Page<EventGraphicsListDTO> eventGraphicsList(Pageable pageable);

	//@Query("SELECT new net.grinecraft.etwig.dto.events.SingleTimeEventGraphicsPublicInfoDTO(g) " +
	//		"FROM Event e " +
	//		"LEFT JOIN EventGraphics g " +
	//		"WITH g.id = (SELECT MAX(g2.id) FROM EventGraphics g2 WHERE g2.eventId = e.id AND g2.banner = false) " +
	//		"WHERE FUNCTION('DATE', e.startTime) = :date " +
	//		"AND e.recurring = false" +
	//		"ORDER BY e.startTime ASC")
	@Query("SELECT new net.grinecraft.etwig.dto.events.SingleTimeEventGraphicsPublicInfoDTO(g) FROM Event e \n" +
			"LEFT JOIN EventGraphics g WITH g.id = (\n" +
			"    SELECT MAX(g2.id) \n" +
			"    FROM EventGraphics g2 \n" +
			"    WHERE g2.eventId = e.id AND g2.banner = false\n" +
			") \n" +
			"WHERE FUNCTION('DATE', e.startTime) = :date AND e.recurring = false \n" +
			"ORDER BY e.startTime ASC\n")
	List<SingleTimeEventGraphicsPublicInfoDTO> findSingleTimeEventsAndLatestGraphicByDate(@Param("date") LocalDate date);

	@Query("SELECT new net.grinecraft.etwig.dto.events.RecurringEventGraphicsPublicInfoDTO(g) " +
			"FROM Event e " +
			"LEFT JOIN EventGraphics g " +
			"WITH g.id = (SELECT MAX(g2.id) FROM EventGraphics g2 WHERE g2.eventId = e.id AND g2.banner = false) " +
			"WHERE e.recurring = true " +
			"ORDER BY e.startTime ASC")
	List<RecurringEventGraphicsPublicInfoDTO> findRecurringEventsAndLatestGraphicByDate();

	List<EventGraphics> findByEventIdAndBannerOrderByIdDesc(Long eventId, boolean banner);
}
