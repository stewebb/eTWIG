/**
 	* eTWIG - The event management software for Griffin Hall.
 	* @copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
	* @license: MIT
	* @author: Steven Webb [xiaoancloud@outlook.com]
	* @website: https://etwig.grinecraft.net
	* @function: The repository for Portfolio model.
	*/

package net.etwig.webapp.repository;

import net.etwig.webapp.model.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PortfolioRepository extends JpaRepository<Portfolio, Long>, JpaSpecificationExecutor<Portfolio> {

	//Optional<Portfolio> findById(long id);
    
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