package com.y3tu.yao.gateway.exception;

import com.y3tu.tool.core.exception.BaseException;
import com.y3tu.tool.core.exception.ErrorEnum;
import com.y3tu.tool.core.exception.IError;

/**
 * 未授权或者token过期异常
 * @author y3tu
 * @date 2019-05-17
 */
public class UnAuthorizedException extends BaseException {
    public UnAuthorizedException() {
    }

    public UnAuthorizedException(String message) {
        super(message);
    }

    public UnAuthorizedException(Throwable e) {
        super(e);
    }

    public UnAuthorizedException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnAuthorizedException(String messageTemplate, Object... params) {
        super(messageTemplate, params);
    }

    public UnAuthorizedException(Throwable throwable, String messageTemplate, Object... params) {
        super(throwable, messageTemplate, params);
    }

    public UnAuthorizedException(IError error) {
        super(error);
    }

    public UnAuthorizedException(String message, ErrorEnum error) {
        super(message, error);
    }

    public UnAuthorizedException(String message, Throwable cause, ErrorEnum error) {
        super(message, cause, error);
    }

    public UnAuthorizedException(Throwable cause, ErrorEnum error) {
        super(cause, error);
    }
}
