package edu.nju.exception;

/**
 * @author ：tsl
 * @date ：Created in 2020/4/6 15:10
 * @description：参数错误异常
 */

public class ParamErrorException extends RuntimeException {
    /**
     * Constructs a new exception with the specified detail message.  The
     * cause is not initialized, and may subsequently be initialized by
     * a call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public ParamErrorException(String message) {
        super(message);
    }

    /**
     * Constructs a new exception with {@code null} as its detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     */
    public ParamErrorException() {
    }
}
