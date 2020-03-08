package edu.nju.dao.status;

import edu.nju.dao.BaseDailyHourlyDao;
import edu.nju.model.monthly.MostUseMode;
import edu.nju.model.status.ModeDaily;

/**
 * @author ：tsl
 * @date ：Created in 2020/3/1 13:55
 * @description：
 */

public interface ModeDailyDao extends BaseDailyHourlyDao<ModeDaily> {
    int getAutoMinutes(String uid, int methodCode, long startTime, long endTime);

    int getSleepMinutes(String uid, int methodCode, long startTime, long endTime);

    int getManualMinutes(String uid, int methodCode, long startTime, long endTime);
}
