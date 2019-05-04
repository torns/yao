package com.y3tu.cloud.auth.exception;

import com.y3tu.tool.core.exception.BaseException;
import com.y3tu.tool.core.exception.ExceptionUtil;
import com.y3tu.tool.core.util.StrUtil;

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
        super(StrUtil.format(messageTemplate, params));
    }

    public AuthException(Throwable throwable, String messageTemplate, Object... params) {
        super(StrUtil.format(messageTemplate, params), throwable);
    }
}
