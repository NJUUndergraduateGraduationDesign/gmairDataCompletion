package edn.nju.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: Bright Chan
 * @date: 2020/3/7 14:20
 * @description: TODO
 */

@AllArgsConstructor
@Getter
public enum PredictMethodEnum {
    GRADIENT(1, "gradient"),
    USE_PREVIOUS(2, "usePrevious");

    private int code;
    private String name;
}
