package com.y3tu.yao.generator.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 列的数据信息
 *
 * @author y3tu
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ColumnInfo {

    /**
     * 数据库字段名称
     **/
    private String name;

    /**
     * 允许空值
     **/
    private boolean isNullable;

    /**
     * 数据库字段类型
     **/
    private String typeName;

    /**
     * 数据库字段注释
     **/
    private String comment;

    /**
     * 额外的参数
     **/
    private Object extra;

    /**
     * 查询 1:模糊 2：精确
     **/
    private int columnQuery;

    /**
     * 是否在列表显示
     **/
    private boolean columnShow;
}
