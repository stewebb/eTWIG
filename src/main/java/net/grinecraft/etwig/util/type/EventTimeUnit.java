/**
 	* eTWIG - The event management software for Griffin Hall.
 	* @copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
	* @license: MIT
	* @author: Steven Webb [xiaoancloud@outlook.com]
	* @website: https://etwig.grinecraft.net
	* @function: The time units for events.
	*/

package net.grinecraft.etwig.util.type;

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
