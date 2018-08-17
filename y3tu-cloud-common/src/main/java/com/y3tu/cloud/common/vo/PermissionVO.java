package com.y3tu.cloud.common.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class PermissionVO implements Serializable {

    /**
     * 主键
     */
    private String id;
    /**
     * 创建人
     */
    private String createBy;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 是否删除 0:否;1:是
     */
    private Integer delFlag;
    /**
     * 更新人
     */
    private String updateBy;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 描述
     */
    private String description;
    /**
     * 权限名称
     */
    private String name;
    /**
     * 权限父Id
     */
    private String parentId;
    /**
     * 权限类型
     */
    private Integer type;
    private BigDecimal sortOrder;
    private String component;
    /**
     * 菜单路径
     */
    private String path;
    /**
     * 标题
     */
    private String title;
    /**
     * 图标
     */
    private String icon;
    /**
     * 级别
     */
    private Integer level;
    /**
     * 按钮类型
     */
    private String buttonType;
    /**
     * 状态
     */
    private Integer status;
}
