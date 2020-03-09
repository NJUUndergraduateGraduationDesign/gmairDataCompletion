package edu.nju.dao.status.impl;

import edu.nju.dao.impl.BaseDailyHourlyDaoImpl;
import edu.nju.dao.status.InnerPm25DailyDao;
import edu.nju.model.status.InnerPm25Daily;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author ：tsl
 * @date ：Created in 2020/3/1 13:54
 * @description：
 */

@Repository
public class InnerPm25DailyDaoImpl extends BaseDailyHourlyDaoImpl<InnerPm25Daily> implements InnerPm25DailyDao {
    @Override
    public List<Double> getAverageList(String uid, int methodCode, long startTime, long endTime) {
        String hql = "SELECT o.averagePm25 FROM InnerPm25Daily o "
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
    public int getOverCount(String uid, int methodCode, long startTime, long endTime) {
        String hql = "SELECT COUNT(o) FROM InnerPm25Daily o "
                + " WHERE o.uid = ?0 AND o.completeMethod = ?1"
                + " AND o.averagePm25 > 25 AND o.createAt BETWEEN ?2 AND ?3";
        return (int) getUniqueColumnByHQL(hql, uid, methodCode, startTime, endTime);
    }

    @Override
    public long getLatestTime(String uid) {
        String hql = "SELECT max(a.createAt) FROM InnerPm25Daily a, IndoorPm25Daily b"
                + " WHERE a.uid= ?0 and a.uid = b.uid and a.createAt = b.createAt";
        Object obj = getUniqueColumnByHQL(hql, uid);
        return obj == null ? 0 : (long) obj;
    }
}
