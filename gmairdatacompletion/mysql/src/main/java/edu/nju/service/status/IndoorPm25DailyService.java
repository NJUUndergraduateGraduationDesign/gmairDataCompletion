package edu.nju.service.status;

import edu.nju.model.statistic.AvgDataDaily;
import edu.nju.model.status.IndoorPm25Daily;
import edu.nju.service.BaseDailyHourlyService;

import java.util.List;

/**
 * @author ：tsl
 * @date ：Created in 2020/3/4 0:29
 * @description：
 */

public interface IndoorPm25DailyService extends BaseDailyHourlyService<IndoorPm25Daily> {
    int getAverageData(String uid, int methodCode, long startTime, long endTime);

    List<AvgDataDaily> getAverageList(String uid, int methodCode, long startTime, long endTime);

    List<Double> getAvgList(String uid, int methodCode, long startTime, long endTime);
}
