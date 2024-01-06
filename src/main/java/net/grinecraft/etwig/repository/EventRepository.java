/**
 	* eTWIG - The event management software for university communities.
 	* @copyright: Copyright (c) 2024 Steven Webb
	* @license: MIT
	* @author: Steven Webb [xiaoancloud@outlook.com]
	* @website: https://etwig.grinecraft.net
	* @function: The repository for Event model.
	*/

package net.grinecraft.etwig.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import net.grinecraft.etwig.model.Event;

public interface EventRepository extends JpaRepository<Event, Long>{
	public Optional<Event> findById(long id);
}
