/**
 	* eTWIG - The event management software for Griffin Hall.
 	* @copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
	* @license: MIT
	* @author: Steven Webb [xiaoancloud@outlook.com]
	* @website: https://etwig.grinecraft.net
	* @function: The repository for UserRole model.
	*/

package net.etwig.webapp.repository;

import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import net.etwig.webapp.model.UserRole;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long>, JpaSpecificationExecutor<UserRole> {

	/**
	 * Finds all portfolios of a user based on the user's ID.
	 * This method retrieves a set of UserRole entities associated with the specified user ID.
	 *
	 * @param userId the ID of the user whose portfolios are to be retrieved
	 * @return a set of UserRole entities associated with the specified user
	 */
	
	Set<UserRole> findByUserId(Long userId);

	/**
	 * Retrieves all UserRole entities where the associated role has graphics or admin access.
	 * This method uses a custom JPQL query to find UserRoles where the role grants either graphics access or admin access.
	 *
	 * @return a set of UserRole entities with roles that have graphics or admin access
	 */

	@Query("SELECT u FROM UserRole u JOIN u.role r WHERE r.graphicsAccess = true OR r.adminAccess = true")
	Set<UserRole> getGraphicsManagers();

	@Query("SELECT u.user.id, u.user.fullName, u.id, u.position FROM UserRole u")
	Page<Object[]> findAllUsersWithPositions(Pageable pageable);
}
