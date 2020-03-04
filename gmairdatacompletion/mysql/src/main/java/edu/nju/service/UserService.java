package edu.nju.service;

import edu.nju.model.User;

import java.util.Date;
import java.util.List;

public interface UserService {
    User findByUid(String uid);

    List<User> findByQueryCond(Date createTimeGTE, Date createTimeLTE);

    List<User> findAllUsers();

    List<User> findAllValidUsers();

    void update(User user);

    int count();

    int countByBindTime(Date startTime, Date endTime);

    int countByProvince(String province);

    int countByProvinceAndBindTime(String province, Date startTime, Date endTime);
}
