package edu.nju.shiro;

import edn.nju.constant.Constant;
import edn.nju.enums.UserRoleEnum;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.Md5Hash;

/**
 * @author ：tsl
 * @date ：Created in 2020/2/23 18:12
 * @description：util of shiro
 */

public class ShiroUtil {
    public static void main(String[] args) {
        System.out.println(encrypt("admin","2020"));
    }
    public static boolean isAdmin(String uid) {
        return Constant.Admin.DEFAULT_ADMIN_USERNAME.equals(uid);
    }

    public static void logout() {
        SecurityUtils.getSubject().logout();
    }

    public static String getCurrentUid() {
        return (String) SecurityUtils.getSubject().getPrincipal();
    }

    public static String getCurrentRole() {
        return isAdmin(getCurrentUid()) ? UserRoleEnum.ADMIN.getName() : UserRoleEnum.USER.getName();
    }

    /**
     * 密码+盐值md5加密三次
     * @param originalPassword
     * @param salt
     * @return 加密结果
     */
    public static String encrypt(String originalPassword, String salt) {
        return new Md5Hash(originalPassword, salt, 3).toString();
    }
}
