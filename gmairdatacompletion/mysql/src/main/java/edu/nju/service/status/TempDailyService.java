package edu.nju.service.status;

import edu.nju.model.status.TempDaily;
import edu.nju.service.BaseDailyHourlyService;

import java.util.List;

/**
 * @author ：tsl
 * @date ：Created in 2020/3/4 0:29
 * @description：
 */

public interface TempDailyService extends BaseDailyHourlyService<TempDaily> {
    int getAverageData(String uid, int methodCode, long startTime, long endTime);

    List<Double> getAvgList(String uid, int methodCode, long startTime, long endTime);
}
