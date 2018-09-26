package com.y3tu.cloud.upms.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;

import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import com.y3tu.tool.web.base.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author y3tu
 * @date 2018-08-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("users")
public class Users extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 用户名
     */
    private String username;
    /**
     * 用户密码密文
     */
    private String password;
    /**
     * 用户姓名
     */
    private String name;
    /**
     * 用户手机
     */
    private String mobile;
    /**
     * 是否有效用户
     */
    private Integer enabled;
    /**
     * 账号是否未过期
     */
    @TableField("account_non_expired")
    private Integer accountNonExpired;
    /**
     * 密码是否未过期
     */
    @TableField("credentials_non_expired")
    private Integer credentialsNonExpired;
    /**
     * 账号是否锁定
     */
    @TableField("account_non_locked")
    private Integer accountNonLocked;
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

    @TableField(exist = false)
    private List<Roles> rolesList;
    @TableField(exist = false)
    private List<Menus> menusList;
    @TableField(exist = false)
    private List<Resources> resourcesList;
    @TableField(exist = false)
    private List<Positions> positionsList;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
