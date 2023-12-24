package net.grinecraft.etwig.model;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Embeddable
public class UserRoleKey implements Serializable {
	
    private static final long serialVersionUID = 1L;
	private Long userId;
    private Long portfolioId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserRoleKey)) return false;
        UserRoleKey that = (UserRoleKey) o;
        return Objects.equals(userId, that.userId) && Objects.equals(portfolioId, that.portfolioId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, portfolioId);
    }
}
