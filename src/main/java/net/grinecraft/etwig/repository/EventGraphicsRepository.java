package net.grinecraft.etwig.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import net.grinecraft.etwig.model.EventGraphics;

@Repository
public interface EventGraphicsRepository extends JpaRepository<EventGraphics, Long>{

	//@Query("SELECT g FROM EventGraphics g "
	//	       + "LEFT JOIN Event e ON g.eventId = e.id "
	//	       + "WHERE e.startTime > :start AND e.startTime < :end")
	//@Query("SELECT * FROM etwig_event_graphics g JOIN etwig_event e ON g.event = e.id WHERE e.start_time > :dts AND e.start_time < :dte", nativeQuery = true)

	@Query(value = "SELECT * FROM etwig_event_graphics g JOIN etwig_event e ON g.event = e.id WHERE e.start_time > :dts AND e.start_time < :dte", nativeQuery = true)
	public List<EventGraphics> getGraphicsListByDateRange(@Param("dts") LocalDate start,  @Param("dte")LocalDate end);
}
