package edu.nju.exception;

import edn.nju.ResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.ShiroException;
import org.checkerframework.checker.index.qual.SameLen;
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
    @ExceptionHandler(value = ShiroException.class)
    @ResponseBody
    public ResponseDTO handleShiroException(ShiroException e) {
        return ResponseDTO.ofShiroError();
    }

    @ExceptionHandler(value = RuntimeException.class)
    @ResponseBody
    public ResponseDTO handleRuntimeException(RuntimeException e) {
        log.error("Exception:",e);
        return ResponseDTO.ofServerError();
    }
}
