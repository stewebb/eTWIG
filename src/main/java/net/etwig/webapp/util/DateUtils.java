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
import java.time.Duration;
import java.util.Date;
import java.time.temporal.TemporalAdjusters;

import net.grinecraft.etwig.util.exception.InvalidDateFormatException;


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
	
	/**
	 * Find Next Monday.
	 * @param date A given date in LocalDate format.
	 * @return The next Monday of this given date in LocalDate format.
	 */
	
    public static LocalDate findNextMonday(LocalDate date) {
        return date.with(TemporalAdjusters.next(java.time.DayOfWeek.MONDAY));
    }
    
    /**
	 * Find the first day of last month.
	 * @param date A given date in LocalDate format.
	 * @return The first day of last month of this given date in LocalDate format.
	 */
    
    public static LocalDate findFirstDayOfLastMonth(LocalDate date) {
        return date.withDayOfMonth(1).minusMonths(1);
    }
    
    /**
	 * Find the first day of this month.
	 * @param date A given date in LocalDate format.
	 * @return The first day of this month of this given date in LocalDate format.
	 */
    
    public static LocalDate findFirstDayOfThisMonth(LocalDate date) {
        return date.withDayOfMonth(1);
    }
    
    /**
   	 * Find the first day of next month.
   	 * @param date A given date in LocalDate format.
   	 * @return The first day of next month of this given date in LocalDate format.
   	 */
    
    public static LocalDate findFirstDayOfNextMonth(LocalDate date) {
        return date.withDayOfMonth(1).plusMonths(1);
    }
    
    /**
   	 * Find the tomorrow date.
   	 * @param date A given date in LocalDate format.
   	 * @return Tomorrow date in LocalDate format.
   	 */
    
    public static LocalDate findTomorrow(LocalDate date) {
		return date.plusDays(1);
    }
    
    
    public static String timeAgo(LocalDateTime date) {
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(date, now);

        if (duration.isNegative()) {
            return "In the future";
        }

        long seconds = duration.getSeconds();
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;
        long months = days / 30;
        long years = months / 12;

        if (years > 0) return years + " years ago";
        if (months > 0) return months + " months ago";
        if (days > 0) return days + " days ago";
        if (hours > 0) return hours + " hours ago";
        if (minutes > 0) return minutes + " minutes ago";
        return "Just now";
    }
    
   
    
}
