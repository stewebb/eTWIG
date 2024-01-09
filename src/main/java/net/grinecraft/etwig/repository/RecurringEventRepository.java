/**
 	* eTWIG - The event management software for Griffin Hall.
 	* @copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
	* @license: MIT
	* @author: Steven Webb [xiaoancloud@outlook.com]
	* @website: https://etwig.grinecraft.net
	* @function: The repository for RecurringEvent model.
	*/

package net.grinecraft.etwig.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import net.grinecraft.etwig.model.RecurringEvent;

public interface RecurringEventRepository extends JpaRepository<RecurringEvent, Long>{
	
	/**
	 * Find a recurring event by its id.
	 * @param id
	 * @return
	 */
	
	public Optional<RecurringEvent> findById(long id);
}
