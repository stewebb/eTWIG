package net.grinecraft.etwig.services.event;

import java.util.LinkedHashMap;

public interface EventInfo {
	public LinkedHashMap<String, Object> getEventById(long id, boolean showAllDetails);
}
