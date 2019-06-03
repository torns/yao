package com.y3tu.cloud.common.enums;

/**
 * 资源类型枚举
 */
public enum ResourceTypeEnum {

    TOP_MENU(-1, "顶层菜单"),

    MENU(0, "普通菜单"),

    BUTTON(1, "按钮");


    private int code;

    private String message;

    ResourceTypeEnum(int code, String message) {
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
