/**
 	* eTWIG - The event management software for Griffin Hall.
 	* @copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
	* @license: MIT
	* @author: Steven Webb [xiaoancloud@outlook.com]
	* @website: https://etwig.grinecraft.net
	* @function: The repository for UserRole model.
	*/

package net.etwig.webapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.etwig.webapp.model.EventOption;
import net.etwig.webapp.model.EventOptionKey;

@Repository
public interface EventOptionRepository extends JpaRepository<EventOption, EventOptionKey> {

    List<EventOption> findByIdEventId(Long eventId);
}
