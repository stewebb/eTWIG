package net.grinecraft.etwig.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import net.grinecraft.etwig.model.RecurringEvent;

public interface RecurringEventRepository extends JpaRepository<RecurringEvent, Long>{
	public Optional<RecurringEvent> findById(long id);
}
