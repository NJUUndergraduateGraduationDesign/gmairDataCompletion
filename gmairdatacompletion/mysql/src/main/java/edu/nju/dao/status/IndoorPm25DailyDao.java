package edu.nju.dao.status;

import edu.nju.dao.BaseDailyHourlyDao;
import edu.nju.model.statistic.AvgDataDaily;
import edu.nju.model.status.IndoorPm25Daily;

import java.util.List;

/**
 * @author ：tsl
 * @date ：Created in 2020/3/1 13:53
 * @description：
 */

public interface IndoorPm25DailyDao extends BaseDailyHourlyDao<IndoorPm25Daily> {
    List<Double> getAverageList(String uid, int methodCode, long startTime, long endTime);

    List<AvgDataDaily> getAvgListWithDate(String uid, int methodCode, long startTime, long endTime);

    List<String> getAllUids(int methodCode, long startTime, long endTime);

    double getAverage(String uid, int methodCode, long startTime, long endTime);
}
