package com.y3tu.cloud.upms.service.impl;


import com.y3tu.cloud.upms.dao.PermissionDao;
import com.y3tu.cloud.upms.entity.Permission;
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
public class PermissionServiceImpl extends BaseServiceImpl<PermissionDao, Permission> implements PermissionService {

    @Override
    public List<Permission> findByUserId(String id) {
        return baseMapper.findByUserId(id);
    }
}
