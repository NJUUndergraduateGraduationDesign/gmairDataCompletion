package edu.nju.service.impl;

import edu.nju.dao.BaseDailyHourlyDao;
import edu.nju.service.BaseDailyHourlyService;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * @author ：tsl
 * @date ：Created in 2020/3/3 1:16
 * @description：
 */

@Transactional
@Service
public abstract class BaseDailyHourlyServiceImpl<T> implements BaseDailyHourlyService<T> {

    protected BaseDailyHourlyDao<T> dao;

    public void setDao(BaseDailyHourlyDao<T> dao) {
        this.dao = dao;
    }

    @Override
    public List<T> getByUidAndCompleteMethod(String uid, int completeMethod, long startTime, long endTime) {
        return dao.getByUidAndMethod(uid, completeMethod, startTime, endTime);
    }

    @Override
    public long getLatestTime(String uid) {
        return dao.getLatestTime(uid);
    }

    @Override
    public int getAverageData(String uid, String colName, int methodCode, long startTime, long endTime) {
        return dao.getAverageData(uid, colName, methodCode, startTime, endTime);
    }
}
