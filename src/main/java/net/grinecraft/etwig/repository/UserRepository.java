package net.grinecraft.etwig.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.grinecraft.etwig.model.User;

public interface UserRepository extends JpaRepository<User, Long>{
	User findByEmail(String email);
}
