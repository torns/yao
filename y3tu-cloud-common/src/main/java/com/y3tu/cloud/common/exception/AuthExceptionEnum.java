package com.y3tu.cloud.common.exception;

import com.y3tu.tool.core.exception.IError;

/**
 * @author y3tu
 * @date 2019-05-10
 */
public enum AuthExceptionEnum implements IError {

    INVALID_REQUEST("401", "无效请求"),
    INVALID_CLIENT("402", "无效client_id"),
    INVALID_GRANT("403", "无效授权"),
    INVALID_SCOPE("404", "无效scope"),
    INVALID_TOKEN("405", "无效token"),
    INSUFFICIENT_SCOPE("410", "授权不足"),
    REDIRECT_URI_MISMATCH("420", "redirect url不匹配"),
    ACCESS_DENIED("430", "拒绝访问"),
    METHOD_NOT_ALLOWED("440", "不支持该方法"),
    SERVER_ERROR("450", "权限服务错误"),
    UNAUTHORIZED_CLIENT("450", "未授权客户端"),
    UNAUTHORIZED("470", "未授权"),
    UNSUPPORTED_RESPONSE_TYPE("480", " 支持的响应类型"),
    UNSUPPORTED_GRANT_TYPE("490", "不支持的授权类型");

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

