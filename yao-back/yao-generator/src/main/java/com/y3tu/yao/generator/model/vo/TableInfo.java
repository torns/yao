package com.y3tu.yao.generator.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 表信息
 *
 * @author y3tu
 * @date 2019-07-02
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TableInfo {
    /**
     * 名称
     */
    private String tableName;
    /**
     * 备注
     */
    private String comment;
    /**
     * 主键
     */
    private ColumnInfo pk;
    /**
     * 列名
     */
    private List<ColumnInfo> columns;
    /**
     * 驼峰类型
     */
    private String caseClassName;
    /**
     * 普通类型
     */
    private String lowerClassName;
}
