package net.grinecraft.etwig.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import net.grinecraft.etwig.model.SingleTimeEvents;

@Repository
public interface SingTimeEventsRepository extends JpaRepository<SingleTimeEvents, Long> {
	
	//@Query(value = "SELECT * FROM events_single_time WHERE start_datetime >= u and start_datetime <= u")
    //public List<SingleTimeEvents> findByDateRange(String startDateTime);
	
	@Query(value = "SELECT * FROM events_single_time u WHERE u.start_datetime >= :dt and u.start_datetime <= :dt", nativeQuery = true)
	public List<SingleTimeEvents> findByDateRange(@Param("dt") String startDateTime);
    
    public Optional<SingleTimeEvents> findById(long id);
}
