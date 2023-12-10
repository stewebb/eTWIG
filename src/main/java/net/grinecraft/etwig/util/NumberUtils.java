package net.grinecraft.etwig.util;

public class NumberUtils extends org.apache.commons.lang3.math.NumberUtils{
	
	/**
	 * Check whether an integer is a long number.
	 * @param str the String that need to be checked
	 * @return Result
	 */
	
	public static boolean isLong(String str) {
		
		if(str == null || str.length() == 0) {
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
	 * Convert a String to long without exception
	 * @param str The input String
	 * @return A long number if the String can be converted to Long. Null if the String cannot be converted to Long.
	 */
	
	public static Long safeCreateLong(String str) {
		try {
			return createLong(str);
		} catch (NumberFormatException e) {
			return null;
		}
	}
}
