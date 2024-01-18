/**
 	* eTWIG - The event management software for Griffin Hall.
 	* @copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
	* @license: MIT
	* @author: Steven Webb [xiaoancloud@outlook.com]
	* @website: https://etwig.grinecraft.net
	* @function: The repository for Event model.
	*/

package net.grinecraft.etwig.repository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import net.grinecraft.etwig.model.Event;

public interface EventRepository extends JpaRepository<Event, Long>{
	
	/**
	 * Find the event information by its id.
	 * @param id
	 * @return
	 */
	//public Optional<Event> findById(long id);

	//@Query(value = "SELECT * FROM event_single_time u WHERE u.start_datetime >= :dts and u.start_datetime <= :dte", nativeQuery = true)
	//public List<Event> findByDateRange(@Param("dts") LocalDate startDateTime, @Param("dte") LocalDate endDateTime);
    
    //@Query("SELECT s FROM SingleTimeEvent s WHERE s.portfolio IN :portfolios ORDER BY s.id DESC")
    ///List<Event> findByMultiPortfolios(@Param("portfolios") Collection<Long> portfolios);
    
}
