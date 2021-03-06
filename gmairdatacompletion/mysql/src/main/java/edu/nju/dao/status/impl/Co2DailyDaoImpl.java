package edu.nju.dao.status.impl;

import edu.nju.dao.impl.BaseDailyHourlyDaoImpl;
import edu.nju.dao.status.Co2DailyDao;
import edu.nju.model.status.Co2Daily;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author ：tsl
 * @date ：Created in 2020/3/1 13:52
 * @description：
 */

@Repository
public class Co2DailyDaoImpl extends BaseDailyHourlyDaoImpl<Co2Daily> implements Co2DailyDao {
    @Override
    public List<Double> getAverageList(String uid, int methodCode, long startTime, long endTime) {
        String hql = "SELECT o.averageCo2 FROM Co2Daily o "
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
    public List<String> getUserIds() {
        String hql = "select c.uid from Co2Daily c group by c.uid";
        Query query = getSession().createQuery(hql);
        return (List<String>) query.list();
    }
}
