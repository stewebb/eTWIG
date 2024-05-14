/**
 	* eTWIG - The event management software for Griffin Hall.
 	* @copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
	* @license: MIT
	* @author: Steven Webb [xiaoancloud@outlook.com]
	* @website: https://etwig.grinecraft.net
	* @function: All utilities for numbers, based on Apache Commons. 
	*/

package net.etwig.webapp.util;

public class NumberUtils extends org.apache.commons.lang3.math.NumberUtils{

	/**
	 * Checks if a given string can be parsed as a long integer.
	 *
	 * @param str the string to be checked
	 * @return true if the string can be parsed as a long integer, false otherwise
	 */
	
	public static boolean isLong(String str) {
		if(str == null || str.isEmpty()) {
			return false;
		}
		
		try {
			Long.parseLong(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	/**
	 * Converts a String to a Long without throwing an exception.
	 *
	 * @param str The input String
	 * @return A Long number if the String can be converted to Long, or null if the String cannot be converted.
	 *         If the input String is null, returns null directly.
	 */
	
	public static Long safeCreateLong(String str) {
		try {
	        return str != null ? Long.valueOf(str) : null;
	    } catch (NumberFormatException e) {
	        return null;
	    }
	}
}
