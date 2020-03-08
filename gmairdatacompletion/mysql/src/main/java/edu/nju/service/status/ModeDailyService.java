package edu.nju.service.status;

import edu.nju.model.monthly.MostUseMode;
import edu.nju.model.status.ModeDaily;
import edu.nju.service.BaseDailyHourlyService;

/**
 * @author ：tsl
 * @date ：Created in 2020/3/4 0:29
 * @description：
 */

public interface ModeDailyService extends BaseDailyHourlyService<ModeDaily> {
    int getAutoMinutes(String uid, int methodCode, long startTime, long endTime);

    int getSleepMinutes(String uid, int methodCode, long startTime, long endTime);

    int getManualMinutes(String uid, int methodCode, long startTime, long endTime);
}
