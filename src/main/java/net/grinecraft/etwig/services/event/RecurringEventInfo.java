package net.grinecraft.etwig.services.event;

import java.util.LinkedHashMap;

import net.grinecraft.etwig.util.DataIntegrityViolationException;

public class RecurringEventInfo implements EventInfo {

	/**
	 * Get all details related to a recurring event by it's id.
	 * @param id The id of that event.
	 * @param showAllDetails True to show all details, false to show brief information.
	 * @return A linkedHaskMap about the details of the event. If event doesn't exist, return null.
	 * @throws DataIntegrityViolationException If the violation of the data integrity is detected.
	 */
	
	@Override
	public LinkedHashMap<String, Object> getEventById(long id, boolean showAllDetails) {
		// TODO Auto-generated method stub
		return null;
	}

}
