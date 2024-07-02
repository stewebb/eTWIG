package net.etwig.webapp.services;

import jakarta.persistence.criteria.Predicate;
import net.etwig.webapp.dto.admin.UserListDTO;
import net.etwig.webapp.model.UserRole;
import net.etwig.webapp.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    private UserRoleRepository userRoleRepository;

    public Page<UserListDTO> getAllUsersWithPositions(Long portfolioId, String roleId, Pageable pageable) {

        Specification<UserRole> spec = userCriteria(portfolioId, roleId);

        // Adjust this query to use specifications and properly project the required data
        Page<Object[]> rawData = userRoleRepository.findAll(spec, pageable).map(
                ur -> new Object[] {
                        ur.getUser().getId(),           // User ID
                        ur.getUser().getFullName(),     // User Name
                        ur.getId(),                     // Position ID
                        ur.getPosition()                // Position Name
                }
        );

        // TODO
        //Page<Object[]> rawData = userRoleRepository.findAllUsersWithPositions(pageable);
        Map<Long, UserListDTO> users = new LinkedHashMap<>();

        rawData.forEach(objects -> {
            Long userId = (Long) objects[0];
            String userName = (String) objects[1];
            Long positionId = (Long) objects[2];
            String positionName = (String) objects[3];

            UserListDTO userDto = users.computeIfAbsent(userId, id -> new UserListDTO());
            userDto.setUser(userId, userName);
            userDto.addPosition(positionId, positionName);
        });

        List<UserListDTO> dtos = new ArrayList<>(users.values());
        return new PageImpl<>(dtos, pageable, rawData.getTotalElements());
    }

    public Specification<UserRole> userCriteria(Long portfolioId, String roleId) {
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

            return finalPredicate;
        };
    }
}
