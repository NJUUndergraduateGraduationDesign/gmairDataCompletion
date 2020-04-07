package edu.nju.shiro;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * @author ：tsl
 * @date ：Created in 2020/4/3 18:20
 * @description：customized token
 */

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomizedToken extends UsernamePasswordToken {
    //用户身份类型，具体见UserRoleEnum
    private String role;

    public CustomizedToken(String username, String password, String role) {
        super(username, password);
        this.role = role;
    }
}
