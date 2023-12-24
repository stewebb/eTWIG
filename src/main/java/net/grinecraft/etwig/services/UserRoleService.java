package net.grinecraft.etwig.services;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.grinecraft.etwig.model.Portfolio;
import net.grinecraft.etwig.model.User;
import net.grinecraft.etwig.model.UserRole;
import net.grinecraft.etwig.repository.PortfolioRepository;
import net.grinecraft.etwig.repository.UserRepository;
import net.grinecraft.etwig.repository.UserRoleRepository;

@Service
public class UserRoleService {
	
    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PortfolioRepository portfolioRepository;

    /*
    public List<Portfolio> getPortfoliosByUserId(Long userId) {
        List<UserRole> roles = userRoleRepository.findByIdUserId(userId);
        return roles.stream()
                    .map(role -> portfolioRepository.findById(role.getId().getPortfolioId()).orElse(null))
                    .collect(Collectors.toList());
    }

    public List<User> getUsersByPortfolioId(Long portfolioId) {
        List<UserRole> roles = userRoleRepository.findByIdPortfolioId(portfolioId);
        return roles.stream()
                    .map(role -> userRepository.findById(role.getId().getUserId()).orElse(null))
                    .collect(Collectors.toList());
    }
    
    */
    
    public LinkedHashMap<Long, Portfolio> getPortfoliosByUserId(Long userId) {
        List<UserRole> roles = userRoleRepository.findByIdUserId(userId);
        LinkedHashMap<Long, Portfolio> portfoliosMap = new LinkedHashMap<>();

        for (UserRole role : roles) {
            Long PortfolioId = role.getId().getPortfolioId();
            portfoliosMap.computeIfAbsent(PortfolioId, id -> portfolioRepository.findById(id).orElse(null));
        }

        return portfoliosMap;
    }

    public LinkedHashMap<Long, User> getUsersByPortfolioId(Long portfolioId) {
        List<UserRole> roles = userRoleRepository.findByIdPortfolioId(portfolioId);
        LinkedHashMap<Long, User> usersMap = new LinkedHashMap<>();

        for (UserRole role : roles) {
            Long userId = role.getId().getUserId();
            usersMap.computeIfAbsent(userId, id -> userRepository.findById(id).orElse(null));
        }

        return usersMap;
    }

}
