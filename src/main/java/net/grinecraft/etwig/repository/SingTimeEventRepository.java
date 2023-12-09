package net.grinecraft.etwig.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import net.grinecraft.etwig.model.SingleTimeEvent;

@Repository
public interface SingTimeEventRepository extends JpaRepository<SingleTimeEvent, Long> {
	
	@Query(value = "SELECT * FROM event_single_time u WHERE u.start_datetime >= :dts and u.start_datetime <= :dte", nativeQuery = true)
	public List<SingleTimeEvent> findByDateRange(@Param("dts") LocalDate startDateTime, @Param("dte") LocalDate endDateTime);
    
    public Optional<SingleTimeEvent> findById(long id);
}
