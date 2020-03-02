package edu.nju.service;

import java.util.List;

/**
 * @author ：tsl
 * @date ：Created in 2020/3/3 1:15
 * @description：
 */

public interface BaseDailyHourlyService<T> {
    List<T> getByUidAndCompleteMethod(String uid, int completeMethod, long startTime, long endTime);
}
