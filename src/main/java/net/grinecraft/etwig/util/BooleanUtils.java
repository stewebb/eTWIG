package net.grinecraft.etwig.util;

public class BooleanUtils extends org.apache.commons.lang3.BooleanUtils{

	/**
	 * Convert a String to boolean, which is based on Apache Commons BooleanUtils.toBoolean() method.
	 * However, if input is null or empty, the output is true! (different than that in Apache Commons)
	 * 'true', 'on', 'y', 't', 'yes' (case insensitive), null and empty String will return true. Otherwise, false is returned.
	 * @param str A String.
	 * @return The boolean form of that String.
	 */
	
	public static boolean toBooleanNullTrue(String str) {
		if(str == null || str.length() == 0) {
			return true;
		}
		return BooleanUtils.toBoolean(str);
	}
}
