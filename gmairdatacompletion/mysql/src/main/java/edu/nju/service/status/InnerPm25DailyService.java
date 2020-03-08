package edu.nju.service.status;

import edu.nju.model.status.InnerPm25Daily;
import edu.nju.service.BaseDailyHourlyService;

import java.util.List;

/**
 * @author ：tsl
 * @date ：Created in 2020/3/4 0:29
 * @description：
 */

public interface InnerPm25DailyService extends BaseDailyHourlyService<InnerPm25Daily> {
    int getAverageData(String uid, int methodCode, long startTime, long endTime);

    List<Double> getAvgList(String uid, int methodCode, long startTime, long endTime);

    int getOverCount(String uid, int methodCode, long startTime, long endTime);
}
