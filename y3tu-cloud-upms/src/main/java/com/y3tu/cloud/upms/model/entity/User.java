package com.y3tu.cloud.upms.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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
 * 用户表
 * </p>
 *
 * @author y3tu
 * @date 2018-08-05
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_user")
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type = IdType.INPUT)
    protected String id;
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
     * 地址
     */
    private String address;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 简介
     */
    private String description;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 手机
     */
    private String mobile;
    /**
     * 昵称
     */
    @TableField("nick_name")
    private String nickName;
    /**
     * 密码
     */
    private String password;
    /**
     * 性别
     */
    private Integer sex;
    /**
     * 状态 0：正常 1 锁定
     */
    private Integer status;
    /**
     * 类型
     */
    private Integer type;
    /**
     * 用户名
     */
    private String username;
    /**
     * 是否删除 0:否;1:是
     */
    @TableField("del_flag")
    private Integer delFlag;

    /**
     * 部门Id
     */
    @TableField("department_id")
    private String departmentId;

    /**
     * 部门名称
     */
    @TableField(exist = false)
    private String departmentName;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
