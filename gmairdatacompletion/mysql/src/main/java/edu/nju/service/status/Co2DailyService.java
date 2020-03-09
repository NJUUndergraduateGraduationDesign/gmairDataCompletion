package edu.nju.service.status;

import edu.nju.model.status.Co2Daily;
import edu.nju.service.BaseDailyHourlyService;

import java.util.List;

/**
 * @author ：tsl
 * @date ：Created in 2020/3/3 2:28
 * @description：
 */

public interface Co2DailyService extends BaseDailyHourlyService<Co2Daily> {
    int getAverageData(String uid, int methodCode, long startTime, long endTime);

    List<Double> getAvgList(String uid, int methodCode, long startTime, long endTime);

    List<String> getUserIds();
}
