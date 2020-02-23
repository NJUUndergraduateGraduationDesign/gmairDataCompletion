package edu.nju.service;

import edu.nju.model.User;

public interface UserService {
    User findByUid(String uid);

    void update(User user);
}
