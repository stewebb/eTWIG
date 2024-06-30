/**
 	* eTWIG - The event management software for Griffin Hall.
 	* @copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
	* @license: MIT
	* @author: Steven Webb [xiaoancloud@outlook.com]
	* @website: https://etwig.grinecraft.net
	* @function: All utilities for date and time, based on Apache Commons. 
	*/

package net.etwig.webapp.util;

import java.time.*;
import java.util.Date;
import java.time.temporal.TemporalAdjusters;


/**
 * The util class about dates, based on Apache Commons.
 */

public class DateUtils extends org.apache.commons.lang3.time.DateUtils {
	
	/**
	 * Parse date without throw an exception.
	 * @param dateStr The date in String format.
	 * @param patternStr The pattern String.
	 * @return LocalDate if the dateStr is well-formed. Null otherwise.
	 */
	
	public static LocalDate safeParseDate(String dateStr, String patternStr) {
		try {
			Date date = DateUtils.parseDate(dateStr, patternStr);
			return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		} catch (Exception e) {
	        throw new InvalidDateFormatException("Invalid date format: " + dateStr + ", which is not fit in the pattern:" + patternStr);
		}
	}

	//public static LocalDate parseDate(String dateStr, String patternStr) {
	//	Date date = DateUtils.parseDate(dateStr, patternStr);
	//	return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	//}
	
	/**
	 * Parse date and time without throw an exception.
	 * @param dateTimeStr The datetime in String format.
	 * @param patternStr The pattern String.
	 * @return LocalDateTime if the dateTimeStr is well-formed. Null otherwise.
	 */
	
	public static LocalDateTime safeParseDateTime(String dateTimeStr, String patternStr) {
		try {
			Date date = DateUtils.parseDate(dateTimeStr, patternStr);
			return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * Find This Monday.
	 * @param date A given date in LocalDate format.
	 * @return The Monday of this given date in LocalDate format.
	 */
	
	public static LocalDate findThisMonday(LocalDate date) {
        return date.with(TemporalAdjusters.previousOrSame(java.time.DayOfWeek.MONDAY));
    }


}
