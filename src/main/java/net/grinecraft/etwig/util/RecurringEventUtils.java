package net.grinecraft.etwig.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.dmfs.rfc5545.DateTime;
import org.dmfs.rfc5545.recur.RecurrenceRule;
import org.dmfs.rfc5545.recur.RecurrenceRuleIterator;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RecurringEventUtils {
	
	private String rRule;
	private LocalDate startDate;
	private LocalDate endDate;
	private LocalDateTime time;
	
	public Set<LocalDateTime> calculateRecurringDates() throws Exception {
		
		
		//String modifiedRule = this.rRule.substring(this.rRule.lastIndexOf("RRULE:") + 1);
		//System.out.println(modifiedRule);
		String[] splitedRule = this.rRule.split("RRULE:");
		String extractedRule = null;
		
		if(splitedRule.length == 1) {
			extractedRule = this.rRule;
		}
		
		else {
			extractedRule = splitedRule[1];
		}
		//System.out.println(Arrays.toString(splitedRule));
		//System.out.println(splitedRule.length);
		
		
		
		// Get the time part from the DB.
        LocalTime timePart = this.time.toLocalTime();
		Set<LocalDateTime> dateTimes = new HashSet<>();

        // Parse the rRule
        RecurrenceRule rule = new RecurrenceRule(extractedRule);

        // Convert LocalDate to DateTime
        DateTime startDateTime = new DateTime(this.startDate.getYear(), this.startDate.getMonthValue() - 1, this.startDate.getDayOfMonth());
        DateTime endDateTime = new DateTime(this.endDate.getYear(), this.endDate.getMonthValue() - 1, this.endDate.getDayOfMonth());

        // Create a RecurrenceRuleIterator
        RecurrenceRuleIterator it = rule.iterator(startDateTime);

        // Iterate over the recurring events
        while (it.hasNext()) {
            DateTime nextInstance = it.nextDateTime();
            if (nextInstance.after(endDateTime)) {
                break;
            }
            LocalDate nextDate = LocalDate.of(nextInstance.getYear(), nextInstance.getMonth() + 1, nextInstance.getDayOfMonth());
            
            dateTimes.add(nextDate.atTime(timePart));
        }
        
		return dateTimes;
	 }
}
