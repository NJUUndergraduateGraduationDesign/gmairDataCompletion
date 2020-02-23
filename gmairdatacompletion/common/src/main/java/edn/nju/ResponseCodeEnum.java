package edn.nju;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseCodeEnum {
    RESPONSE_OK(200,"成功"),
    RESPONSE_PARAM_ERROR(400,"参数错误"),
    RESPONSE_SERVER_ERROR(500,"服务器错误");

    private Integer code;
    private String desc;
}
