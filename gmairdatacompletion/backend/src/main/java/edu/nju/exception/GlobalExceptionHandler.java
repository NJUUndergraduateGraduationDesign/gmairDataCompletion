package edu.nju.exception;

import edn.nju.ResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author ：tsl
 * @date ：Created in 2020/2/25 13:25
 * @description：global Exception handler
 */

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = UnauthorizedException.class)
    @ResponseBody
    public ResponseDTO handleUnauthorizedException(UnauthorizedException e) {
        log.error("Exception:", e);
        return ResponseDTO.ofUnauthorizedError();
    }

    @ExceptionHandler(value = UnauthenticatedException.class)
    @ResponseBody
    public ResponseDTO handleUnauthenticatedException(ShiroException e) {
        log.error("Exception:", e);
        return ResponseDTO.ofUnauthenticatedError();
    }

    @ExceptionHandler(value = ParamErrorException.class)
    @ResponseBody
    public ResponseDTO handleParamErrorException(ParamErrorException e) {
        log.error("Exception:", e);
        if (StringUtils.isEmpty(e.getMessage())) {
            return ResponseDTO.ofParamError();
        }
        return ResponseDTO.ofParamError(e.getMessage());
    }

    @ExceptionHandler(value = RuntimeException.class)
    @ResponseBody
    public ResponseDTO handleRuntimeException(RuntimeException e) {
        log.error("Exception:", e);
        return ResponseDTO.ofServerError();
    }
}
