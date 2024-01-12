package net.grinecraft.etwig.util;

import java.time.LocalDate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DateUtilsTest {

	/**
	 * safeParseDate() method.
	 */
	
	@Test
	public void testSafeParseDateInputNullOutputNull(){
		Assertions.assertNull(DateUtils.safeParseDate(null, "yyyy-mm-dd"));
	}
	
	@Test
	public void testSafeParseDateInputNotWellFormedOutputNull() {
		Assertions.assertNull(DateUtils.safeParseDate("1970.01.01", "yyyy-mm-dd"));
	}
	
	@Test
	public void testSafeParseDateValidInput(){
		LocalDate date = DateUtils.safeParseDate("1970-01-02", "yyyy-mm-dd");
		Assertions.assertEquals(1970, date.getYear());
		Assertions.assertEquals(1, date.getMonthValue());
		Assertions.assertEquals(2, date.getDayOfMonth());
	}
	
	/**
	 * findThisMonday() method.
	 */
	
    @Test
    public void testFindThisMondayTodayIsMonday() {
        LocalDate inputDate = LocalDate.of(2023, 12, 4);
        LocalDate expected = LocalDate.of(2023, 12, 4);
        Assertions.assertEquals(expected, DateUtils.findThisMonday(inputDate));
    }

    @Test
    public void testFindThisMondayTodayIsWednesday() {
        LocalDate inputDate = LocalDate.of(2023, 12, 6);
        LocalDate expected = LocalDate.of(2023, 12, 4);
        Assertions.assertEquals(expected, DateUtils.findThisMonday(inputDate));
    }

    @Test
    public void testFindThisMondayTodayIsSunday1() {
        LocalDate inputDate = LocalDate.of(2023, 12, 10);
        LocalDate expected = LocalDate.of(2023, 12, 4);
        Assertions.assertEquals(expected, DateUtils.findThisMonday(inputDate));
    }

    @Test
    public void testFindThisMondayTodayIsSunday2() {
        LocalDate inputDate = LocalDate.of(2023, 12, 3);
        LocalDate expected = LocalDate.of(2023, 11, 27);
        Assertions.assertEquals(expected, DateUtils.findThisMonday(inputDate));
    }
    
	/**
	 * findNextMonday() method.
	 */
    
    @Test
    public void testFindNextMondayTodayIsMonday() {
        LocalDate inputDate = LocalDate.of(2023, 12, 4);
        LocalDate expected = LocalDate.of(2023, 12, 11);
        Assertions.assertEquals(expected, DateUtils.findNextMonday(inputDate));
    }

    @Test
    public void testFindNextMondayTodayIsThursday() {
        LocalDate inputDate = LocalDate.of(2023, 12, 7);
        LocalDate expected = LocalDate.of(2023, 12, 11);
        Assertions.assertEquals(expected, DateUtils.findNextMonday(inputDate));
    }

    @Test
    public void testFindNextMondayTodayIsSunday1() {
        LocalDate inputDate = LocalDate.of(2023, 12, 10);
        LocalDate expected = LocalDate.of(2023, 12, 11);
        Assertions.assertEquals(expected, DateUtils.findNextMonday(inputDate));
    }

    @Test
    public void testFindNextMondayTodayIsSunday2() {
        LocalDate inputDate = LocalDate.of(2023, 12, 3);
        LocalDate expected = LocalDate.of(2023, 12, 4);
        Assertions.assertEquals(expected, DateUtils.findNextMonday(inputDate));
    }
    
    /**
	 * findFirstDayOfThisMonth() method.
	 */
    
    @Test
    public void testFindFirstDayOfThisMonth() {
        LocalDate inputDate = LocalDate.of(2023, 12, 15);
        LocalDate expected = LocalDate.of(2023, 12, 1);
        Assertions.assertEquals(expected, DateUtils.findFirstDayOfThisMonth(inputDate));
    }

    /**
   	 * findFirstDayOfNextMonth() method.
   	 */
    
    @Test
    public void testFindFirstDayOfNextMonth() {
        LocalDate inputDate = LocalDate.of(2023, 12, 15);
        LocalDate expected = LocalDate.of(2024, 1, 1);
        Assertions.assertEquals(expected, DateUtils.findFirstDayOfNextMonth(inputDate));
    }

    @Test
    public void testFindFirstDayOfNextMonthFebruaryToMarch() {
        LocalDate inputDate = LocalDate.of(2023, 2, 28);
        LocalDate expected = LocalDate.of(2023, 3, 1);
        Assertions.assertEquals(expected, DateUtils.findFirstDayOfNextMonth(inputDate));
    }
    
    /**
   	 * findTomorrow() method.
   	 */
    
    @Test
    public void testFindTomorrow() {
        LocalDate inputDate = LocalDate.of(2023, 12, 31);
        LocalDate expected = LocalDate.of(2024, 1, 1);
        Assertions.assertEquals(expected, DateUtils.findTomorrow(inputDate));
    }
    
    @Test
    public void testTomorrowFebruaryToMarchNotLeapYear() {
        LocalDate inputDate = LocalDate.of(2023, 2, 28);
        LocalDate expected = LocalDate.of(2023, 3, 1);
        Assertions.assertEquals(expected, DateUtils.findTomorrow(inputDate));
    }
    
    //@Test
    //public void testTomorrowFebruaryToMarchLeapYear() {
    //    LocalDate inputDate = LocalDate.of(2024, 2, 28);
    //    LocalDate expected = LocalDate.of(2024, 2, 29);
    //    Assertions.assertEquals(expected, DateUtils.findFirstDayOfNextMonth(inputDate));
    //}
}
