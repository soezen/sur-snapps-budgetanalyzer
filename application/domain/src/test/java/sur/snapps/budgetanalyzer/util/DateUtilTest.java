package sur.snapps.budgetanalyzer.util;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.HOUR_OF_DAY;
import static java.util.Calendar.MILLISECOND;
import static java.util.Calendar.MINUTE;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.SECOND;
import static java.util.Calendar.YEAR;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * User: SUR
 * Date: 3/05/14
 * Time: 11:15
 */
public class DateUtilTest {

    @Test
    public void testNow() {
        Date now = DateUtil.now();

        Calendar calendar = Calendar.getInstance();
        Calendar nowCalendar = Calendar.getInstance();
        nowCalendar.setTime(now);
        assertEquals(calendar.get(YEAR), nowCalendar.get(YEAR));
        assertEquals(calendar.get(MONTH), nowCalendar.get(MONTH));
        assertEquals(calendar.get(DAY_OF_MONTH), nowCalendar.get(DAY_OF_MONTH));
        assertEquals(calendar.get(HOUR_OF_DAY), nowCalendar.get(HOUR_OF_DAY));
        assertEquals(calendar.get(MINUTE), nowCalendar.get(MINUTE));
        assertEquals(calendar.get(SECOND), nowCalendar.get(SECOND));
    }

    @Test
    public void testAddDays() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(YEAR, 2000);
        calendar.set(MONTH, Calendar.MARCH);
        calendar.set(DAY_OF_MONTH, 31);
        calendar.set(HOUR_OF_DAY, 22);
        calendar.set(MINUTE, 4);
        calendar.set(SECOND, 55);
        calendar.set(MILLISECOND, 333);

        Date day = calendar.getTime();
        Date nowPlusDays = DateUtil.addDays(day, 5);
        Calendar nowPlusDaysCalendar = Calendar.getInstance();
        nowPlusDaysCalendar.setTime(nowPlusDays);

        assertEquals(calendar.get(YEAR), nowPlusDaysCalendar.get(YEAR));
        assertEquals(Calendar.APRIL, nowPlusDaysCalendar.get(MONTH));
        assertEquals(5, nowPlusDaysCalendar.get(DAY_OF_MONTH));
        assertEquals(calendar.get(HOUR_OF_DAY), nowPlusDaysCalendar.get(HOUR_OF_DAY));
        assertEquals(calendar.get(MINUTE), nowPlusDaysCalendar.get(MINUTE));
        assertEquals(calendar.get(SECOND), nowPlusDaysCalendar.get(SECOND));
        assertEquals(calendar.get(MILLISECOND), nowPlusDaysCalendar.get(MILLISECOND));
    }

    @Test
    public void testIsSameDayTrue() {
        Calendar cal1 = Calendar.getInstance();
        cal1.set(YEAR, 2000);
        cal1.set(MONTH, Calendar.MARCH);
        cal1.set(DAY_OF_MONTH, 5);
        cal1.set(HOUR_OF_DAY, 0);
        cal1.set(MINUTE, 0);
        cal1.set(SECOND, 0);
        cal1.set(MILLISECOND, 0);
        Date day1 = cal1.getTime();

        Calendar cal2 = Calendar.getInstance();
        cal2.set(YEAR, 2000);
        cal2.set(MONTH, Calendar.MARCH);
        cal2.set(DAY_OF_MONTH, 5);
        cal2.set(HOUR_OF_DAY, 23);
        cal2.set(MINUTE, 59);
        cal2.set(SECOND, 59);
        cal2.set(MILLISECOND, 999);
        Date day2 = cal2.getTime();

        assertTrue(DateUtil.isSameDay(day1, day2));
    }

    @Test
    public void testIsSameDayFalseDifferentYear() {
        Calendar cal1 = Calendar.getInstance();
        cal1.set(YEAR, 2001);
        cal1.set(MONTH, Calendar.MARCH);
        cal1.set(DAY_OF_MONTH, 5);
        cal1.set(HOUR_OF_DAY, 0);
        cal1.set(MINUTE, 0);
        cal1.set(SECOND, 0);
        cal1.set(MILLISECOND, 0);
        Date day1 = cal1.getTime();

        Calendar cal2 = Calendar.getInstance();
        cal2.set(YEAR, 2000);
        cal2.set(MONTH, Calendar.MARCH);
        cal2.set(DAY_OF_MONTH, 5);
        cal2.set(HOUR_OF_DAY, 23);
        cal2.set(MINUTE, 59);
        cal2.set(SECOND, 59);
        cal2.set(MILLISECOND, 999);
        Date day2 = cal2.getTime();

        assertFalse(DateUtil.isSameDay(day1, day2));
    }


    @Test
    public void testIsSameDayFalseDifferentMonth() {
        Calendar cal1 = Calendar.getInstance();
        cal1.set(YEAR, 2000);
        cal1.set(MONTH, Calendar.APRIL);
        cal1.set(DAY_OF_MONTH, 5);
        cal1.set(HOUR_OF_DAY, 0);
        cal1.set(MINUTE, 0);
        cal1.set(SECOND, 0);
        cal1.set(MILLISECOND, 0);
        Date day1 = cal1.getTime();

        Calendar cal2 = Calendar.getInstance();
        cal2.set(YEAR, 2000);
        cal2.set(MONTH, Calendar.MARCH);
        cal2.set(DAY_OF_MONTH, 5);
        cal2.set(HOUR_OF_DAY, 23);
        cal2.set(MINUTE, 59);
        cal2.set(SECOND, 59);
        cal2.set(MILLISECOND, 999);
        Date day2 = cal2.getTime();

        assertFalse(DateUtil.isSameDay(day1, day2));
    }
    @Test
    public void testIsSameDayFalseDifferentDay() {
        Calendar cal1 = Calendar.getInstance();
        cal1.set(YEAR, 2000);
        cal1.set(MONTH, Calendar.MARCH);
        cal1.set(DAY_OF_MONTH, 6);
        cal1.set(HOUR_OF_DAY, 0);
        cal1.set(MINUTE, 0);
        cal1.set(SECOND, 0);
        cal1.set(MILLISECOND, 0);
        Date day1 = cal1.getTime();

        Calendar cal2 = Calendar.getInstance();
        cal2.set(YEAR, 2000);
        cal2.set(MONTH, Calendar.MARCH);
        cal2.set(DAY_OF_MONTH, 5);
        cal2.set(HOUR_OF_DAY, 23);
        cal2.set(MINUTE, 59);
        cal2.set(SECOND, 59);
        cal2.set(MILLISECOND, 999);
        Date day2 = cal2.getTime();

        assertFalse(DateUtil.isSameDay(day1, day2));
    }

    @Test
    public void testRemoveTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(YEAR, 2000);
        calendar.set(MONTH, Calendar.MARCH);
        calendar.set(DAY_OF_MONTH, 5);
        calendar.set(HOUR_OF_DAY, 23);
        calendar.set(MINUTE, 59);
        calendar.set(SECOND, 59);
        calendar.set(MILLISECOND, 999);
        Date day = calendar.getTime();
        Date dayWithoutTime = DateUtil.removeTime(day);
        Calendar calendarWithoutTime = Calendar.getInstance();
        calendarWithoutTime.setTime(dayWithoutTime);

        assertEquals(2000, calendarWithoutTime.get(YEAR));
        assertEquals(Calendar.MARCH, calendarWithoutTime.get(MONTH));
        assertEquals(5, calendarWithoutTime.get(DAY_OF_MONTH));
        assertEquals(0, calendarWithoutTime.get(HOUR_OF_DAY));
        assertEquals(0, calendarWithoutTime.get(MINUTE));
        assertEquals(0, calendarWithoutTime.get(SECOND));
        assertEquals(0, calendarWithoutTime.get(MILLISECOND));

    }
}
