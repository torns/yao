package com.y3tu.cloud.upms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.y3tu.cloud.upms.mapper.PermissionMapper;
import com.y3tu.cloud.upms.model.entity.Permission;
import com.y3tu.cloud.upms.service.PermissionService;
import com.y3tu.tool.web.base.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 权限表 服务实现类
 * </p>
 *
 * @author y3tu
 * @since 2018-08-05
 */
@Service
public class PermissionServiceImpl extends BaseServiceImpl<PermissionMapper, Permission> implements PermissionService {

    @Override
    public List<Permission> findByUserId(String id) {
        return baseMapper.findByUserId(id);
    }

    @Override
    public List<Permission> findByTypeAndStatusOrderBySortOrder(Integer type, Integer status) {
        List<Permission> permissions = baseMapper.selectList(
                new QueryWrapper<Permission>().eq("type", type).eq("status", status).orderByAsc("sort_order"));
        return permissions;
    }
}
