package com.y3tu.yao.upms.service;

import com.y3tu.yao.upms.model.entity.ZuulRoute;
import com.y3tu.tool.web.base.service.BaseService;

/**
 * <p>
 * 动态路由配置表 服务类
 * </p>
 *
 * @author y3tu
 */
public interface ZuulRouteService extends BaseService<ZuulRoute> {

    /**
     * 立即生效配置
     *
     * @return
     */
    Boolean applyZuulRoute();
}
