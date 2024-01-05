/**
	 * eTWIG - The event and banner management software for residential halls and student unions.
	 * @copyright: Copyright (c) 2024 Steven Webb, eTWIG developers [etwig@grinecraft.net]
	 * @license: MIT
	 * @author: Steven Webb [xiaoancloud@outlook.com]
	 * @website: https://etwig.grinecraft.net
	 * @function: The repository for Portfolio model.
	 */

package net.grinecraft.etwig.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.grinecraft.etwig.model.Portfolio;

@Repository
public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {
	
    public List<Portfolio> findAll();
    
    public Optional<Portfolio> findById(long id);
    
    public List<Portfolio> findByIsSeparatedCalendarTrue();
    public List<Portfolio> findByIsSeparatedCalendarFalse();
}