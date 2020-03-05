package edu.nju.dao.status;

import edu.nju.dao.BaseDailyHourlyDao;
import edu.nju.model.status.VolumeDaily;

import java.util.List;

/**
 * @author ：tsl
 * @date ：Created in 2020/3/1 13:56
 * @description：
 */

public interface VolumeDailyDao extends BaseDailyHourlyDao<VolumeDaily> {
    List<Double> getAverageList(String uid, int methodCode, long startTime, long endTime);
}
