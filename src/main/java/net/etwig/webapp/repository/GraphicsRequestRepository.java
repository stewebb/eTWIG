package net.etwig.webapp.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import net.etwig.webapp.model.GraphicsRequest;

public interface GraphicsRequestRepository extends JpaRepository <GraphicsRequest, Long>, JpaSpecificationExecutor<GraphicsRequest> {

	long countByEventId(long eventId);
	
    long countByApprovedIsNullAndEventId(long eventIds);
	
    @Query(value = "SELECT * FROM etwig_graphics_request WHERE event = ?1 ORDER BY id DESC LIMIT ?2", nativeQuery = true)
    List<GraphicsRequest> findByRequestsByEventDescending(Long eventId, int limit);

    //@Query(value = "SELECT * FROM etwig_graphics_request WHERE event = ?1",
    //        countQuery = "SELECT count(*) FROM etwig_graphics_request WHERE event = ?1",
    //        nativeQuery = true)
    //Page<GraphicsRequest> findByRequestsByEvent(Long eventId, Pageable pageable);
    
    // All pending requests, order by excepted date DESC.
    Page<GraphicsRequest> findByApprovedIsNullOrderByExpectDateDesc(Pageable pageable);

    // All requests, order by response time DESC.
    Page<GraphicsRequest> findByApprovedIsNotNullOrderByResponseTimeDesc(Pageable pageable);
}
