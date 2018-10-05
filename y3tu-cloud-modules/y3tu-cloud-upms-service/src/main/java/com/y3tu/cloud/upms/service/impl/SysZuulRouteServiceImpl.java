package com.y3tu.cloud.upms.service.impl;


import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.y3tu.cloud.common.constant.CommonConstant;
import com.y3tu.cloud.upms.dao.SysZuulRouteMapper;
import com.y3tu.cloud.upms.model.entity.SysZuulRoute;
import com.y3tu.cloud.upms.service.SysZuulRouteService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.y3tu.tool.core.util.JsonUtil;
import com.y3tu.tool.web.redis.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 动态路由配置表 服务实现类
 *
 * @author liuht
 * @since 2018-05-15
 */
@Service
public class SysZuulRouteServiceImpl extends ServiceImpl<SysZuulRouteMapper, SysZuulRoute> implements SysZuulRouteService {
    @Autowired
    private RedisUtils redisUtils;

    /**
     * 同步路由配置信息,到服务网关
     *
     * @return 同步成功
     */
    @Override
    public Boolean applyZuulRoute() {
        EntityWrapper<SysZuulRoute> wrapper = new EntityWrapper<>();
        wrapper.eq(CommonConstant.DEL_FLAG, CommonConstant.STATUS_NORMAL);
        List<SysZuulRoute> routeList = selectList(wrapper);
        redisUtils.set(CommonConstant.ROUTE_KEY, JsonUtil.toJson(routeList));
        return Boolean.TRUE;
    }
}
