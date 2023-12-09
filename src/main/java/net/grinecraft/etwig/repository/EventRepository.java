package net.grinecraft.etwig.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import net.grinecraft.etwig.model.Event;

public interface EventRepository extends JpaRepository<Event, Long>{
	public Optional<Event> findById(long id);
}
