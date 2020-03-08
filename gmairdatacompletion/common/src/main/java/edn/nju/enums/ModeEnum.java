package edn.nju.enums;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author ：tsl
 * @date ：Created in 2020/3/8 17:12
 * @description：
 */

@Getter
@AllArgsConstructor
public enum ModeEnum {
    AUTO(0, "auto","自动"),
    MANUAL(1, "manual","手动"),
    SLEEP(2, "sleep","睡眠");//原始数据为0

    private int code;
    private String name;
    private String cName;

    public static List<Integer> getAllCode() {
        return Lists.newArrayList(AUTO.getCode(), MANUAL.getCode(), SLEEP.getCode());
    }

    public static boolean isValidCode(Integer code) {
        return getAllCode().contains(code);
    }

    public static ModeEnum getEnumByCode(Integer code){
        for(ModeEnum e:ModeEnum.values()){
            if(e.getCode()==code){
                return e;
            }
        }
        return null;
    }
}
