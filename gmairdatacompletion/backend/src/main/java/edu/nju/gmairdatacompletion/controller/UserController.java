package edu.nju.gmairdatacompletion.controller;

import edu.nju.gmairdatacompletion.model.User;
import edu.nju.gmairdatacompletion.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    @ResponseBody
    public Serializable add() {
        User user = new User();
        user.setUserName("a");
        return userService.addUser(user);
    }
}
