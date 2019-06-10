package com.y3tu.yao.upms.emums;

/**
 * @author y3tu
 * @date 2018/10/27
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
