package com.y3tu.cloud.upms.service;

import com.y3tu.cloud.upms.model.entity.ZuulRoute;
import com.y3tu.tool.web.base.service.BaseService;

/**
 * <p>
 * 动态路由配置表 服务类
 * </p>
 *
 * @author liuht
 * @since 2018-05-15
 */
public interface ZuulRouteService extends BaseService<ZuulRoute> {

    /**
     * 立即生效配置
     *
     * @return
     */
    Boolean applyZuulRoute();
}
