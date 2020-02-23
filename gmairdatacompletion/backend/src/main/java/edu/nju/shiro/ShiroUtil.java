package edu.nju.shiro;

import edn.nju.constant.Constant;
import org.apache.shiro.SecurityUtils;

import java.security.Security;

/**
 * @author ：tsl
 * @date ：Created in 2020/2/23 18:12
 * @description：util of shiro
 */

public class ShiroUtil {
    public static boolean isAdmin(String uid){
        return Constant.Admin.DEFAULT_ADMIN_USERNAME.equals(uid);
    }

    public static void logout() {
        SecurityUtils.getSubject().logout();
    }

    public static String getCurrentUid(){
        return (String)SecurityUtils.getSubject().getPrincipal();
    }
}
