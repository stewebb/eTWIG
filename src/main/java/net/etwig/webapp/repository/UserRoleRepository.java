/**
 	* eTWIG - The event management software for Griffin Hall.
 	* @copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
	* @license: MIT
	* @author: Steven Webb [xiaoancloud@outlook.com]
	* @website: https://etwig.grinecraft.net
	* @function: The repository for UserRole model.
	*/

package net.grinecraft.etwig.repository;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import net.grinecraft.etwig.dto.PositionDTO;
import net.grinecraft.etwig.model.UserRole;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
	
	@NonNull
	Optional<UserRole> findById(@NonNull Long id);
	
	/**
	 * Find all portfolios of a user.
	 * @param userId
	 * @return
	 */
	
	Set<UserRole> findByUserId(Long userId);
    
    /**
     * Find all users who are in the same given portfolio.
     * @param portfolioId
     * @return
     */
    
	Set<UserRole> findByPortfolioId(Long portfolioId);
	
	@Query("SELECT new net.grinecraft.etwig.dto.PositionDTO(u) FROM UserRole u WHERE u.userId = :userId")
	Set<PositionDTO> getPositionsByUserId(Long userId);
	
	@Query("SELECT new net.grinecraft.etwig.dto.PositionDTO(u) FROM UserRole u JOIN u.role r WHERE r.graphicsAccess = true OR r.adminAccess = true")
	Set<PositionDTO> getGraphicsManagers();
}
