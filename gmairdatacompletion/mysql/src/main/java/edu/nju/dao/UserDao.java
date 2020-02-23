package edu.nju.dao;

import edu.nju.model.User;

public interface UserDao extends BaseDao<User>{
    User findByUid(String uid);
}
