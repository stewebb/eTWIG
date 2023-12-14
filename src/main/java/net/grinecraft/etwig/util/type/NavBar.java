package net.grinecraft.etwig.util.type;

/**
 * Different navbar types.
 */

public enum NavBar {
	
	HOME("HOME"),
	TWIG("TWIG"),
	EVENT("EVENT"),
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
