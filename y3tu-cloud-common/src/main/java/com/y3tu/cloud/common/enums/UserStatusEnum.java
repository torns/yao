package com.y3tu.cloud.common.enums;

/**
 * 用户状态枚举
 */
public enum UserStatusEnum {

    /**
     * 正常
     */
    NORMAL(0, "正常"),

    /**
     * 锁定
     */
    LOCK(1, "锁定");


    private int code;

    private String message;

    UserStatusEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
