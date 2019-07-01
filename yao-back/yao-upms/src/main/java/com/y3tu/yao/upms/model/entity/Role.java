package com.y3tu.yao.upms.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.y3tu.yao.common.constants.CommonConstants;
import com.y3tu.tool.web.base.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 角色表
 * </p>
 *
 * @author y3tu
 */
@Data
@TableName("t_role")
@AllArgsConstructor
@NoArgsConstructor
public class Role extends BaseEntity {

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.INPUT)
    private String id;

    /**
     * 角色编码
     */
    @TableField("role_code")
    private String roleCode;
    /**
     * 创建人
     */
    @TableField("create_by")
    private String createBy;
    /**
     * 创建时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("create_time")
    private Date createTime;
    /**
     * 更新人
     */
    @TableField("update_by")
    private String updateBy;
    /**
     * 更新时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("update_time")
    private Date updateTime;
    /**
     * 角色名称
     */
    private String name;
    /**
     * 是否删除 0:否;1:是
     */
    @TableField("del_flag")
    private Integer delFlag;
    /**
     * 默认角色
     */
    @TableField("default_role")
    private Boolean defaultRole;

    /**
     * 数据权限类型 0全部默认 1自定义
     */
    @TableField("data_type")
    private Integer dataType = CommonConstants.DATA_TYPE_ALL;

    /**
     * 备注
     */
    private String description;

    /**
     * 菜单权限
     */
    @TableField(exist = false)
    private List<Resource> resources;

    /**
     * 数据权限
     */
    @TableField(exist = false)
    private List<Department> departments;

}
