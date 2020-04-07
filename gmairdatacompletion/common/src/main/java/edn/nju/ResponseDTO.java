package edn.nju;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseDTO {
    //返回码:见ResponseCodeEnum
    private Integer code;
    //返回描述:与ResponseCode对应,可以自定义详细描述
    private String desc;
    //返回数据
    private Object data;

    public static ResponseDTO ofSuccess() {
        return new ResponseDTO(ResponseCodeEnum.RESPONSE_OK.getCode(), ResponseCodeEnum.RESPONSE_OK.getDesc(), null);
    }

    public static ResponseDTO ofSuccess(Object data) {
        return new ResponseDTO(ResponseCodeEnum.RESPONSE_OK.getCode(), ResponseCodeEnum.RESPONSE_OK.getDesc(), data);
    }

    public static ResponseDTO ofParamError() {
        return new ResponseDTO(ResponseCodeEnum.RESPONSE_PARAM_ERROR.getCode(), ResponseCodeEnum.RESPONSE_PARAM_ERROR.getDesc(), null);
    }

    public static ResponseDTO ofParamError(String desc) {
        return new ResponseDTO(ResponseCodeEnum.RESPONSE_PARAM_ERROR.getCode(), desc, null);
    }

    public static ResponseDTO ofServerError() {
        return new ResponseDTO(ResponseCodeEnum.RESPONSE_SERVER_ERROR.getCode(), ResponseCodeEnum.RESPONSE_SERVER_ERROR.getDesc(), null);
    }

    public static ResponseDTO ofUnauthorizedError() {
        return new ResponseDTO(ResponseCodeEnum.RESPONSE_UNAUTHORIZED_ERROR.getCode(), ResponseCodeEnum.RESPONSE_UNAUTHORIZED_ERROR.getDesc(), null);
    }

    public static ResponseDTO ofUnauthenticatedError() {
        return new ResponseDTO(ResponseCodeEnum.RESPONSE_UNAUTHENTICATED_ERROR.getCode(), ResponseCodeEnum.RESPONSE_UNAUTHENTICATED_ERROR.getDesc(), null);
    }
}

