package com.y3tu.yao.upms.model.dto;

import com.y3tu.yao.upms.model.entity.Resource;
import com.y3tu.yao.upms.model.entity.Role;
import com.y3tu.yao.upms.model.entity.User;
import lombok.Data;

import java.util.List;
import java.util.Set;

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
    private Set<Resource> resources;
}
