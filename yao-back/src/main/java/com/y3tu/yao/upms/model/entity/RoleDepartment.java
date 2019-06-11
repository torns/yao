package com.y3tu.yao.upms.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.y3tu.tool.web.base.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;


/**
 * 角色部门关系表
 *
 * @author y3tu
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_role_department")
public class RoleDepartment extends BaseEntity<RoleDepartment> {

    /**
     *
     */
    @TableId(value = "id", type = IdType.INPUT)
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

}
