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
}
