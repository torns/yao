package com.y3tu.cloud.upms.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.y3tu.tool.web.base.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 权限表
 * </p>
 *
 * @author y3tu
 * @date  2018-08-05
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("permission")
public class Permission extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
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
	@TableField("sort_order")
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
	@TableField("button_type")
	private String buttonType;
    /**
     * 状态
     */
	private Integer status;


	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
