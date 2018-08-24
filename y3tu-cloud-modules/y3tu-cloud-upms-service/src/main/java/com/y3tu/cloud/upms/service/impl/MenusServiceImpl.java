package com.y3tu.cloud.upms.service.impl;

import com.y3tu.cloud.upms.entity.Menus;
import com.y3tu.cloud.upms.dao.MenusDao;
import com.y3tu.cloud.upms.service.MenusService;
import com.y3tu.tool.web.base.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 菜单表 服务实现类
 * </p>
 *
 * @author y3tu
 * @since 2018-08-24
 */
@Service
public class MenusServiceImpl extends BaseServiceImpl<MenusDao, Menus> implements MenusService {

    @Override
    public List<Menus> findByUserId(long userId) {
        return baseMapper.findByUserId(userId);
    }
}
