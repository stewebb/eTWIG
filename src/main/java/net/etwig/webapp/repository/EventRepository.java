/**
 	* eTWIG - The event management software for Griffin Hall.
 	* @copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
	* @license: MIT
	* @author: Steven Webb [xiaoancloud@outlook.com]
	* @website: https://etwig.grinecraft.net
	* @function: The repository for Event model.
	*/

package net.etwig.webapp.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import net.etwig.webapp.model.Event;

public interface EventRepository extends JpaRepository<Event, Long>{

	//public Optional<Event> findById(long id);
	
	@Query(value = "SELECT * FROM etwig_event e WHERE e.recurring = false AND e.start_time >= :dts and e.start_time <= :dte", nativeQuery = true)
	public List<Event> findSingleTimeEventByDateRange(@Param("dts") LocalDate startDateTime, @Param("dte") LocalDate endDateTime);
    
	public List<Event> findByRecurringTrue();
	
    //@Query("SELECT s FROM SingleTimeEvent s WHERE s.portfolio IN :portfolios ORDER BY s.id DESC")
    ///List<Event> findByMultiPortfolios(@Param("portfolios") Collection<Long> portfolios);
    
}
