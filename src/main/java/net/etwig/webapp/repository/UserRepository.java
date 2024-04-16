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

import net.grinecraft.etwig.model.User;

public interface UserRepository extends JpaRepository<User, Long>{

	
	/**
	 * Find the user by email address.
	 * @param email
	 * @return
	 */
	
	User findByEmail(String email);
}
