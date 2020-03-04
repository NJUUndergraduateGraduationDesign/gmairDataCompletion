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
        String hql = "SELECT o FROM " + clazz.getName() + " o"
                + " WHERE o.uid = ?0 AND o.completeMethod = ?1"
                + " AND o.createAt BETWEEN ?2 AND ?3";
        return getListByHQL(hql, uid, methodCode, startTime, endTime);
    }

    @Override
    public long getLatestTime(String uid) {
        String hql = "SELECT max(createAt) FROM " + clazz.getName()
                + " WHERE uid= ?0";
        return (long)getUniqueColumnByHQL(hql, uid);
    }

    @Override
    public int getAverageData(String uid, String colName,
                                 int methodCode, long startTime, long endTime) {
        String hql = "SELECT avg(o." + "?0" + ") FROM " + clazz.getName() + " o"
                + " WHERE o.uid = ?1 AND o.completeMethod = ?2"
                + " AND o.createAt BETWEEN ?3 AND ?4";
        return (int)Math.round(
                (double)getUniqueColumnByHQL(hql, colName, uid, methodCode, startTime, endTime));
    }
}
