package com.y3tu.cloud.upms.service.impl;

import com.y3tu.cloud.upms.entity.Resources;
import com.y3tu.cloud.upms.dao.ResourcesDao;
import com.y3tu.cloud.upms.service.ResourcesService;
import com.y3tu.tool.web.base.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 资源表 服务实现类
 * </p>
 *
 * @author y3tu
 * @since 2018-08-24
 */
@Service
public class ResourcesServiceImpl extends BaseServiceImpl<ResourcesDao, Resources> implements ResourcesService {

    @Override
    public List<Resources> findByUserId(long userId) {
        return baseMapper.findByUserId(userId);
    }
}
