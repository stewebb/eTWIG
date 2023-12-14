package net.grinecraft.etwig.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.grinecraft.etwig.model.UserAuth;

public interface UserAuthRepository extends JpaRepository<UserAuth, Long>{
	UserAuth findByEmail(String email);
}
