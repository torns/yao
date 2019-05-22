package com.y3tu.cloud.upms.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.y3tu.tool.web.base.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 资源表
 * </p>
 *
 * @author y3tu
 * @date 2018-08-05
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_resource")
@AllArgsConstructor
@NoArgsConstructor
public class Resource extends BaseEntity {

    /**
     * 主键
     */
    @TableId
    private String id;
    /**
     * 创建人
     */
    @TableField("create_by")
    private String createBy;
    /**
     * 创建时间
     */
    @TableField("create_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /**
     * 是否删除 0:否;1:是
     */
    @TableField("del_flag")
    private Integer delFlag;
    /**
     * 更新人
     */
    @TableField("update_by")
    private String updateBy;
    /**
     * 更新时间
     */
    @TableField("update_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
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
    @TableField("parent_id")
    private String parentId;
    /**
     * 权限类型
     */
    private Integer type;
    /**
     * 排列权重顺序
     */
    private int sort;
    /**
     * 前台页面组件
     */
    private String component;
    /**
     * 菜单路径
     */
    private String path;
    /**
     * 图标
     */
    private String icon;
    /**
     * 权限编码
     */
    private String permission;
    /**
     * 按钮类型
     */
    @TableField("button_type")
    private String buttonType;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 跳转url
     */
    private String url;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
