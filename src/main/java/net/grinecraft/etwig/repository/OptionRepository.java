/**
 	* eTWIG - The event management software for Griffin Hall.
 	* @copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
	* @license: MIT
	* @author: Steven Webb [xiaoancloud@outlook.com]
	* @website: https://etwig.grinecraft.net
	* @function: The repository for Property model.
	*/

package net.grinecraft.etwig.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.grinecraft.etwig.model.Option;

@Repository
public interface OptionRepository extends JpaRepository<Option, Long> {
	
	/**
	 * Find all options in the database.
	 */
	
    //public List<Option> findAll();
    
}