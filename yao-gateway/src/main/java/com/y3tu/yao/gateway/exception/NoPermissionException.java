package com.y3tu.yao.gateway.exception;

import com.y3tu.tool.core.exception.BaseException;
import com.y3tu.tool.core.exception.ErrorEnum;
import com.y3tu.tool.core.exception.IError;

/**
 * 没有权限异常
 */
public class NoPermissionException extends BaseException {
    public NoPermissionException() {
    }

    public NoPermissionException(String message) {
        super(message);
    }

    public NoPermissionException(Throwable e) {
        super(e);
    }

    public NoPermissionException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoPermissionException(String messageTemplate, Object... params) {
        super(messageTemplate, params);
    }

    public NoPermissionException(Throwable throwable, String messageTemplate, Object... params) {
        super(throwable, messageTemplate, params);
    }

    public NoPermissionException(IError error) {
        super(error);
    }

    public NoPermissionException(String message, ErrorEnum error) {
        super(message, error);
    }

    public NoPermissionException(String message, Throwable cause, ErrorEnum error) {
        super(message, cause, error);
    }

    public NoPermissionException(Throwable cause, ErrorEnum error) {
        super(cause, error);
    }
}
