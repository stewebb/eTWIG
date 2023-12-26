package net.grinecraft.etwig.util.type;

/**
 * Different navbar types.
 */

public enum EventTimeUnit {
	
	HOUR("h"),
	DAY("d"),
	WEEK("w"),
	MONTH("m"),
	CUSTOMIZE("c");
	
	private final String current;  
	
	private EventTimeUnit(String string) {
		current = string;
	}
	
	public boolean equalsName(String other) {
        return current == other;
    }

    public String toString() {
       return this.current;
    }
    
    public static EventTimeUnit fromString(String str) {
        for (EventTimeUnit u : EventTimeUnit.values()) {
            if (u.toString().equalsIgnoreCase(str)) {
                return u;
            }
        }
        return CUSTOMIZE;
    }
}
