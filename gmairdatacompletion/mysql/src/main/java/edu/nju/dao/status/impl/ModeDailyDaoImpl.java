package edu.nju.dao.status.impl;

import edu.nju.dao.impl.BaseDailyHourlyDaoImpl;
import edu.nju.dao.status.ModeDailyDao;
import edu.nju.model.status.ModeDaily;
import org.springframework.stereotype.Repository;

/**
 * @author ：tsl
 * @date ：Created in 2020/3/1 13:55
 * @description：
 */

@Repository
public class ModeDailyDaoImpl extends BaseDailyHourlyDaoImpl<ModeDaily> implements ModeDailyDao {
    @Override
    public int getAutoMinutes(String uid, int methodCode, long startTime, long endTime) {
        String hql = "SELECT sum(m.autoMinute) FROM ModeDaily m " +
                "WHERE m.uid= ?0 AND m.completeMethod =?1 " +
                "AND m.createAt between ?2 and ?3";
        return ((Long) getUniqueColumnByHQL(hql, uid, methodCode, startTime, endTime)).intValue();
    }

    @Override
    public int getSleepMinutes(String uid, int methodCode, long startTime, long endTime) {
        String hql = "SELECT sum(m.sleepMinute) FROM ModeDaily m " +
                "WHERE m.uid= ?0 AND m.completeMethod =?1 " +
                "AND m.createAt between ?2 and ?3";
        return ((Long) getUniqueColumnByHQL(hql, uid, methodCode, startTime, endTime)).intValue();
    }

    @Override
    public int getManualMinutes(String uid, int methodCode, long startTime, long endTime) {
        String hql = "SELECT sum(m.manualMinute) FROM ModeDaily m " +
                "WHERE m.uid= ?0 AND m.completeMethod =?1 " +
                "AND m.createAt between ?2 and ?3";
        return ((Long) getUniqueColumnByHQL(hql, uid, methodCode, startTime, endTime)).intValue();
    }
}
