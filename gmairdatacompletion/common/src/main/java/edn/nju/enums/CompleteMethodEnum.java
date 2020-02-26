package edn.nju.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author ：tsl
 * @date ：Created in 2020/2/25 15:30
 * @description：enum of completeMethod:none,
 */

@AllArgsConstructor
@Getter
public enum CompleteMethodEnum {
    NONE(0,"none");//原始数据为0

    private int code;
    private String name;
}
