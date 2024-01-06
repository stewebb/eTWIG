/**
	 * eTWIG - The event management software for university communities.
	 * @copyright: Copyright (c) 2024 Steven Webb
	 * @license: MIT
	 * @author: Steven Webb [xiaoancloud@outlook.com]
	 * @website: https://etwig.grinecraft.net
	 * @function: The repository for Property model.
	 */

package net.grinecraft.etwig.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import net.grinecraft.etwig.model.Option;

@Repository
public interface OptionRepository extends JpaRepository<Option, Long> {
	
    public List<Option> findAll();
    
    @Query("SELECT o.propertyId, COUNT(o) FROM Option o GROUP BY o.propertyId")
    List<Object[]> groupByProperty();
    
}