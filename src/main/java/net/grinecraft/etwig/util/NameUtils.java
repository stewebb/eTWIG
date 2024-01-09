/**
 	* eTWIG - The event management software for Griffin Hall.
 	* @copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
	* @license: MIT
	* @author: Steven Webb [xiaoancloud@outlook.com]
	* @website: https://etwig.grinecraft.net
	* @function: All utilities for username, and other names.
	*/

package net.grinecraft.etwig.util;

public class NameUtils {

	/**
	 * Merge the name of a person to a single String.
	 * @param firstName
	 * @param middleName
	 * @param lastName
	 * @return The merged full name.
	 * @throws IllegalArgumentException when firstName or lastName is null or empty.
	 */
	
	public static String nameMerger(String firstName, String middleName, String lastName) {
		StringBuilder nameStr = new StringBuilder();
		
		// First Name doesn't allow null or empty.
		if(firstName == null || firstName.length() == 0) {
			throw new IllegalArgumentException("First name must not be empty!");
		}else {
			nameStr.append(firstName + " ");
		}
		
		// Middle Name allow null or empty.
		if(middleName != null && middleName.length() > 0) {
			nameStr.append(middleName + " ");
		}
		
		// Last Name doesn't allow null or empty.
		if(lastName == null || lastName.length() == 0) {
			throw new IllegalArgumentException("Last name must not be empty!");
		}else {
			nameStr.append(lastName);
		}
				
		return nameStr.toString();
	}
}
