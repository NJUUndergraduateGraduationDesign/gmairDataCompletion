package edu.nju.dao.status;

import edu.nju.dao.BaseDailyHourlyDao;
import edu.nju.model.statistic.AvgDataDaily;
import edu.nju.model.status.PowerDaily;

import java.util.List;

/**
 * @author ：tsl
 * @date ：Created in 2020/3/1 13:55
 * @description：
 */

public interface PowerDailyDao extends BaseDailyHourlyDao<PowerDaily> {
    double getAvgMachineOpenTime(String uid, int methodCode, long startTime, long endTime);

    List<AvgDataDaily> getAvgListWithDate(String uid, int methodCode, long startTime, long endTime);
}
