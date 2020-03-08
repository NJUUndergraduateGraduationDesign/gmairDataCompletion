package edu.nju.dao.status;

import edu.nju.dao.BaseDailyHourlyDao;
import edu.nju.model.status.InnerPm25Daily;

import java.util.List;

/**
 * @author ：tsl
 * @date ：Created in 2020/3/1 13:54
 * @description：
 */

public interface InnerPm25DailyDao extends BaseDailyHourlyDao<InnerPm25Daily> {
    List<Double> getAverageList(String uid, int methodCode, long startTime, long endTime);

    int getOverCount(String uid, int methodCode, long startTime, long endTime);
}
