package com.y3tu.cloud.upms.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;

import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import com.y3tu.tool.web.base.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 菜单表
 * </p>
 *
 * @author y3tu
 * @date 2018-08-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("menus")
public class Menus extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 菜单id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 父菜单id
     */
    @TableField("parent_id")
    private Integer parentId;
    /**
     * 菜单类型
     */
    private String type;
    /**
     * 菜单路径
     */
    private String href;
    /**
     * 菜单图标
     */
    private String icon;
    /**
     * 菜单名称
     */
    private String name;
    /**
     * 菜单简介
     */
    private String description;
    /**
     * 菜单序列
     */
    @TableField("order_num")
    private Integer orderNum;
    /**
     * 创建时间
     */
    @TableField("created_time")
    private Date createdTime;
    /**
     * 更新时间
     */
    @TableField("updated_time")
    private Date updatedTime;
    /**
     * 创建人
     */
    @TableField("created_by")
    private String createdBy;
    /**
     * 更新人
     */
    @TableField("updated_by")
    private String updatedBy;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
