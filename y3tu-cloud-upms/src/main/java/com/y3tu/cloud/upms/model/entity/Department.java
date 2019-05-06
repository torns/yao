package com.y3tu.cloud.upms.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.y3tu.tool.web.base.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * 部门实体
 *
 * @author y3tu
 * @date 2018-11-26 19:47:53
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_department")
@Accessors(chain = true)
public class Department extends BaseEntity<Department> {

    /**
     *
     */
    @TableId
    private String id;
    /**
     *
     */
    @TableField("create_by")
    private String createBy;
    /**
     *
     */
    @TableField("create_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /**
     *
     */
    @TableField("del_flag")
    private Integer delFlag;
    /**
     *
     */
    @TableField("update_by")
    private String updateBy;
    /**
     *
     */
    @TableField("update_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    /**
     *
     */
    @TableField("parent_id")
    private String parentId;
    /**
     *
     */
    @TableField("sort_order")
    private BigDecimal sortOrder;
    /**
     *
     */
    private Integer status;
    /**
     *
     */
    private String title;
    /**
     * 是否为父节点(含子节点) 默认false
     */
    @TableField("is_parent")
    private Boolean isParent;

    /**
     * 父节点名称
     */
    @TableField(exist = false)
    private String parentTitle;

    /**
     * 主键值
     */
    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
