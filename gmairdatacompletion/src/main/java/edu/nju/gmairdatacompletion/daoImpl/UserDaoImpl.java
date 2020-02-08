package edu.nju.gmairdatacompletion.daoImpl;

import edu.nju.gmairdatacompletion.dao.BaseDao;
import edu.nju.gmairdatacompletion.dao.UserDao;
import edu.nju.gmairdatacompletion.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private BaseDao baseDao;

    @Override
    public long addUser(User user) {
        return (long) baseDao.add(user);
    }
}
