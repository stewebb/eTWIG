package net.etwig.webapp.services;

import jakarta.persistence.criteria.Predicate;
import net.etwig.webapp.dto.admin.UserListDTO;
import net.etwig.webapp.dto.user.CurrentUserBasicInfoDTO;
import net.etwig.webapp.model.User;
import net.etwig.webapp.model.UserRole;
import net.etwig.webapp.repository.UserRepository;
import net.etwig.webapp.repository.UserRoleRepository;
import net.etwig.webapp.util.InvalidParameterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.*;

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

    /**
     * Adds a new user and their associated roles to the database.
     * <p>
     * This method first checks if a user with the provided email already exists in the database. If the user already exists,
     * the method returns {@code false}. If the user does not exist, it creates a new {@link User} instance by extracting
     * information from a provided map, encoding the user's password using BCrypt, and setting the user's last login time to
     * the current moment. It then stores this user in the repository. Following the user creation, it sets up initial user
     * roles based on the provided data, associating these roles with the newly created user, and returns {@code true}.
     * </p>
     *
     * @param newUserInfo A {@link Map} containing user information such as full name, email, password,
     *                    system role, portfolio, and position details. Keys in this map should include:
     *                    <ul>
     *                      <li>userFullName: String specifying the user's full name.</li>
     *                      <li>userEmail: String specifying the user's email address.</li>
     *                      <li>userPassword: String specifying the user's password (will be encoded).</li>
     *                      <li>userSystemRole: Long or String convertible to Long specifying the user's role ID.</li>
     *                      <li>userPortfolio: Long or String convertible to Long specifying the user's portfolio ID.</li>
     *                      <li>userPortfolioEmail: String specifying the email associated with the user's portfolio.</li>
     *                      <li>userPosition: String specifying the user's position.</li>
     *                    </ul>
     * @return {@code true} if the user was successfully added; {@code false} if a user with the same email already exists.
     * @throws IllegalArgumentException if required keys are missing in the newUserInfo map or if the values cannot
     *                                  be correctly parsed or converted.
     */

    public Boolean addUser(@RequestBody Map<String, Object> newUserInfo) {

        // Step 1: Check if user is existing
        String userEmail = newUserInfo.get("userEmail").toString();
        //User existingUser = userRepository.findByEmail(userEmail);
        //if (existingUser != null) {
        //    return false;
        //}

        if (findByEmail(userEmail) != null) {
            return false;
        }

        // Step 2: Add user information
        User user = new User();
        user.setFullName(newUserInfo.get("userFullName").toString());
        user.setEmail(userEmail);

        String encodedPassword = (new BCryptPasswordEncoder()).encode(newUserInfo.get("userPassword").toString());
        user.setPassword(encodedPassword);
        user.setLastLogin(LocalDateTime.now());
        Long addedUserId = userRepository.save(user).getId();

        // Step 3: Add initial position
        UserRole userRole = new UserRole();
        userRole.setUserId(addedUserId);
        userRole.setRoleId(Long.parseLong(newUserInfo.get("userSystemRole").toString()));
        userRole.setPortfolioId(Long.parseLong(newUserInfo.get("userPortfolio").toString()));
        userRole.setEmail(newUserInfo.get("userPortfolioEmail").toString());
        userRole.setPosition(newUserInfo.get("userPosition").toString());
        userRoleRepository.save(userRole);

        return true;
    }

    /**
     * Retrieves a user by their unique identifier and converts their data into a DTO format.
     * This method utilizes the repository to query the database for a user by ID. If found, it transforms
     * the user data into a {@link CurrentUserBasicInfoDTO} instance using a constructor reference.
     * If no user is found with the given ID, the method returns null.
     *
     * @param userId The unique identifier of the user to be retrieved. Must not be null.
     * @return {@link CurrentUserBasicInfoDTO} containing user's basic information if the user exists,
     *         otherwise returns null.
     * @throws IllegalArgumentException if the userId parameter is null.
     */

    public CurrentUserBasicInfoDTO findById(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        return user.map(CurrentUserBasicInfoDTO::new).orElse(null);
    }

    /**
     * Updates user details based on the provided map of user information. This method
     * finds the user by ID, updates their name and email, and conditionally updates the password if provided and non-blank.
     * The user's password is encrypted before being stored if it is updated.
     *
     * @param userInfo A map containing user details such as userId, userFullName, userEmail, and userPassword.
     *                 The userId is used to locate the user in the database.
     *                 - userId: The ID of the user to update.
     *                 - userFullName: The new full name of the user.
     *                 - userEmail: The new email of the user.
     *                 - userPassword: A new password for the user, which will be encrypted if provided.
     * @return Boolean True if the user's password was updated, otherwise False.
     * @throws InvalidParameterException If no user exists with the given ID or if any other parameter issues occur.
     */

    public Boolean changeUserDetails (@RequestBody Map<String, Object> userInfo) {

        // Step 1: Find the target user
        Long userId = Long.parseLong(userInfo.get("userId").toString());
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            throw new InvalidParameterException("User with id=" + userId + " does not exist!");
        }

        // Step 2: Update user details
        user.setFullName(userInfo.get("userFullName").toString());
        user.setEmail(userInfo.get("userEmail").toString());

        // Step 3: Update user password if it is not blank
        boolean passwordUpdated = false;
        String userPassword = userInfo.get("userPassword").toString();
        if (userPassword != null && !userPassword.isEmpty()) {
            user.setPassword((new BCryptPasswordEncoder()).encode(userPassword));
            passwordUpdated =  true;
        }

        userRepository.save(user);
        return passwordUpdated;
    }

    /**
     * Checks if a user exists in the database by their email address.
     * This method queries the database using the provided email and returns a boolean
     * indicating whether a user with that email exists.
     *
     * @param email The email address to check against existing user records.
     * @return Boolean True if a user with the given email exists, otherwise False.
     * @throws IllegalArgumentException If the provided email is null or empty, ensuring
     *                                  that the query is only run with valid data.
     */

    public User findByEmail (String email) {
        return userRepository.findByEmail(email);
    }
}
