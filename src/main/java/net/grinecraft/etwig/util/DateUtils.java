package net.grinecraft.etwig.util;

import java.util.Date;

/**
 * The util class about dates, based on Apache Commons.
 */

public class DateUtils extends org.apache.commons.lang3.time.DateUtils {
	
	/**
	 * Parse date without throw an exception.
	 * @param dateStr The date in String format.
	 * @param patternStr The pattern String.
	 * @return Date if the dateStr is well-formed. Null otherwise.
	 */
	
	public static Date safeParseDate(String dateStr, String patternStr) {
		try {
			return DateUtils.parseDate(dateStr, patternStr);
		} catch (Exception e) {
			return null;
		}
	}
}
