package edu.nju.exception;

import edn.nju.ResponseDTO;
import org.apache.shiro.ShiroException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author ：tsl
 * @date ：Created in 2020/2/25 13:25
 * @description：global Exception handler
 */

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = ShiroException.class)
    @ResponseBody
    public ResponseDTO handleShiroException(ShiroException e) {
        return ResponseDTO.ofShiroError();
    }

    @ExceptionHandler(value = RuntimeException.class)
    @ResponseBody
    public ResponseDTO handleRuntimeException(RuntimeException e) {
        return ResponseDTO.ofServerError();
    }
}
