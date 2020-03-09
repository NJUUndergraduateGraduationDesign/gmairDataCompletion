package edu.nju.dao.impl;

import edu.nju.dao.UserDao;
import edu.nju.model.User;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao {
    @Override
    public User findByUid(String uid) {
        return getUniqueResultByHQL("SELECT u FROM User u WHERE u.uid = ?0", uid);
    }

    @Override
    public List<User> findByQueryCond(Date createTimeGTE, Date createTimeLTE) {
        String hql = "SELECT u FROM User u WHERE u.dataType <> -1 AND " +
                "unix_timestamp(u.bindTime) BETWEEN unix_timestamp(?0) AND unix_timestamp(?1)";
        return getListByHQL(hql, createTimeGTE, createTimeLTE);
    }

    @Override
    public List<User> findAllValidUsers() {
        String hql = "SELECT u FROM User u WHERE u.dataType <> -1";
        return getListByHQL(hql);
    }

    @Override
    public List<User> findAllUsersByDataType(int code) {
        String hql = "SELECT u FROM User u WHERE u.dataType = ?0";
        return getListByHQL(hql,code);
    }

    @Override
    public List<String> findAllUidsByDateType(int code) {
        String hql = "SELECT u.uid FROM User u WHERE u.dataType = ?0";
        return getObjListByHQL(hql,code);
    }

    @Override
    public int count(){
        String hql = "select count(*) from User";
        return ((Long)getUniqueColumnByHQL(hql)).intValue();
    }

    @Override
    public int countByBindTime(Date startTime, Date endTime) {
        String hql = "select count(*) from User u where " +
                "unix_timestamp(u.bindTime) BETWEEN unix_timestamp(?0) AND unix_timestamp(?1)";
        return ((Long)getUniqueColumnByHQL(hql,startTime,endTime)).intValue();
    }

    @Override
    public int countByProvince(String province) {
        String hql = "select count(*) from User u, Location l where " +
                "u.cityId = l.cityId and l.provinceName= ?0";
        return ((Long)getUniqueColumnByHQL(hql,province)).intValue();
    }

    @Override
    public int countByProvinceAndBindTime(String province, Date startTime, Date endTime) {
        String hql = "select count(*) from User u, Location l where " +
                "u.cityId = l.cityId and l.provinceName= ?0 and " +
                "unix_timestamp(u.bindTime) BETWEEN unix_timestamp(?1) AND unix_timestamp(?2)";
        return ((Long)getUniqueColumnByHQL(hql,province,startTime,endTime)).intValue();
    }
}
