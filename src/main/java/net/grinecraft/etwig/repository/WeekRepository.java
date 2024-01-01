/**
	 * eTWIG - The event and banner management software for residential halls and student unions.
	 * @copyright: Copyright (c) 2024 Steven Webb, eTWIG developers [etwig@grinecraft.net]
	 * @license: MIT
	 * @author: Steven Webb [xiaoancloud@outlook.com]
	 * @website: https://etwig.grinecraft.net
	 * @function: The repository for Week model.
	 */

package net.grinecraft.etwig.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.grinecraft.etwig.model.Week;

@Repository
public interface WeekRepository extends JpaRepository<Week, Long> {
	
    public Optional<Week> findByMonday(LocalDate monday);
    
}