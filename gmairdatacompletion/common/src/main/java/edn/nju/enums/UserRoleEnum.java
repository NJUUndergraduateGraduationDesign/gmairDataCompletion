package edn.nju.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author ：tsl
 * @date ：Created in 2020/2/23 14:30
 * @description：enum of user:admin and user
 */

@Getter
@AllArgsConstructor
public enum UserRoleEnum {
    ADMIN(0,"admin"),USER(1,"user");

    private int code;
    private String name;
}
