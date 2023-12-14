package net.grinecraft.etwig.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import net.grinecraft.etwig.model.UserAuth;
import net.grinecraft.etwig.repository.UserAuthRepository;

@Service
public class UserAuthService implements UserDetailsService {

	@Autowired
	private UserAuthRepository userAuthRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		UserAuth userAuth = userAuthRepository.findByEmail(email);
        if (userAuth == null) {
        	throw new UsernameNotFoundException("User not found");
        }
        return new org.springframework.security.core.userdetails.User(userAuth.getEmail(), userAuth.getPassword(), new ArrayList<>());
	}
}
