package edu.nju.service;

import edu.nju.model.User;

import java.util.Date;
import java.util.List;

public interface UserService {
    User findByUid(String uid);

    List<User> findByQueryCond(int offset, int pageSize);

    List<User> findByQueryCond(int offset, int pageSize, Date createTimeGTE, Date createTimeLTE);

    void update(User user);
}
