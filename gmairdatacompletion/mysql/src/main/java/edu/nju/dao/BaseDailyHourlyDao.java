package edu.nju.dao;

import java.util.List;

/**
 * @author ：tsl
 * @date ：Created in 2020/3/3 1:31
 * @description：
 */

public interface BaseDailyHourlyDao<T> extends BaseDao<T>{
    List<T> getByUidAndMethod(String uid, int methodCode, long startTime, long endTime);

    long getLatestTime(String uid);

    List<Double> getAverageList(String uid, String colName, int methodCode, long startTime, long endTime);
}
