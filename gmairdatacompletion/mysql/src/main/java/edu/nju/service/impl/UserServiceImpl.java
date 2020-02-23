package edu.nju.service.impl;

import edu.nju.dao.UserDao;
import edu.nju.model.User;
import edu.nju.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public void update(User user) {
        userDao.update(user);
    }
}
