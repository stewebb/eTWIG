/**
	 * eTWIG - The event and banner management software for residential halls and student unions.
	 * @copyright: Copyright (c) 2024 Steven Webb, eTWIG developers [etwig@grinecraft.net]
	 * @license: MIT
	 * @author: Steven Webb [xiaoancloud@outlook.com]
	 * @website: https://etwig.grinecraft.net
	 * @function: All utilities for date and time, based on Apache Commons. 
	 */

package net.grinecraft.etwig.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;

import net.grinecraft.etwig.util.type.EventTimeUnit;

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
			return null;
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
    
    /**
     * Calculate the event end time
     * @param timeUnit One of the elements of EventTimeUnit Enum
     * @param startTime Event start time in LocalDateTime format
     * @param multiplier The duration
     * @return Calculated event end time, in LocalDateTime format.
     */
    
    public static LocalDateTime calculateEndTime(EventTimeUnit timeUnit, LocalDateTime startTime, int multiplier) {
    	
    	float totalMinutes = 0;
    	switch (timeUnit) {
        	case HOUR:
        		totalMinutes = multiplier * 60;
        		break;
        	case DAY:
        		totalMinutes = multiplier * 1440; // 24 * 60
        		break;
        	case WEEK:
        		totalMinutes = multiplier * 10080; // 7 * 24 * 60
        		break;
        	case MONTH:
        		totalMinutes = multiplier * 43200; // 30 days in a month
        		break;
    		default:
    			totalMinutes = multiplier;
    		break;
    	}

    	// Keeping only the integer part of the total minutes
    	long minutesToAdd = (long) totalMinutes;
    	return startTime.plusMinutes(minutesToAdd);
    }
}
