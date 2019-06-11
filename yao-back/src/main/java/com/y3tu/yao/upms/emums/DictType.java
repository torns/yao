package com.y3tu.yao.upms.emums;

/**
 * 字典类型枚举
 * @author y3tu
 */
public enum DictType {
    /**
     * SYS_DICT配置
     */
    DICT(0),
    /**
     * SYS_SQL配置
     */
    SQL(1);

    private int value;

    private DictType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
