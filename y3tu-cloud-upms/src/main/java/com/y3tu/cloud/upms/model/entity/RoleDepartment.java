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
 * ${comments}
 *
 * @author y3tu
 * @date 2018-11-27 19:16:18
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_role_department")
public class RoleDepartment extends BaseEntity<RoleDepartment> {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId(value = "id")
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
    @TableField("department_id")
    private String departmentId;
    /**
     *
     */
    @TableField("role_id")
    private String roleId;

    /**
     * 主键值
     */
    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
