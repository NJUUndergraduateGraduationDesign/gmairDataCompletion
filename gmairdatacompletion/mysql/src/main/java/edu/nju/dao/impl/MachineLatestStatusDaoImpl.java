package edu.nju.dao.impl;

import edu.nju.dao.MachineLatestStatusDao;
import edu.nju.model.machine.MachineBasicInfo;
import edu.nju.model.machine.MachineLatestStatus;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author: Bright Chan
 * @date: 2020/3/3 13:07
 * @description: TODO
 */

@Repository
public class MachineLatestStatusDaoImpl extends BaseDaoImpl<MachineLatestStatus>
                                        implements MachineLatestStatusDao {
    @Override
    public List<MachineBasicInfo> findByQueryCond(int offset, int pageSize) {
        String hql = "select new edu.nju.model.machine.MachineBasicInfo(" +
                "u.uid, u.codeValue, l.cityName, m.power, m.mode, u.bindTime, m.overCount, m.heat) from " +
                "User u, MachineLatestStatus m, Location l " +
                "where u.dataType <> -1 and u.uid=m.uid and u.cityId=l.cityId";
        return executeHQL(hql, offset, pageSize);
    }

    @Override
    public List<MachineBasicInfo> findByQueryCond(Date createTimeGTE, Date createTimeLTE,
                                                  int offset, int pageSize) {
        String hql = "select new edu.nju.model.machine.MachineBasicInfo(" +
                "u.uid, u.codeValue, l.cityName, m.power, m.mode, u.bindTime, m.overCount, m.heat) from " +
                "User u, MachineLatestStatus m, Location l " +
                "where u.dataType <> -1 and u.uid=m.uid and u.cityId=l.cityId and " +
                "unix_timestamp(u.bindTime) between unix_timestamp(?0) and unix_timestamp(?1)";
        return executeHQL(hql, offset, pageSize, createTimeGTE, createTimeLTE);
    }

    @Override
    public List<MachineBasicInfo> findByQueryCond(int isPower,
                                                  int offset, int pageSize) {
        String hql = "select new edu.nju.model.machine.MachineBasicInfo(" +
                "u.uid, u.codeValue, l.cityName, m.power, m.mode, u.bindTime, m.overCount, m.heat) from " +
                "User u, MachineLatestStatus m, Location l " +
                "where u.dataType <> -1 and u.uid=m.uid and u.cityId=l.cityId and m.power = ?0";
        return executeHQL(hql, offset, pageSize, isPower);
    }

    @Override
    public List<MachineBasicInfo> findByQueryCond(int overCountGTE, int overCountLTE,
                                                  int offset, int pageSize) {
        String hql = "select new edu.nju.model.machine.MachineBasicInfo(" +
                "u.uid, u.codeValue, l.cityName, m.power, m.mode, u.bindTime, m.overCount, m.heat) from " +
                "User u, MachineLatestStatus m, Location l " +
                "where u.dataType <> -1 and u.uid=m.uid and u.cityId=l.cityId " +
                "and m.overCount >= ?0 and m.overCount < ?1";
        return executeHQL(hql, offset, pageSize, overCountGTE, overCountLTE);
    }

    @Override
    public List<MachineBasicInfo> findByQueryCond(Date createTimeGTE, Date createTimeLTE,
                                                  int isPower,
                                                  int offset, int pageSize) {
        String hql = "select new edu.nju.model.machine.MachineBasicInfo(" +
                "u.uid, u.codeValue, l.cityName, m.power, m.mode, u.bindTime, m.overCount, m.heat) from " +
                "User u, MachineLatestStatus m, Location l " +
                "where u.dataType <> -1 and u.uid=m.uid and u.cityId=l.cityId and m.power = ?0 and " +
                "unix_timestamp(u.bindTime) between unix_timestamp(?1) and unix_timestamp(?2)";
        return executeHQL(hql, offset, pageSize, isPower, createTimeGTE, createTimeLTE);
    }

    @Override
    public List<MachineBasicInfo> findByQueryCond(Date createTimeGTE, Date createTimeLTE,
                                                  int overCountGTE, int overCountLTE,
                                                  int offset, int pageSize) {
        String hql = "select new edu.nju.model.machine.MachineBasicInfo(" +
                "u.uid, u.codeValue, l.cityName, m.power, m.mode, u.bindTime, m.overCount, m.heat) from " +
                "User u, MachineLatestStatus m, Location l " +
                "where u.dataType <> -1 and u.uid=m.uid and u.cityId=l.cityId " +
                "and m.overCount >= ?0 and m.overCount < ?1 and " +
                "unix_timestamp(u.bindTime) between unix_timestamp(?2) and unix_timestamp(?3)";
        return executeHQL(hql, offset, pageSize, overCountGTE, overCountLTE, createTimeGTE, createTimeLTE);
    }

    @Override
    public List<MachineBasicInfo> findByQueryCond(int overCountGTE, int overCountLTE,
                                                  int isPower,
                                                  int offset, int pageSize) {
        String hql = "select new edu.nju.model.machine.MachineBasicInfo(" +
                "u.uid, u.codeValue, l.cityName, m.power, m.mode, u.bindTime, m.overCount, m.heat) from " +
                "User u, MachineLatestStatus m, Location l " +
                "where u.dataType <> -1 and u.uid=m.uid and u.cityId=l.cityId " +
                "and m.overCount >= ?0 and m.overCount < ?1 and " +
                "m.power = ?2";
        return executeHQL(hql, offset, pageSize, overCountGTE, overCountLTE, isPower);
    }

    @Override
    public List<MachineBasicInfo> findByQueryCond(Date createTimeGTE, Date createTimeLTE,
                                                  int isPower,
                                                  int overCountGTE, int overCountLTE,
                                                  int offset, int pageSize) {
        String hql = "select new edu.nju.model.machine.MachineBasicInfo(" +
                "u.uid, u.codeValue, l.cityName, m.power, m.mode, u.bindTime, m.overCount, m.heat) from " +
                "User u, MachineLatestStatus m, Location l " +
                "where u.dataType <> -1 and u.uid=m.uid and u.cityId=l.cityId " +
                "and m.overCount >= ?0 and m.overCount < ?1 and m.power = ?2 and " +
                "unix_timestamp(u.bindTime) between unix_timestamp(?3) and unix_timestamp(?4)";
        return executeHQL(hql, offset, pageSize, overCountGTE, overCountLTE, isPower,
                createTimeGTE, createTimeLTE);
    }

    private List<MachineBasicInfo> executeHQL(String hql, int offset, int pageSize, Object... values) {
        Query query = getSession().createQuery(hql);
        query.setFirstResult(offset);
        query.setMaxResults(pageSize);
        if (values != null) {
            int count = values.length;
            for (int i = 0; i < count; i++) {
                query.setParameter(i, values[i]);
            }
        }
        return query.list();
    }
}
