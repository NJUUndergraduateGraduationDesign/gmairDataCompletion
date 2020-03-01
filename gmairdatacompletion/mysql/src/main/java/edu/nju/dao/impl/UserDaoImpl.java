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
    public List<User> findByQueryCond(int offset, int pageSize) {
        String hql = "SELECT * FROM User WHERE data_type <> -1 LIMIT ?0, ?1";
        return getListByHQL(hql, offset, pageSize);
    }

    @Override
    public List<User> findByQueryCond(int offset, int pageSize, Date createTimeGTE, Date createTimeLTE) {
        String hql = "SELECT * FROM User WHERE data_type <> -1 AND " +
                "unix_timestamp(bind_time) BETWEEN unix_timestamp(?0) AND unix_timestamp(?1) LIMIT ?2, ?3";
        return getListByHQL(hql, createTimeGTE, createTimeLTE, offset, pageSize);
    }
}
