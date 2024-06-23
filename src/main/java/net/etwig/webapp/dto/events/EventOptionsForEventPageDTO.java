package net.etwig.webapp.dto.events;

import lombok.Getter;
import lombok.ToString;
import net.etwig.webapp.model.Option;

@Getter
@ToString
public class EventOptionsForEventPageDTO {

    private final Long optionId;
    private final String name;
    private final Long belongsToPropertyId;
    private boolean selected;

    public EventOptionsForEventPageDTO(Option option) {
        this.optionId = option.getId();
        this.name = option.getName();
        this.belongsToPropertyId = option.getBelongsToId();

        // By default, the option is not selected.
        this.selected = false;
    }

    public void select() {
        this.selected = true;
    }
}
