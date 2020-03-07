package edu.nju.service.status;

import edu.nju.model.status.VolumeDaily;
import edu.nju.service.BaseDailyHourlyService;

import java.util.List;

/**
 * @author ：tsl
 * @date ：Created in 2020/3/4 0:29
 * @description：
 */

public interface VolumeDailyService extends BaseDailyHourlyService<VolumeDaily> {
    int getAverageData(String uid, int methodCode, long startTime, long endTime);

    List<Double> getAvgList(String uid, int methodCode, long startTime, long endTime);
}
