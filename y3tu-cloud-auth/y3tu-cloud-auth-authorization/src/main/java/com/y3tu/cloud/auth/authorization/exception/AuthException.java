package com.y3tu.cloud.auth.authorization.exception;

import com.y3tu.tool.core.exception.ExceptionUtil;
import com.y3tu.tool.core.text.StringUtils;

/**
 * 权限异常
 *
 * @author y3tu
 * @date 2018/9/29
 */
public class AuthException extends RuntimeException {

    public AuthException(Throwable e) {
        super(ExceptionUtil.getMessage(e), e);
    }

    public AuthException(String message) {
        super(message);
    }

    public AuthException(String messageTemplate, Object... params) {
        super(StringUtils.format(messageTemplate, params));
    }

    public AuthException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public AuthException(Throwable throwable, String messageTemplate, Object... params) {
        super(StringUtils.format(messageTemplate, params), throwable);
    }
}
