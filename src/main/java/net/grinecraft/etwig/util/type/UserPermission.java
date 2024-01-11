/**
 	* eTWIG - The event management software for Griffin Hall.
 	* @copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
	* @license: MIT
	* @author: Steven Webb [xiaoancloud@outlook.com]
	* @website: https://etwig.grinecraft.net
	* @function: The user permissions that in the DB.
	*/

package net.grinecraft.etwig.util.type;

public enum UserPermission {
	
	EVENT_MANAGER("e"),
	GRAPHICS_MANAGER("g"),
	ADMINISTRATOR("a"),
	UNCATEGORIZED("u");
	
	private final String current;  
	
	private UserPermission(String string) {
		current = string;
	}
	
	public boolean equalsName(String other) {
        return current == other;
    }

    public String toString() {
       return this.current;
    }
    
    public static UserPermission fromString(String str) {
        for (UserPermission u : UserPermission.values()) {
            if (u.toString().equalsIgnoreCase(str)) {
                return u;
            }
        }
        return UNCATEGORIZED;
    }
}
