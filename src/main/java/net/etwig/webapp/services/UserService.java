package net.etwig.webapp.services;

import jakarta.persistence.criteria.Predicate;
import net.etwig.webapp.dto.admin.UserListDTO;
import net.etwig.webapp.model.User;
import net.etwig.webapp.model.UserRole;
import net.etwig.webapp.repository.UserRepository;
import net.etwig.webapp.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Retrieves a paginated list of users with their positions, filtered by portfolio ID, role ID, and search user name.
     * The method also projects the required user and portfolio data.
     *
     * @param portfolioId the ID of the portfolio to filter users by (optional).
     * @param roleId the ID of the role to filter users by (optional).
     * @param searchUserName the name of the user to search for (optional).
     * @param pageable the pagination information.
     * @return a {@link Page} of {@link UserListDTO} containing user data and their positions.
     */

    public Page<UserListDTO> getAllUsersWithPositions(
            Long portfolioId,
            String roleId,
            String searchUserName,
            Pageable pageable
    ) {

        Specification<UserRole> spec = userCriteria(portfolioId, roleId, searchUserName);

        // Adjust this query to use specifications and properly project the required data
        Page<Object[]> rawData = userRoleRepository.findAll(spec, pageable).map(
                ur -> new Object[] {
                        ur.getUser().getId(),                   // 0: User ID
                        ur.getUser().getFullName(),             // 1: User Name
                        ur.getUser().getEmail(),                // 2: User Email
                        ur.getUser().getLastLogin(),            // 3: User Last Login
                        ur.getPosition(),                       // 4: Position Name
                        ur.getPortfolio().getName(),            // 5: Portfolio Name
                        ur.getPortfolio().getAbbreviation(),    // 6: Portfolio Abbreviation
                        ur.getPortfolio().getColor(),           // 7: Portfolio Color
                        ur.getEmail()                           // 8: Portfolio Email
                }
        );

        Map<Long, UserListDTO> users = new LinkedHashMap<>();

        rawData.forEach(objects -> {
            Long userId = (Long) objects[0];
            String userName = (String) objects[1];
            String userEmail = (String) objects [2];
            LocalDateTime userLastLogin = (LocalDateTime) objects[3];
            String positionName = (String) objects[4];
            String portfolioName = (String) objects[5];
            String portfolioAbbr = (String) objects[6];
            String portfolioColor = (String) objects[7];
            String portfolioEmail = (String) objects[8];

            UserListDTO userDto = users.computeIfAbsent(userId, id -> new UserListDTO());
            userDto.setUser(userId, userName, userEmail, userLastLogin);
            userDto.addPosition(
                    positionName,
                    (portfolioAbbr == null || portfolioAbbr.isEmpty()) ? portfolioName : portfolioName + " (" + portfolioAbbr +")",
                    portfolioColor,
                    portfolioEmail);
        });

        List<UserListDTO> dtos = new ArrayList<>(users.values());
        return new PageImpl<>(dtos, pageable, rawData.getTotalElements());
    }

    /**
     * Creates a {@link Specification} for filtering {@link UserRole} entities based on the given criteria.
     *
     * @param portfolioId the ID of the portfolio to filter by (optional).
     * @param roleId the ID of the role to filter by (optional).
     * @param searchUserName the name of the user to search for (optional).
     * @return a {@link Specification} for filtering {@link UserRole} entities.
     */

    public Specification<UserRole> userCriteria(Long portfolioId, String roleId, String searchUserName) {
        return (root, query, criteriaBuilder) -> {
            Predicate finalPredicate = criteriaBuilder.conjunction();

            // Filter by portfolioId
            if (portfolioId != null) {
                finalPredicate = criteriaBuilder.and(finalPredicate, criteriaBuilder.equal(root.get("portfolioId"), portfolioId));
            }

            // Filter by roleId
            if (roleId != null) {
                finalPredicate = criteriaBuilder.and(finalPredicate, criteriaBuilder.equal(root.get("roleId"), roleId));
            }

            // Search by username
            if (searchUserName != null && !searchUserName.isEmpty()) {
                Predicate searchPredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("user").get("fullName")), "%" + searchUserName.toLowerCase() + "%");
                finalPredicate = criteriaBuilder.and(finalPredicate, searchPredicate);
            }

            return finalPredicate;
        };
    }

    public void addUser(@RequestBody Map<String, Object> newUserInfo) {

        // Step 1: Add user information
        User user = new User();
        user.setFullName(newUserInfo.get("userFullName").toString());
        user.setEmail(newUserInfo.get("userEmail").toString());

        String encodedPassword = (new BCryptPasswordEncoder()).encode(newUserInfo.get("userPassword").toString());
        user.setPassword(encodedPassword);
        user.setLastLogin(LocalDateTime.now());
        Long addedUserId = userRepository.save(user).getId();

        // Step 2: Add initial position
        UserRole userRole = new UserRole();
        userRole.setUserId(addedUserId);
        userRole.setRoleId(Long.parseLong(newUserInfo.get("userSystemRole").toString()));
        userRole.setPortfolioId(Long.parseLong(newUserInfo.get("userPortfolio").toString()));
        userRole.setEmail(newUserInfo.get("userPortfolioEmail").toString());
        userRole.setPosition(newUserInfo.get("userPosition").toString());
        userRoleRepository.save(userRole);
    }
}
