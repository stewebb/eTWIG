package net.grinecraft.etwig.util;

import java.time.LocalDate;
import java.util.Date;

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
}
