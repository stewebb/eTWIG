package net.grinecraft.etwig.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import net.grinecraft.etwig.model.Events;

public interface EventsRepository extends JpaRepository<Events, Long>{
	public Optional<Events> findById(long id);
}
