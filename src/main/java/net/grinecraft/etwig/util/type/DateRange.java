/**
	 * eTWIG - The event and banner management software for residential halls and student unions.
	 * @copyright: Copyright (c) 2024 Steven Webb, eTWIG developers [etwig@grinecraft.net]
	 * @license: MIT
	 * @author: Steven Webb [xiaoancloud@outlook.com]
	 * @website: https://etwig.grinecraft.net
	 * @function: Three different date types when finding events.
	 */

package net.grinecraft.etwig.util.type;

public enum DateRange {
	
	MONTH("MONTH"),
	WEEK("WEEK"),
	DAY("DAY");

	private final String range;  
	private final static DateRange defaultRange = DAY;
	
	private DateRange(String str) {
		range = str;
	}
	
	public boolean equalsName(String other) {
        return range.equals(other);
    }

    public String toString() {
       return this.range;
    }
    
    /**
     * A safe version valueOf to avoid NullPointerException and IllegalArgumentException.
     * @param str A String
     * @return The enum value of that String if the String is valid. The defaultRange if the String is invalid
     */
    
    public static DateRange safeValueOf(String str) {
    	try {
    		return valueOf(str.toUpperCase());	// Convert to upper case.
    	} catch (NullPointerException | IllegalArgumentException e) {
    		return defaultRange;
    	}    	
    }
}
