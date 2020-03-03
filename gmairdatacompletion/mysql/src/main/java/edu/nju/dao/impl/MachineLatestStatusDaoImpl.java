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
                                        implements MachineLatestStatusDao{
    @Override
    public List<MachineBasicInfo> findByQueryCond(Date createTimeGTE, Date createTimeLTE,
                                                  int offset, int pageSize) {
        String hql = "select new edu.nju.model.machine.MachineBasicInfo(" +
                "u.uid, u.codeValue, u.cityId, m.power,m.mode, u.bindTime, 0, m.heat) from " +
                "User u join MachineLatestStatus m on u.uid=m.uid " +
                "where u.dataType <> -1 and " +
                "unix_timestamp(u.bindTime) between unix_timestamp(?0) and unix_timestamp(?1)";
        return executeHQL(hql, offset, pageSize, createTimeGTE, createTimeLTE);
    }

    @Override
    public List<MachineBasicInfo> findByQueryCond(int isPower,
                                                  int offset, int pageSize) {
        String hql = "select new edu.nju.model.machine.MachineBasicInfo(" +
                "u.uid, u.codeValue, u.cityId, m.power,m.mode, u.bindTime, 0, m.heat) from " +
                "User u join MachineLatestStatus m on u.uid=m.uid " +
                "where u.dataType <> -1 and m.power = ?0";
        return executeHQL(hql, offset, pageSize, isPower);
    }

    @Override
    public List<MachineBasicInfo> findByQueryCond(int overCountGTE, int overCountLTE,
                                                  int offset, int pageSize) {
        return null;
    }

    @Override
    public List<MachineBasicInfo> findByQueryCond(Date createTimeGTE, Date createTimeLTE,
                                                  int isPower,
                                                  int offset, int pageSize) {
        String hql = "select new edu.nju.model.machine.MachineBasicInfo(" +
                "u.uid, u.codeValue, u.cityId, m.power,m.mode, u.bindTime, 0, m.heat) from " +
                "User u join MachineLatestStatus m on u.uid=m.uid " +
                "where u.dataType <> -1 and m.power = ?0 and " +
                "unix_timestamp(u.bindTime) between unix_timestamp(?1) and unix_timestamp(?2)";
        return executeHQL(hql, offset, pageSize, isPower, createTimeGTE, createTimeLTE);
    }

    @Override
    public List<MachineBasicInfo> findByQueryCond(Date createTimeGTE, Date createTimeLTE,
                                                  int overCountGTE, int overCountLTE,
                                                  int offset, int pageSize) {
        return null;
    }

    @Override
    public List<MachineBasicInfo> findByQueryCond(int overCountGTE, int overCountLTE,
                                                  int isPower,
                                                  int offset, int pageSize) {
        return null;
    }

    @Override
    public List<MachineBasicInfo> findByQueryCond(Date createTimeGTE, Date createTimeLTE,
                                                  int isPower,
                                                  int overCountGTE, int overCountLTE,
                                                  int offset, int pageSize) {
        return null;
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
