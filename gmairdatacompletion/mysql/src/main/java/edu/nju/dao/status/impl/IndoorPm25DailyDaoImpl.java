package edu.nju.dao.status.impl;

import edu.nju.dao.impl.BaseDailyHourlyDaoImpl;
import edu.nju.dao.status.IndoorPm25DailyDao;
import edu.nju.model.statistic.AvgDataDaily;
import edu.nju.model.status.IndoorPm25Daily;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author ：tsl
 * @date ：Created in 2020/3/1 13:53
 * @description：
 */

@Repository
public class IndoorPm25DailyDaoImpl extends BaseDailyHourlyDaoImpl<IndoorPm25Daily> implements IndoorPm25DailyDao {
    @Override
    public List<Double> getAverageList(String uid, int methodCode, long startTime, long endTime) {
        String hql = "SELECT o.averagePm25 FROM IndoorPm25Daily o "
                + " WHERE o.uid = ?1 AND o.completeMethod = ?2"
                + " AND o.createAt BETWEEN ?3 AND ?4 order by o.createAt";
        Query query = getSession().createQuery(hql);
        query.setParameter(1, uid);
        query.setParameter(2, methodCode);
        query.setParameter(3, startTime);
        query.setParameter(4, endTime);
        return query.list();
    }

    @Override
    public List<AvgDataDaily> getAvgListWithDate(String uid, int methodCode,
                                                 long startTime, long endTime) {
        String hql = "SELECT new edu.nju.model.statistic.AvgDataDaily(o.createAt, o.averagePm25)" +
                " FROM IndoorPm25Daily o"
                + " WHERE o.uid = ?1 AND o.completeMethod = ?2"
                + " AND o.createAt BETWEEN ?3 AND ?4 order by o.createAt";
        Query query = getSession().createQuery(hql);
        query.setParameter(1, uid);
        query.setParameter(2, methodCode);
        query.setParameter(3, startTime);
        query.setParameter(4, endTime);
        return query.list();
    }
}
