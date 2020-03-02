package edu.nju.dao.impl;

import edu.nju.dao.BaseDailyHourlyDao;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author ：tsl
 * @date ：Created in 2020/3/3 1:31
 * @description：
 */

@Repository
public abstract class BaseDailyHourlyDaoImpl<T> extends BaseDaoImpl<T> implements BaseDailyHourlyDao<T> {
    @Override
    public List<T> getByUidAndMethod(String uid, int methodCode, long startTime, long endTime) {
        String hql = "SELECT * FROM " + clazz.getName()
                + " WHERE uid = ?0 AND completeMethod = ?1"
                + " AND createAt BETWEEN ?2 AND ?3";
        return getListByHQL(hql, uid, methodCode, startTime, endTime);
    }
}
