/**
 	* eTWIG - The event management software for Griffin Hall.
 	* @copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
	* @license: MIT
	* @author: Steven Webb [xiaoancloud@outlook.com]
	* @website: https://etwig.grinecraft.net
	* @function: The class that mapping to "event_option" table in the database.
	*/

package net.etwig.webapp.model;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Embeddable
public class EventOptionKey implements Serializable {
	
    @Serial
    private static final long serialVersionUID = 1L;
	private Long eventId;
    private Long optionId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EventOptionKey that)) return false;
        return Objects.equals(eventId, that.eventId) && Objects.equals(optionId, that.optionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventId, optionId);
    }
}
