/**
 	* eTWIG - The event management software for Griffin Hall.
 	* @copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
	* @license: MIT
	* @author: Steven Webb [xiaoancloud@outlook.com]
	* @website: https://etwig.grinecraft.net
	* @function: The repository for SingleTimeEvent model.
	*/

package net.grinecraft.etwig.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import net.grinecraft.etwig.model.SingleTimeEvent;

@Repository
public interface SingleTimeEventRepository extends JpaRepository<SingleTimeEvent, Long> {
	
	/**
	 * Find all single time events that happens in a given time range.
	 * @param startDateTime
	 * @param endDateTime
	 * @return The list of SingleTimeEvent objects.
	 */
	
	@Query(value = "SELECT * FROM event_single_time u WHERE u.start_datetime >= :dts and u.start_datetime <= :dte", nativeQuery = true)
	public List<SingleTimeEvent> findByDateRange(@Param("dts") LocalDate startDateTime, @Param("dte") LocalDate endDateTime);
    
	/**
	 * Find a single time event by its id.
	 * @param id
	 * @return
	 */
    public Optional<SingleTimeEvent> findById(long id);
}
