package com.y3tu.cloud.auth.exception;

import com.y3tu.tool.core.exception.BaseException;
import com.y3tu.tool.core.exception.ExceptionUtil;
import com.y3tu.tool.core.exception.IError;
import com.y3tu.tool.core.text.StringUtils;

/**
 * 权限异常
 *
 * @author y3tu
 * @date 2018/9/29
 */
public class AuthException extends BaseException {

    public AuthException() {
        super();
    }

    public AuthException(String message) {
        super(message);
    }

    public AuthException(Throwable cause) {
        super(ExceptionUtil.getMessage(cause), cause);
    }

    public AuthException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthException(String messageTemplate, Object... params) {
        super(StringUtils.format(messageTemplate, params));
    }

    public AuthException(Throwable throwable, String messageTemplate, Object... params) {
        super(StringUtils.format(messageTemplate, params), throwable);
    }

    public AuthException(IError error) {
        super();
        this.errorMessage = null;
        this.error = error;
    }

    public AuthException(String message, IError error) {
        this(message);
        this.errorMessage = message;
        this.error = error;
    }

    public AuthException(String message, Throwable cause, IError error) {
        this(message, cause);
        this.errorMessage = message;
        this.error = error;
    }

    public AuthException(Throwable cause, IError error) {
        this(cause);
        this.errorMessage = null;
        this.error = error;
    }
}
