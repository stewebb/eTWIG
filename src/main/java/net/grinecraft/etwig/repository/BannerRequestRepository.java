package net.grinecraft.etwig.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import net.grinecraft.etwig.model.BannerRequest;


public interface BannerRequestRepository extends JpaRepository <BannerRequest, Long>  {

	long countByEventId(long eventId);
	
    long countByApprovedIsNullAndEventId(long eventIds);
	
    @Query(value = "SELECT * FROM banner_request WHERE event_id = ?1 ORDER BY id DESC LIMIT ?2", nativeQuery = true)
    List<BannerRequest> findByRequestsByEventDescending(Long eventId, int limit);

}
