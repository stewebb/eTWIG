package net.grinecraft.etwig.DAO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import net.grinecraft.etwig.Model.Events;

@Repository
public interface EventsRepository extends JpaRepository<Events, Long> {
	
	//@Query("SELECT t FROM Tag t WHERE (SELECT SUM(v.views) FROM Video v WHERE t MEMBER OF v.tags) > 0")
    public List<Events> findAll();
}
