/**
 	* eTWIG - The event management software for Griffin Hall.
 	* @copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
	* @license: MIT
	* @author: Steven Webb [xiaoancloud@outlook.com]
	* @website: https://etwig.grinecraft.net
	* @function: The repository for Week model.
	*/

package net.grinecraft.etwig.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.grinecraft.etwig.model.Week;

@Repository
public interface WeekRepository extends JpaRepository<Week, Long> {
	
	/**
	 * Find a week information by the Monday date.
	 * @param monday
	 * @return
	 */
	
    //public Optional<Week> findByMonday(LocalDate monday);
    
}