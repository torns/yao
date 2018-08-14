package com.y3tu.cloud.upms.service.impl;


import com.baomidou.mybatisplus.annotations.DataSource;
import com.y3tu.cloud.upms.dao.RoleDao;
import com.y3tu.cloud.upms.entity.Role;
import com.y3tu.cloud.upms.service.RoleService;
import com.y3tu.tool.web.base.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author y3tu
 * @since 2018-08-05
 */
@Service
public class RoleServiceImpl extends BaseServiceImpl<RoleDao, Role> implements RoleService {

    @Override
    public boolean insertTestTwo(Role entity) throws Exception {
        this.insert(entity);
        return true;
    }

    @Override
    public boolean insertTestOne(Role entity)throws Exception {
        return this.insert(entity);
    }
}
