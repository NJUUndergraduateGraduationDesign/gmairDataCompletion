package edu.nju.dao.status.impl;

import edu.nju.dao.impl.BaseDailyHourlyDaoImpl;
import edu.nju.dao.status.PowerDailyDao;
import edu.nju.model.statistic.AvgDataDaily;
import edu.nju.model.status.PowerDaily;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author ：tsl
 * @date ：Created in 2020/3/1 13:55
 * @description：
 */

@Repository
public class PowerDailyDaoImpl extends BaseDailyHourlyDaoImpl<PowerDaily> implements PowerDailyDao {
    @Override
    public double getAvgMachineOpenTime(String uid, int methodCode, long startTime, long endTime) {
        String hql = "SELECT avg(o.powerOnMinute) FROM PowerDaily o "
                + " WHERE o.uid = ?0 AND o.completeMethod = ?1"
                + " AND o.createAt BETWEEN ?2 AND ?3 order by o.createAt";
        Object obj = getUniqueColumnByHQL(hql,uid,methodCode,startTime,endTime);
        return obj == null ? 0 : (double)obj;
    }

    @Override
    public List<AvgDataDaily> getAvgListWithDate(String uid, int methodCode, long startTime, long endTime) {
        String hql = "SELECT new edu.nju.model.statistic.AvgDataDaily(o.createAt, o.powerOnMinute * 1.0)" +
                " FROM PowerDaily o"
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
    public int getOpenCount(String uid, int methodCode, long startTime, long endTime) {
        String hql = "SELECT count(*) FROM PowerDaily p " +
                "WHERE p.uid = ?0 AND p.completeMethod = ?1 " +
                "AND p.createAt BETWEEN ?2 AND ?3 " +
                "AND p.powerOnMinute>0";
        Object obj = getUniqueColumnByHQL(hql, uid, methodCode, startTime, endTime);
        return obj == null ? 0 : ((Long) obj).intValue();
    }

    @Override
    public PowerDaily getMostOpenDay(String uid, int methodCode, long startTime, long endTime) {
        String hql = "SELECT p1 FROM PowerDaily p1 " +
                "WHERE p1.uid = ?0 AND p1.completeMethod=?1 " +
                "AND p1.createAt BETWEEN ?2 AND ?3 " +
                "AND p1.powerOnMinute = ( " +
                "SELECT max(p2.powerOnMinute) FROM PowerDaily p2 " +
                "WHERE p2.uid= ?0 AND p2.completeMethod=?1 " +
                "AND p2.createAt BETWEEN ?2 AND ?3)";
        List<PowerDaily> res = getListByHQL(hql, uid, methodCode, startTime, endTime);
        return (res != null && res.size() > 0) ? res.get(0) : null;
    }
}
