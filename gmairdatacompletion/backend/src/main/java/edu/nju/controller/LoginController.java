package edu.nju.controller;

import edn.nju.ResponseDTO;
import edu.nju.service.UserService;
import edu.nju.shiro.ShiroUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：tsl
 * @date ：Created in 2020/2/23 17:37
 * @description：controller of login
 */

@RestController
public class LoginController {
    @Autowired
    UserService userService;

    @PostMapping("/login")
    public ResponseDTO login(@RequestParam String uid) {
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token=new UsernamePasswordToken(uid,"");
        try {
            subject.login(token);
            return ResponseDTO.ofSuccess();
        } catch (AuthenticationException e) {
            return ResponseDTO.ofParamError("uid错误");
        }
    }

    @PostMapping("/logout")
    public ResponseDTO logout(){
        ShiroUtil.logout();
        return ResponseDTO.ofSuccess();
    }
}
