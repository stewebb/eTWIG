package net.grinecraft.etwig.util;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;

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
}
