package edu.nju.dao;

import edu.nju.model.User;

import java.util.Date;
import java.util.List;

public interface UserDao extends BaseDao<User>{
    User findByUid(String uid);

    List<User> findByQueryCond(Date createTimeGTE, Date createTimeLTE);

    List<User> findAllValidUsers();
}
