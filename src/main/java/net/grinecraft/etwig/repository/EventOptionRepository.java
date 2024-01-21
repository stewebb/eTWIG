/**
 	* eTWIG - The event management software for Griffin Hall.
 	* @copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
	* @license: MIT
	* @author: Steven Webb [xiaoancloud@outlook.com]
	* @website: https://etwig.grinecraft.net
	* @function: The repository for UserRole model.
	*/

package net.grinecraft.etwig.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.grinecraft.etwig.model.EventOption;
import net.grinecraft.etwig.model.EventOptionKey;

@Repository
public interface EventOptionRepository extends JpaRepository<EventOption, EventOptionKey> {
	
	/**
	 * Find all options of a given event.
	 * @param eventId
	 * @return
	 */
	
    //List<EventOption> findByIdEventId(Long eventId);
}
