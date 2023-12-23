package net.grinecraft.etwig.util.type;

/**
 * Different navbar types.
 */

public enum NavBar {
	
	DASHBOARD("DASHBOARD"),
	TWIG("TWIG"),
	CALENDAR("CALENDAR"),
	EDIT_EVENT("EDIT_EVENT"),
	ADD_EVENT("ADD_EVENT"),
	LOGIN("LOGIN"),
	OTHER("OTHER");

	private final String current;  
	
	private NavBar(String str) {
		current = str;
	}
	
	public boolean equalsName(String other) {
        return current.equals(other);
    }

    public String toString() {
       return this.current;
    }
}
