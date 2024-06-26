/**
 	* eTWIG - The event management software for Griffin Hall.
 	* @copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
	* @license: MIT
	* @author: Steven Webb [xiaoancloud@outlook.com]
	* @website: https://etwig.grinecraft.net
	* @function: The repository for Portfolio model.
	*/

package net.etwig.webapp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import net.etwig.webapp.model.Portfolio;

@Repository
public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {
	
	/**
	 * Find all portfolios in the database.
	 */
	
    //public @NonNull List<Portfolio> findAll();
    
    //@Query(value = "SELECT * FROM etwig_portfolio ORDER BY LENGTH(name) DESC", nativeQuery = true)
    @Query(value = "SELECT * FROM etwig_portfolio ORDER BY (COALESCE(LENGTH(name), 0) + COALESCE(LENGTH(abbreviation), 0)) DESC", nativeQuery = true)
    public List<Portfolio> findAllOrderByNameLengthDesc();
    
    /**
     * Find a specific portfolio by its id.
     * @param id
     * @return
     */

    Optional<Portfolio> findById(long id);
    
    /**
     * Find the portfolios with separated calendar.
     * @return
     */

    List<Portfolio> findBySeparatedCalendarTrue();
    
    /**
     * Find the portfolios without separated calendar.
     * @return
     */

    List<Portfolio> findBySeparatedCalendarFalse();
}