package edn.nju.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author ：tsl
 * @date ：Created in 2020/2/29 23:51
 * @description：util of time
 */

public class TimeUtil {
    /*
    filed:1代表年操作，2代表月操作，3代表星操作，5代表日操作，
    11代表小时操作，12代表分钟操作，13代表秒操作，14代表毫秒操作。
     */
    public static long startOfThisHour(long time) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(time));
        cal.set(Calendar.MILLISECOND, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        return cal.getTime().getTime();
    }

    public static long startOfNextHour(long time) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(time));
        cal.set(Calendar.MILLISECOND, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.add(Calendar.HOUR_OF_DAY, 1);
        return cal.getTime().getTime();
    }

    public static long startOfThisDay(long time) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(time));
        cal.set(Calendar.MILLISECOND, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        return cal.getTime().getTime();
    }

    public static long endOfThisDay(long time) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(startOfNextDay(time)));
        cal.add(Calendar.MILLISECOND, -1);
        return cal.getTime().getTime();
    }

    public static long startOfNextDay(long time) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(time));
        cal.set(Calendar.MILLISECOND, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.add(Calendar.DAY_OF_YEAR, 1);
        return cal.getTime().getTime();
    }

    public static long getNDayBefore(long time, int n) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(time));
        cal.add(Calendar.DAY_OF_YEAR, -n);
        return cal.getTime().getTime();
    }

    public static Date getNDayBefore(Date date, int n) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_YEAR, -n);
        return cal.getTime();
    }

    public static Date getNMonthBefore(Date date, int n) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, -n);
        return cal.getTime();
    }

    public static Date getStartOfThisMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.MILLISECOND, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return cal.getTime();
    }

    public static Date getEndOfThisMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.add(Calendar.MONTH, 1);
        cal.add(Calendar.MILLISECOND, -1);
        return cal.getTime();
    }

    public static Date strToDate(String str) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//注意月份是MM
        return simpleDateFormat.parse(str);
    }

    public static int millisecondsToMinute(long milliseconds) {
        return (int) (milliseconds / (1000 * 60));
    }

    public static double minuteToHour(int minute) {
        return minute / 60.0;
    }

    public static String dateToStrMonth(Date date) {
        return new SimpleDateFormat("yyyy-MM").format(date);
    }

    public static String dateToStrDay(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd").format(date);
    }

    public static String dateToStrTime(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
    }

    public static boolean isBetweenStartAndEnd(long cur, long start, long end) {
        return start <= cur && cur < end;
    }
}
