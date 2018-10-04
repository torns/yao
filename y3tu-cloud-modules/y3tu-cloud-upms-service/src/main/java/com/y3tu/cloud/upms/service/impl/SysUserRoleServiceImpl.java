package com.y3tu.cloud.upms.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.y3tu.cloud.upms.dao.SysUserRoleMapper;
import com.y3tu.cloud.upms.model.entity.SysUserRole;
import com.y3tu.cloud.upms.service.SysUserRoleService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户角色表 服务实现类
 * </p>
 *
 * @author liuht
 * @since 2017-10-29
 */
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements SysUserRoleService {

    /**
     * 根据用户Id删除该用户的角色关系
     *
     * @param userId 用户ID
     * @return boolean
     * @author 寻欢·李
     * @date 2017年12月7日 16:31:38
     */
    @Override
    public Boolean deleteByUserId(Integer userId) {
        return baseMapper.deleteByUserId(userId);
    }
}
