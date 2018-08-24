package com.y3tu.cloud.common.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author y3tu
 * @date  2018-08-24
 */
@Data
public class UsersVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
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
	private Integer accountNonExpired;
    /**
     * 密码是否未过期
     */
	private Integer credentialsNonExpired;
    /**
     * 账号是否锁定
     */
	private Integer accountNonLocked;
    /**
     * 创建时间
     */
	private Date createdTime;
    /**
     * 更新时间
     */
	private Date updatedTime;
    /**
     * 创建人
     */
	private String createdBy;
    /**
     * 更新人
     */
	private String updatedBy;

	private List<RolesVO> rolesList;
	private List<MenusVO> menusList;
	private List<ResourcesVO> resourcesList;


}
