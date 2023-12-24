package net.grinecraft.etwig.util.type;

/**
 * Different navbar types.
 */

public enum EventUnit {
	
	HOUR('h'),
	DAY('d'),
	WEEK('w'),
	MONTH('m'),
	CUSTOMIZE('c');
	
	private final char current;  
	
	private EventUnit(char c) {
		current = c;
	}
	
	public boolean equalsName(char other) {
        return current == other;
    }

    public char toChar() {
       return this.current;
    }
    
    public static EventUnit safeValueOf(char c) {
    	try {
    		return valueOf(String.valueOf(c));
    	} catch (NullPointerException | IllegalArgumentException e) {
    		return CUSTOMIZE;
    	}    	
    }
}
