package net.grinecraft.etwig.services.event;

public class EventInfoFactory {
	
	/**
	 * Select an event type.
	 * @param isRecurring True The event that want to search is an recurring event. False That event is a single time event.
	 * @return The event information instance.
	 */
	
	public EventInfo selectEvent(boolean isRecurring) {
		return isRecurring ? new RecurringEventInfo() : new SingleTimeEventInfo();
	}
}
