package com.y3tu.yao.generator.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 列的数据信息
 * @author y3tu
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ColumnInfo {

    /** 数据库字段名称 **/
    private Object name;

    /** 允许空值 **/
    private Object isNullable;

    /** 数据库字段类型 **/
    private Object typeName;

    /** 数据库字段注释 **/
    private Object comment;

    /** 数据库字段键类型 **/
    private Object columnKey;

    /** 额外的参数 **/
    private Object extra;

    /** 查询 1:模糊 2：精确 **/
    private String columnQuery;

    /** 是否在列表显示 **/
    private String columnShow;
}
