package com.y3tu.cloud.gateway.web.exception;

import com.y3tu.tool.core.exception.BaseException;
import com.y3tu.tool.core.exception.ExceptionUtil;
import com.y3tu.tool.core.exception.IError;
import com.y3tu.tool.core.text.StringUtils;

/**
 * 验证码异常
 *
 * @author y3tu
 * @date 2018/10/2
 */
public class ValidateCodeException extends BaseException {

    public ValidateCodeException() {
        super();
    }

    public ValidateCodeException(String message) {
        super(message);
    }

    public ValidateCodeException(Throwable cause) {
        super(ExceptionUtil.getMessage(cause), cause);
    }

    public ValidateCodeException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValidateCodeException(String messageTemplate, Object... params) {
        super(StringUtils.format(messageTemplate, params));
    }

    public ValidateCodeException(Throwable throwable, String messageTemplate, Object... params) {
        super(StringUtils.format(messageTemplate, params), throwable);
    }

    public ValidateCodeException(IError error) {
        super();
        this.message = null;
        this.error = error;
    }

    public ValidateCodeException(String message, IError error) {
        this(message);
        this.message = message;
        this.error = error;
    }

    public ValidateCodeException(String message, Throwable cause, IError error) {
        this(message, cause);
        this.message = message;
        this.error = error;
    }

    public ValidateCodeException(Throwable cause, IError error) {
        this(cause);
        this.message = null;
        this.error = error;
    }

}
