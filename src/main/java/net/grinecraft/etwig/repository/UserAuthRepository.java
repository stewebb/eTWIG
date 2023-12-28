/**
	 * eTWIG - The event and banner management software for residential halls and student unions.
	 * @copyright: Copyright (c) 2024 Steven Webb, eTWIG developers [etwig@grinecraft.net]
	 * @license: MIT
	 * @author: Steven Webb [xiaoancloud@outlook.com]
	 * @website: https://etwig.grinecraft.net
	 * @function: The repository for UserAuth model.
	 */

package net.grinecraft.etwig.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.grinecraft.etwig.model.UserAuth;

public interface UserAuthRepository extends JpaRepository<UserAuth, Long>{
	UserAuth findByEmail(String email);
}
