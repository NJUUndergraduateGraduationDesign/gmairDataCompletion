package edu.nju.shiro;

import edn.nju.enums.UserRoleEnum;
import edu.nju.model.User;
import edu.nju.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.pam.UnsupportedTokenException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

/**
 * @author ：tsl
 * @date ：Created in 2020/2/23 16:38
 * @description：
 */

public class UserRealm extends AuthorizingRealm {
    /*
    配置懒加载，否则与Spring初始化冲突，导致@Transactional不可用
     */
    @Autowired
    @Lazy
    UserService userService;

    /*
    分身份用户的授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String uid = (String) principalCollection.getPrimaryPrincipal();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        if (ShiroUtil.isAdmin(uid)) {
            info.addRole(UserRoleEnum.ADMIN.getName());
        } else {
            info.addRole(UserRoleEnum.USER.getName());
        }
        return info;
    }

    /*
    分身份用户的认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String uid = token.getUsername();
        if (ShiroUtil.isAdmin(uid)) {
            return new SimpleAuthenticationInfo(uid, "", getName());
        } else {
            User user = userService.findByUid(uid);
            if (null == user || user.getDataType() < 0) {
                throw new UnsupportedTokenException("uid错误");
            } else {
                return new SimpleAuthenticationInfo(uid, "", getName());
            }
        }
    }
}
