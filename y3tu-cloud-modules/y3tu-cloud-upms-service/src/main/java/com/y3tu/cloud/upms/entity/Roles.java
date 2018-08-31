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
 * 角色表
 * </p>
 *
 * @author y3tu
 * @date  2018-08-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("roles")
public class Roles extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 角色id
     */
	@TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 角色编码
     */
	private String code;
    /**
     * 角色名称
     */
	private String name;
    /**
     * 角色简介
     */
	private String description;
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