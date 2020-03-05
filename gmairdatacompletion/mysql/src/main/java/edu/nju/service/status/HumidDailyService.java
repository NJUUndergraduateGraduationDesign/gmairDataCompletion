package edu.nju.service.status;

import edu.nju.model.status.Co2Daily;
import edu.nju.model.status.HumidDaily;
import edu.nju.service.BaseDailyHourlyService;

/**
 * @author ：tsl
 * @date ：Created in 2020/3/4 0:29
 * @description：
 */

public interface HumidDailyService extends BaseDailyHourlyService<HumidDaily> {
    int getAverageData(String uid, int methodCode, long startTime, long endTime);
}
