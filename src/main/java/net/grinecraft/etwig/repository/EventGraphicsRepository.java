package net.grinecraft.etwig.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import net.grinecraft.etwig.model.EventGraphics;

@Repository
public interface EventGraphicsRepository {

	@Query("SELECT g FROM EventGraphics g "
			+ "WHERE e.startTime > :start AND e.startTime < end"
			+ "LEFT JOIN Event e"
			+ "ON g.eventId = e.id")
	public List<EventGraphics> getGraphicsListByDateRange(LocalDate start, LocalDate end);
}
