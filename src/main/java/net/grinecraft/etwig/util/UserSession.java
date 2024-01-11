package net.grinecraft.etwig.util;

import java.util.LinkedHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpSession;
import lombok.Data;
import net.grinecraft.etwig.model.Portfolio;
import net.grinecraft.etwig.model.User;
import net.grinecraft.etwig.repository.UserRepository;
import net.grinecraft.etwig.services.UserRoleService;

@Component
public class UserSession {

	@Autowired
	private HttpSession session;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserRoleService userRoleService;
	
	private String email;
	
	public void put() {
		
		User user = userRepository.findByEmail(this.email);
		   
		if(user == null) {
			throw new IllegalStateException("User authentication successfully, but the information cannot be found in the database.");
		 }
		
		//HttpSession session = request.getSession(true);
		LinkedHashMap<Long, Portfolio> myPortfolios = userRoleService.getPortfoliosByUserId(user.getId());
		session.setAttribute("user", user);
		session.setAttribute("portfolio", myPortfolios);
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
}
