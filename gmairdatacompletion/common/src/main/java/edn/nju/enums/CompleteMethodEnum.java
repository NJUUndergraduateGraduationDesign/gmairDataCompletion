package edn.nju.enums;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * @author ：tsl
 * @date ：Created in 2020/2/25 15:30
 * @description：enum of completeMethod:none,
 */

@AllArgsConstructor
@Getter
public enum CompleteMethodEnum {
    NONE(0,"none"),
    MEAN(1,"mean"),
    USE_PREVIOUS(2,"usePrevious");//原始数据为0

    private int code;
    private String name;

    public static List<Integer> getAllCompleteMethodCode(){
        return Lists.newArrayList(MEAN.getCode(),USE_PREVIOUS.getCode());
    }

    public static List<Integer> getAllCode(){
        return Lists.newArrayList(NONE.getCode(),MEAN.getCode(),USE_PREVIOUS.getCode());
    }

    public static boolean isValidCode(Integer code){
        return getAllCode().contains(code);
    }
}
