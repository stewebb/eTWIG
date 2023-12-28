/**
	 * eTWIG - The event and banner management software for residential halls and student unions.
	 * @copyright: Copyright (c) 2024 Steven Webb, eTWIG developers [etwig@grinecraft.net]
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
	
    List<UserRole> findByIdUserId(Long userId);
    
    List<UserRole> findByIdPortfolioId(Long portfolioId);
}
