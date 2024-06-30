/**
 	* eTWIG - The event management software for Griffin Hall.
 	* @copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
	* @license: MIT
	* @author: Steven Webb [xiaoancloud@outlook.com]
	* @website: https://etwig.grinecraft.net
	* @function: The repository for User model.
	*/

package net.etwig.webapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.etwig.webapp.model.User;

/**
 * Repository interface for accessing user data.
 * <p>
 * This interface extends {@link JpaRepository} to provide standard CRUD operations
 * and includes custom methods for finding users by email and by username and email.
 * </p>
 */

public interface UserRepository extends JpaRepository<User, Long> {

	/**
	 * Finds a user by their email address.
	 *
	 * @param email the email address of the user to find.
	 * @return the {@link User} with the specified email address, or {@code null} if no user is found.
	 */

    User findByEmail(String email);

	/**
	 * Finds a user by their username and email address.
	 *
	 * @param username the username of the user to find.
	 * @param email the email address of the user to find.
	 * @return the {@link User} with the specified username and email address, or {@code null} if no user is found.
	 */

	User findByUsernameAndEmail(String username, String email);
}
