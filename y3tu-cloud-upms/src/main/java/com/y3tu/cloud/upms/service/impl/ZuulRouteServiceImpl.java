package com.y3tu.cloud.upms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.y3tu.cloud.common.constants.CommonConstants;
import com.y3tu.cloud.upms.mapper.ZuulRouteMapper;
import com.y3tu.cloud.upms.model.entity.ZuulRoute;
import com.y3tu.cloud.upms.service.ZuulRouteService;
import com.y3tu.tool.core.util.JsonUtil;
import com.y3tu.tool.web.base.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 动态路由配置表 服务实现类
 *
 * @author liuht
 * @since 2018-05-15
 */
@Service
public class ZuulRouteServiceImpl extends BaseServiceImpl<ZuulRouteMapper, ZuulRoute> implements ZuulRouteService {
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 同步路由配置信息,到服务网关
     *
     * @return 同步成功
     */
    @Override
    public Boolean applyZuulRoute() {
        QueryWrapper<ZuulRoute> wrapper = new QueryWrapper<>();
        wrapper.eq(CommonConstants.DEL_FLAG, CommonConstants.STATUS_NORMAL);
        List<ZuulRoute> routeList = this.list(wrapper);
        redisTemplate.opsForValue().set(CommonConstants.ROUTE_KEY, JsonUtil.toJson(routeList));
        return Boolean.TRUE;
    }
}
