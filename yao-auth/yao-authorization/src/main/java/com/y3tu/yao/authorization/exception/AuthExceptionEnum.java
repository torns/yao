package com.y3tu.yao.authorization.exception;

import com.y3tu.tool.core.exception.IError;

/**
 * @author y3tu
 * @date 2019-05-10
 */
public enum AuthExceptionEnum implements IError {

    /**
     * 错误编码
     */
    INVALID_GRANT("401", "用户名密码无效"),
    ACCESS_DENIED("403", "拒绝访问"),
    UNAUTHORIZED("403", "未授权"),
    INVALID_TOKEN("500", "无效token"),
    INVALID_REQUEST("500", "无效请求"),
    INVALID_CLIENT("500", "无效client_id"),
    INVALID_SCOPE("500", "无效scope"),
    INSUFFICIENT_SCOPE("500", "授权不足"),
    REDIRECT_URI_MISMATCH("500", "redirect url不匹配"),
    METHOD_NOT_ALLOWED("500", "不支持该方法"),
    SERVER_ERROR("500", "权限服务错误"),
    UNAUTHORIZED_CLIENT("500", "未授权客户端"),
    UNSUPPORTED_RESPONSE_TYPE("500", " 支持的响应类型"),
    UNSUPPORTED_GRANT_TYPE("500", "不支持的授权类型");

    /**
     * 错误类型码
     */
    private String code;
    /**
     * 错误类型描述信息
     */
    private String msg;

    AuthExceptionEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return msg;
    }
}

