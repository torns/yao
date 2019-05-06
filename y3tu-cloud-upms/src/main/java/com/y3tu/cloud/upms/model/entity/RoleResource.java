package com.y3tu.cloud.upms.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.y3tu.tool.web.base.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 角色权限关联表
 * </p>
 *
 * @author y3tu
 * @date  2018-08-05
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_role_resource")
public class RoleResource extends BaseEntity {

    private static final long serialVersionUID = 1L;

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
     * 权限ID
     */
	@TableField("permission_id")
	private String permissionId;
    /**
     * 角色ID
     */
	@TableField("role_id")
	private String roleId;


	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
