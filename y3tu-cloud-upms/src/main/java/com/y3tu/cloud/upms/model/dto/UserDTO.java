package com.y3tu.cloud.upms.model.dto;

import com.y3tu.cloud.upms.model.entity.Permission;
import com.y3tu.cloud.upms.model.entity.Role;
import com.y3tu.cloud.upms.model.entity.User;
import lombok.Data;

import java.util.List;

/**
 * @author y3tu
 * @date 2018-12-07
 */
@Data
public class UserDTO extends User {
    /**
     * 用户拥有角色
     */
    private List<Role> roles;

    /**
     * 用户拥有的权限
     */
    private List<Permission> permissions;
}
