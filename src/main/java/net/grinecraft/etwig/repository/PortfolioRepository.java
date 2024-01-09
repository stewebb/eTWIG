/**
 	* eTWIG - The event management software for Griffin Hall.
 	* @copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
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
	
	/**
	 * Find all portfolios in the database.
	 */
	
    public List<Portfolio> findAll();
    
    /**
     * Find a specific portfolio by its id.
     * @param id
     * @return
     */
    
    public Optional<Portfolio> findById(long id);
    
    /**
     * Find the portfolios with separated calendar.
     * @return
     */
    
    public List<Portfolio> findByIsSeparatedCalendarTrue();
    
    /**
     * Find the portfolios without separated calendar.
     * @return
     */
    
    public List<Portfolio> findByIsSeparatedCalendarFalse();
}