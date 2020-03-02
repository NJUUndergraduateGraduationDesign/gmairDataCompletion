package edn.nju.util;

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

    public static boolean isBetweenStartAndEnd(long cur, long start, long end) {
        return start <= cur && cur < end;
    }
}
