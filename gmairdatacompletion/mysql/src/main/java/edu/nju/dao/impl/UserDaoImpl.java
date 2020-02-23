package edu.nju.dao.impl;

import edu.nju.dao.UserDao;
import edu.nju.model.User;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao {
    @Override
    public User findByUid(String uid) {
        return getUniqueResultByHQL("SELECT u FROM User u WHERE u.uid = ?0", uid);
    }
}
