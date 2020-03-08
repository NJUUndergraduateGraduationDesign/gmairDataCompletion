package edu.nju.service.status;

import edu.nju.model.statistic.AvgDataDaily;
import edu.nju.model.status.PowerDaily;
import edu.nju.service.BaseDailyHourlyService;

import java.util.List;

/**
 * @author ：tsl
 * @date ：Created in 2020/3/4 0:29
 * @description：
 */

public interface PowerDailyService extends BaseDailyHourlyService<PowerDaily> {
    int getAvgMachineOpenTime(String uid, int methodCode, long startTime, long endTime);

    List<AvgDataDaily> getAverageList(String uid, int methodCode, long startTime, long endTime);

    int getOpenCount(String uid, int methodCode, long startTime, long endTime);

    PowerDaily getMostOpenDay(String uid, int methodCode, long startTime, long endTime);
}
