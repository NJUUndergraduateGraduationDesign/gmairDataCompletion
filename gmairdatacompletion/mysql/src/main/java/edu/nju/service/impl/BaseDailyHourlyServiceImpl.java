package edu.nju.service.impl;

import edu.nju.dao.BaseDailyHourlyDao;
import edu.nju.dao.BaseDao;
import edu.nju.service.BaseDailyHourlyService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ：tsl
 * @date ：Created in 2020/3/3 1:16
 * @description：
 */

@Service
public abstract class BaseDailyHourlyServiceImpl<T> implements BaseDailyHourlyService<T> {

    protected BaseDailyHourlyDao<T> dao;

    public void setDao(BaseDailyHourlyDao<T> dao) {
        this.dao = dao;
    }

    public BaseDailyHourlyDao<T> getDao() {
        return dao;
    }

    @Override
    public List<T> getByUidAndCompleteMethod(String uid, int completeMethod, long startTime, long endTime) {
        return dao.getByUidAndMethod(uid, completeMethod, startTime, endTime);
    }
}
