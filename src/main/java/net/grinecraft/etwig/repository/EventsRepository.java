package net.grinecraft.etwig.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.grinecraft.etwig.model.Events;

@Repository
public interface EventsRepository extends JpaRepository<Events, Long> {
	
    public List<Events> findAll();
    //public Events findById();
}
