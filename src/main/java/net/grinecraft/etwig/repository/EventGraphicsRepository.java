package net.grinecraft.etwig.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import net.grinecraft.etwig.dto.events.EventGraphicsPublicInfoDTO;
import net.grinecraft.etwig.model.EventGraphics;

@Repository
public interface EventGraphicsRepository extends JpaRepository<EventGraphics, Long>{

	@Query("SELECT new net.grinecraft.etwig.dto.events.EventGraphicsPublicInfoDTO(g) " +
			"FROM EventGraphics g JOIN g.event e " +
			"WHERE e.startTime >= :dts AND e.startTime < :dte AND e.recurring = false " +
			"AND (:portfolio < 0 OR e.userRole.portfolioId = :portfolio)")
	public List<EventGraphicsPublicInfoDTO> getGraphicsList(
			@Param("dts") LocalDateTime start,
			@Param("dte") LocalDateTime tomorrow,
			@Param("portfolio") Long portfolioId
	);
}
