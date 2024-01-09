/**
 	* eTWIG - The event management software for Griffin Hall.
 	* @copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
	* @license: MIT
	* @author: Steven Webb [xiaoancloud@outlook.com]
	* @website: https://etwig.grinecraft.net
	* @function: The repository for UserRole model.
	*/

package net.grinecraft.etwig.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.grinecraft.etwig.model.UserRole;
import net.grinecraft.etwig.model.UserRoleKey;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, UserRoleKey> {
	
	/**
	 * Find all portfolios of a user.
	 * @param userId
	 * @return
	 */
	
    List<UserRole> findByIdUserId(Long userId);
    
    /**
     * Find all users who are in the same given portfolio.
     * @param portfolioId
     * @return
     */
    
    List<UserRole> findByIdPortfolioId(Long portfolioId);
}
