package edu.nju.service.impl;

import edu.nju.dao.UserDao;
import edu.nju.model.User;
import edu.nju.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User findByUid(String uid) {
        return userDao.findByUid(uid);
    }

    @Override
    public List<User> findByQueryCond(int offset, int pageSize) {
        return userDao.findByQueryCond(offset, pageSize);
    }

    @Override
    public List<User> findByQueryCond(int offset, int pageSize, Date createTimeGTE, Date createTimeLTE) {
        return userDao.findByQueryCond(offset, pageSize, createTimeGTE, createTimeLTE);
    }

    @Override
    public void update(User user) {
        userDao.update(user);
    }
}
