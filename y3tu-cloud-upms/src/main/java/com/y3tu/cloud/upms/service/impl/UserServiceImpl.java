package com.y3tu.cloud.upms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.y3tu.cloud.common.constants.CommonConstants;
import com.y3tu.cloud.common.enums.UserStatusEnum;
import com.y3tu.cloud.common.vo.UserVO;
import com.y3tu.cloud.upms.mapper.UserMapper;
import com.y3tu.cloud.upms.model.dto.UserDTO;
import com.y3tu.cloud.upms.model.entity.Permission;
import com.y3tu.cloud.upms.model.entity.Role;
import com.y3tu.cloud.upms.model.entity.User;
import com.y3tu.cloud.upms.service.PermissionService;
import com.y3tu.cloud.upms.service.RoleService;
import com.y3tu.cloud.upms.service.UserRoleService;
import com.y3tu.cloud.upms.service.UserService;
import com.y3tu.tool.core.bean.BeanUtil;
import com.y3tu.tool.core.collection.CollectionUtil;
import com.y3tu.tool.web.base.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author y3tu
 * @since 2018-08-05
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    RoleService roleService;
    @Autowired
    PermissionService permissionService;
    @Autowired
    UserRoleService userRoleService;
    @Autowired
    UserMapper userMapper;

    @Override
    public UserDTO findByUsernameAndStatus(String username) {

        List<User> list = this.list(new QueryWrapper<User>().eq("username", username).eq("status", UserStatusEnum.NORMAL.getCode()));

        if (list != null && list.size() > 0) {
            User user = list.get(0);
            List<Role> roleList = userRoleService.findByUserId(user.getId());
            UserDTO userDTO = new UserDTO();
            BeanUtil.copyProperties(user, userDTO);
            userDTO.setRoles(roleList);
            List<Permission> permissionList = permissionService.findByUserId(user.getId());
            userDTO.setPermissions(permissionList);
            return userDTO;
        }
        return null;
    }

    @Override
    public User findByMobile(String mobile) {
        List<User> list = this.list(new QueryWrapper<User>().eq("mobile", mobile));
        if (CollectionUtil.isNotEmpty(list)) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public User findByEmail(String email) {
        List<User> list = this.list(new QueryWrapper<User>().eq("email", email));
        if (CollectionUtil.isNotEmpty(list)) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public List<UserDTO> selectAll() {
        List<User> list = this.baseMapper.selectAll();
        List<UserDTO> userDTOList = new ArrayList<>();
        list.forEach(user -> {
            UserDTO userDTO = new UserDTO();
            BeanUtil.copyProperties(user, userDTO);
            userDTOList.add(userDTO);
        });
        return userDTOList;
    }

    @Override
    public UserVO findUserByUsername(String username) {
        return userMapper.selectUserVoByUsername(username);
    }

    /**
     * 通过手机号查询用户信息
     *
     * @param mobile 手机号
     * @return 用户信息
     */
    @Override
    public UserVO findUserByMobile(String mobile) {
        return userMapper.selectUserVoByMobile(mobile);
    }

    /**
     * 通过openId查询用户
     *
     * @param openId openId
     * @return 用户信息
     */
    @Override
    public UserVO findUserByOpenId(String openId) {
        return userMapper.selectUserVoByOpenId(openId);
    }


}
