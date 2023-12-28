/**
	 * eTWIG - The event and banner management software for residential halls and student unions.
	 * @copyright: Copyright (c) 2024 Steven Webb, eTWIG developers [etwig@grinecraft.net]
	 * @license: MIT
	 * @author: Steven Webb [xiaoancloud@outlook.com]
	 * @website: https://etwig.grinecraft.net
	 * @function: The navbar types in a page.
	 */

package net.grinecraft.etwig.util.type;


public enum NavBar {
	
	DASHBOARD("DASHBOARD"),
	TWIG("TWIG"),
	CALENDAR("CALENDAR"),
	EDIT_EVENT("EDIT_EVENT"),
	ADD_EVENT("ADD_EVENT"),
	DELETE_EVENT("DELETE_EVENT"),
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
