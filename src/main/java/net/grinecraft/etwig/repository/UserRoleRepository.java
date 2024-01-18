/**
 	* eTWIG - The event management software for Griffin Hall.
 	* @copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
	* @license: MIT
	* @author: Steven Webb [xiaoancloud@outlook.com]
	* @website: https://etwig.grinecraft.net
	* @function: The repository for UserRole model.
	*/

package net.grinecraft.etwig.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.grinecraft.etwig.model.UserRole;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
	
	/**
	 * Find all portfolios of a user.
	 * @param userId
	 * @return
	 */
	
	Set<UserRole> findByUser(Long userId);
    
    /**
     * Find all users who are in the same given portfolio.
     * @param portfolioId
     * @return
     */
    
	Set<UserRole> findByPortfolio(Long portfolioId);
}
