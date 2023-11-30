package net.grinecraft.etwig.Util;

public class NumberUtils extends org.apache.commons.lang3.math.NumberUtils{
	
	/**
	 * Check whether an integer is long
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
}
