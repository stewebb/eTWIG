/**
 	* eTWIG - The event management software for Griffin Hall.
 	* @copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
	* @license: MIT
	* @author: Steven Webb [xiaoancloud@outlook.com]
	* @website: https://etwig.grinecraft.net
	* @function: The repository for UserAuth model.
	*/

package net.grinecraft.etwig.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.grinecraft.etwig.model.UserAuth;

public interface UserAuthRepository extends JpaRepository<UserAuth, Long>{
	
	/**
	 * Find the user by email address.
	 * @param email
	 * @return
	 */
	
	UserAuth findByEmail(String email);
}
