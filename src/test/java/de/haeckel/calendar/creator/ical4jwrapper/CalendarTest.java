package de.haeckel.calendar.creator.ical4jwrapper;

import org.junit.Test;

import java.util.GregorianCalendar;

/**
 * Created by TimoHäckel on 08.05.2017.
 */
public class CalendarTest {

    @Test
    public void stdCalendar() throws Exception {
        Calendar cal = new Calendar("mycalendar");

        // Start Date is on: April 1, 2008, 9:00 am
        java.util.Calendar startDate = new GregorianCalendar();
        startDate.setTimeZone(cal.getTimeZone());
        startDate.set(java.util.Calendar.MONTH, java.util.Calendar.APRIL);
        startDate.set(java.util.Calendar.DAY_OF_MONTH, 1);
        startDate.set(java.util.Calendar.YEAR, 2008);
        startDate.set(java.util.Calendar.HOUR_OF_DAY, 9);
        startDate.set(java.util.Calendar.MINUTE, 0);
        startDate.set(java.util.Calendar.SECOND, 0);

        // End Date is on: April 1, 2008, 13:00
        java.util.Calendar endDate = new GregorianCalendar();
        endDate.setTimeZone(cal.getTimeZone());
        endDate.set(java.util.Calendar.MONTH, java.util.Calendar.APRIL);
        endDate.set(java.util.Calendar.DAY_OF_MONTH, 1);
        endDate.set(java.util.Calendar.YEAR, 2008);
        endDate.set(java.util.Calendar.HOUR_OF_DAY, 13);
        endDate.set(java.util.Calendar.MINUTE, 0);
        endDate.set(java.util.Calendar.SECOND, 0);

        String eventName = "Progress Meeting";
        cal.createEvent(eventName, startDate, endDate);

        System.out.println(cal);

        cal.exportToICS();

    }
}