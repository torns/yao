package com.y3tu.cloud.common.vo;

import lombok.Data;

import java.util.Date;

@Data
public class RoleVO {
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
     * 角色名称
     */
    private String name;
    /**
     * 是否删除 0:否;1:是
     */
    private Integer delFlag;
    /**
     * 默认角色
     */
    private Boolean defaultRole;
}
