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
    public List<User> findByQueryCond(Date createTimeGTE, Date createTimeLTE) {
        return userDao.findByQueryCond(createTimeGTE, createTimeLTE);
    }

    @Override
    public List<User> findAllUsers() {
        return userDao.getAll();
    }

    @Override
    public List<User> findAllValidUsers() {
        return userDao.findAllValidUsers();
    }

    @Override
    public void update(User user) {
        userDao.update(user);
    }

    @Override
    public int count() {
        return userDao.count();
    }

    @Override
    public int countByBindTime(Date startTime, Date endTime) {
        return userDao.countByBindTime(startTime, endTime);
    }

    @Override
    public int countByProvince(String province) {
        return userDao.countByProvince(province);
    }

    @Override
    public int countByProvinceAndBindTime(String province, Date startTime, Date endTime) {
        return userDao.countByProvinceAndBindTime(province, startTime, endTime);
    }


}
