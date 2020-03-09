package edu.nju.service;

import edu.nju.model.User;

import java.util.Date;
import java.util.List;

public interface UserService {
    User findByUid(String uid);

    List<User> findByQueryCond(Date createTimeGTE, Date createTimeLTE);

    List<User> findAllUsers();

    List<User> findAllValidUsers();

    List<User> findAllV2Users();

    List<User> findAllV3Users();

    List<String> findAllV2Uids();

    List<String> findAllV3Uids();

    void update(User user);

    int count();

    int countByBindTime(Date startTime, Date endTime);

    int countByProvince(String province);

    int countByProvinceAndBindTime(String province, Date startTime, Date endTime);
}
