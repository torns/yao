package com.y3tu.cloud.upms.service.impl;

import com.y3tu.cloud.upms.entity.Roles;
import com.y3tu.cloud.upms.dao.RolesDao;
import com.y3tu.cloud.upms.service.RolesService;
import com.y3tu.tool.web.base.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author y3tu
 * @since 2018-08-24
 */
@Service
public class RolesServiceImpl extends BaseServiceImpl<RolesDao, Roles> implements RolesService {

    @Override
    public List<Roles> findByUserId(long userId) {
        return baseMapper.findByUserId(userId);
    }
}
