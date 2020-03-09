package edu.nju.dao.status;

import edu.nju.dao.BaseDailyHourlyDao;
import edu.nju.model.status.Co2Daily;

import java.util.List;

/**
 * @author ：tsl
 * @date ：Created in 2020/3/1 13:52
 * @description：
 */

public interface Co2DailyDao extends BaseDailyHourlyDao<Co2Daily> {
    List<Double> getAverageList(String uid, int methodCode, long startTime, long endTime);

    List<String> getUserIds();
}
