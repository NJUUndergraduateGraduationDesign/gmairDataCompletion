package edu.nju.service.status;

import edu.nju.model.monthly.MostOpenHour;
import edu.nju.model.status.PowerHourly;
import edu.nju.service.BaseDailyHourlyService;

/**
 * @author ：tsl
 * @date ：Created in 2020/3/4 0:29
 * @description：
 */

public interface PowerHourlyService extends BaseDailyHourlyService<PowerHourly> {
    MostOpenHour getMostOpenHour(String uid, int methodCode, long startTime, long endTime);
}
