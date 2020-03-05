package edu.nju.dao.status;

import edu.nju.dao.BaseDailyHourlyDao;
import edu.nju.model.status.TempDaily;

import java.util.List;

/**
 * @author ：tsl
 * @date ：Created in 2020/3/1 13:56
 * @description：
 */

public interface TempDailyDao extends BaseDailyHourlyDao<TempDaily> {
    List<Double> getAverageList(String uid, int methodCode, long startTime, long endTime);
}
