package edu.nju.shiro;

import edn.nju.constant.Constant;
import edn.nju.enums.UserRoleEnum;
import edu.nju.model.User;
import edu.nju.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.pam.UnsupportedTokenException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

/**
 * @author ：tsl
 * @date ：Created in 2020/2/23 16:38
 * @description：
 */

@Slf4j
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
        CustomizedToken token = (CustomizedToken) authenticationToken;
        String role = token.getRole();
        String uid = token.getUsername();
        if (UserRoleEnum.ADMIN.getName().equals(role)) {
            if (ShiroUtil.isAdmin(uid)) {
                ByteSource salt = ByteSource.Util.bytes(Constant.Admin.DEFAULT_ADMIN_SALT);
                return new SimpleAuthenticationInfo(uid, Constant.Admin.DEFAULT_ADMIN_PASSWORD, salt, getName());
            } else {
                throw new UnsupportedTokenException("userName错误");
            }
        } else if (UserRoleEnum.USER.getName().equals(role)) {
            User user = userService.findByUid(uid);
            if (null != user && user.getDataType() >= 0) {
                ByteSource salt = ByteSource.Util.bytes(user.getCodeValue());
                return new SimpleAuthenticationInfo(uid, user.getPassword(), salt, getName());
            } else {
                throw new UnsupportedTokenException("userName错误");
            }
        } else {
            throw new UnsupportedTokenException("userRole错误");
        }
    }
}
