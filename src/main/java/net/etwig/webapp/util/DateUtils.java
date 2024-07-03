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
	 * Parses a date string according to the specified pattern and returns the corresponding LocalDate.
	 * This method throws an InvalidParameterException if the input date string does not conform to the specified pattern.
	 *
	 * @param dateStr The date in String format.
	 * @param patternStr The pattern String specifying the format of the dateStr.
	 * @return LocalDate representing the parsed date if the dateStr is well-formed according to the patternStr.
	 * @throws InvalidParameterException if the date string does not match the pattern string.
	 */
	
	public static LocalDate safeParseDate(String dateStr, String patternStr) {
		try {
			Date date = DateUtils.parseDate(dateStr, patternStr);
			return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		} catch (Exception e) {
	        throw new InvalidParameterException("Invalid date format: " + dateStr + ", which is not fit in the pattern:" + patternStr);
		}
	}
	
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
