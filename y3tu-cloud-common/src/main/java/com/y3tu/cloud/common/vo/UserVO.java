package com.y3tu.cloud.common.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Set;


@Data
public class UserVO {

    /**
     * 主键
     */
    private String id;
    /**
     * 创建人
     */
    private String createBy;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新人
     */
    private String updateBy;
    /**
     * 更新时间
     */
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
     * 状态
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
    private Integer delFlag;

    /**
     * 部门Id
     */
    private String departmentId;

    /**
     * 部门名称
     */
    private String departmentName;

    /**
     * 角色列表
     */
    private List<RoleVO> roles;

    /**
     * 资源权限列表
     */
    private Set<ResourceVO> resources;

}
