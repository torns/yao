package com.y3tu.cloud.common.enums;

/**
 * 数据状态枚举
 */
public enum DataStatusEnum {

    NORMAL(0, "正常"),
    DELETE(1, "删除");


    private int code;

    private String message;

    DataStatusEnum(int code, String message) {
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
