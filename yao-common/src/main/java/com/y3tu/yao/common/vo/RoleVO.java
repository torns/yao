package com.y3tu.yao.common.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 角色表
 * </p>
 */
@Data
@Accessors(chain = true)
public class RoleVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;

    /**
     * 角色编码
     */
    private String roleCode;
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

    /**
     * 数据权限类型 0全部默认 1自定义
     */
    private Integer dataType;

    /**
     * 备注
     */
    private String description;


}
