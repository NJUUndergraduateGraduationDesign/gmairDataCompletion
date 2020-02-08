package edu.nju.gmairdatacompletion.serviceImpl;

import edu.nju.gmairdatacompletion.dao.UserDao;
import edu.nju.gmairdatacompletion.model.User;
import edu.nju.gmairdatacompletion.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public long addUser(User user) {
        return userDao.addUser(user);
    }
}
