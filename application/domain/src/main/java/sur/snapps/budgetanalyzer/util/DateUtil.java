package sur.snapps.budgetanalyzer.util;

import java.util.Calendar;
import java.util.Date;

import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.HOUR_OF_DAY;
import static java.util.Calendar.MILLISECOND;
import static java.util.Calendar.MINUTE;
import static java.util.Calendar.SECOND;

/**
 * User: SUR
 * Date: 3/05/14
 * Time: 11:09
 */
public final class DateUtil {

    private DateUtil() { }

    public static Date now() {
        return new Date();
    }

    public static Date addDays(Date date, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, days);
        return calendar.getTime();
    }

    public static boolean isSameDay(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);

        return cal1.get(Calendar.DATE) == cal2.get(Calendar.DATE)
                && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH)
                && cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
    }

    public static Date removeTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(HOUR_OF_DAY, calendar.getActualMinimum(HOUR_OF_DAY));
        calendar.set(MINUTE, calendar.getActualMinimum(MINUTE));
        calendar.set(SECOND, calendar.getActualMinimum(SECOND));
        calendar.set(MILLISECOND, calendar.getActualMinimum(MILLISECOND));
        return calendar.getTime();
    }

    public static Date firstDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(DAY_OF_MONTH, 1);
        return removeTime(calendar.getTime());
    }

    public static Date lastDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(DAY_OF_MONTH, calendar.getActualMaximum(DAY_OF_MONTH));
        calendar.set(HOUR_OF_DAY, calendar.getActualMaximum(HOUR_OF_DAY));
        calendar.set(MINUTE, calendar.getActualMaximum(MINUTE));
        calendar.set(SECOND, calendar.getActualMaximum(SECOND));
        calendar.set(MILLISECOND, calendar.getActualMaximum(MILLISECOND));
        return calendar.getTime();
    }
}
