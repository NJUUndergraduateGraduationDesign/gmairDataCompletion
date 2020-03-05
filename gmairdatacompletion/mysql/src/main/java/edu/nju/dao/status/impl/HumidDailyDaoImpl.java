package edu.nju.dao.status.impl;

import edu.nju.dao.impl.BaseDailyHourlyDaoImpl;
import edu.nju.dao.status.HumidDailyDao;
import edu.nju.model.status.HumidDaily;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author ：tsl
 * @date ：Created in 2020/3/1 13:53
 * @description：
 */

@Repository
public class HumidDailyDaoImpl extends BaseDailyHourlyDaoImpl<HumidDaily> implements HumidDailyDao {
    @Override
    public List<Double> getAverageList(String uid, int methodCode, long startTime, long endTime) {
        String hql = "SELECT o.averageHumid FROM HumidDaily o "
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
