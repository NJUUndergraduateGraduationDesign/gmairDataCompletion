package edn.nju.util;

import java.util.Calendar;
import java.util.Date;

/**
 * @author ：tsl
 * @date ：Created in 2020/2/29 23:51
 * @description：util of time
 */

public class TimeUtil {
    public static long startOfThisHour(long time){
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(time));
        cal.set(14, 0);
        cal.set(13, 0);
        cal.set(12, 0);
        cal.add(10, 0);
        return cal.getTime().getTime();
    }

    public static long startOfNextHour(long time){
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(time));
        cal.set(14, 0);
        cal.set(13, 0);
        cal.set(12, 0);
        cal.add(10, 1);
        return cal.getTime().getTime();
    }
}
