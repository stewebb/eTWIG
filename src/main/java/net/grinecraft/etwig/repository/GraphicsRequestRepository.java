package net.grinecraft.etwig.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import net.grinecraft.etwig.model.GraphicsRequest;

public interface GraphicsRequestRepository extends JpaRepository <GraphicsRequest, Long>  {

	long countByEventId(long eventId);
	
    long countByApprovedIsNullAndEventId(long eventIds);
	
    @Query(value = "SELECT * FROM etwig_graphics_request WHERE event = ?1 ORDER BY id DESC LIMIT ?2", nativeQuery = true)
    List<GraphicsRequest> findByRequestsByEventDescending(Long eventId, int limit);

}
