package net.grinecraft.etwig.util;

import java.util.Date;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DateUtilsTest {

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
		Date date = DateUtils.safeParseDate("1970-01-02", "yyyy-mm-dd");
		Assertions.assertEquals(70, date.getYear());
		Assertions.assertEquals(0, date.getMonth());
		Assertions.assertEquals(2, date.getDay());
	}
}
