package com.y3tu.yao.generator.exception;

import com.y3tu.tool.core.exception.BaseException;
import com.y3tu.tool.core.exception.IError;

/**
 * 代码生成异常
 *
 * @author y3tu
 * @date 2019-07-04
 */
public class GeneratorException extends BaseException {

    public GeneratorException(String message) {
        super(message);
    }

    public GeneratorException(Throwable cause) {
        super(cause);
    }

    public GeneratorException(String message, Throwable cause) {
        super(message, cause);
    }

    public GeneratorException(String messageTemplate, Object... params) {
        super(messageTemplate, params);
    }

    public GeneratorException(Throwable throwable, String messageTemplate, Object... params) {
        super(throwable, messageTemplate, params);
    }

    public GeneratorException(IError error) {
        super(error);
    }

    public GeneratorException(String message, IError error) {
        super(message, error);
    }

    public GeneratorException(String message, Throwable cause, IError error) {
        super(message, cause, error);
    }

    public GeneratorException(Throwable cause, IError error) {
        super(cause, error);
    }
}
