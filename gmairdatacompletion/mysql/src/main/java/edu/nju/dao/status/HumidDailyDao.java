package edu.nju.dao.status;

import edu.nju.dao.BaseDailyHourlyDao;
import edu.nju.model.status.HumidDaily;

import java.util.List;

/**
 * @author ：tsl
 * @date ：Created in 2020/3/1 13:53
 * @description：
 */

public interface HumidDailyDao extends BaseDailyHourlyDao<HumidDaily> {
    List<Double> getAverageList(String uid, int methodCode, long startTime, long endTime);
}
