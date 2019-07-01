package com.y3tu.yao.upms.controller;

import com.y3tu.yao.common.constants.CommonConstants;
import com.y3tu.yao.upms.model.entity.ZuulRoute;
import com.y3tu.yao.upms.service.ZuulRouteService;
import com.y3tu.tool.core.pojo.R;
import com.y3tu.tool.web.base.controller.BaseController;
import com.y3tu.tool.web.base.pojo.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 动态路由配置表 前端控制器
 * </p>
 *
 * @author y3tu
 */
@RestController
@RequestMapping("/route")
public class ZuulRouteController extends BaseController<ZuulRouteService, ZuulRoute> {
    @Autowired
    private ZuulRouteService zuulRouteService;

    /**
     * 通过ID查询
     *
     * @param id ID
     * @return ZuulRoute
     */
    @GetMapping("/{id}")
    public ZuulRoute get(@PathVariable Integer id) {
        return zuulRouteService.getById(id);
    }

    /**
     * 分页查询信息
     *
     * @param pageInfo 分页对象
     * @return 分页对象
     */
    @GetMapping("/page")
    @Override
    public R page(@RequestBody PageInfo pageInfo) {
        Map param = pageInfo.getParams();
        param.putAll((Map) new HashMap().put(CommonConstants.DEL_FLAG, CommonConstants.STATUS_NORMAL));
        pageInfo.setParams(param);
        return R.success(zuulRouteService.page(pageInfo));
    }

    /**
     * 添加
     *
     * @param zuulRoute 实体
     * @return success/false
     */
    @PostMapping
    public R add(@RequestBody ZuulRoute zuulRoute) {
        return R.success(zuulRouteService.save(zuulRoute));
    }

    /**
     * 删除
     *
     * @param id ID
     * @return success/false
     */
    @DeleteMapping("/{id}")
    public R delete(@PathVariable Integer id) {
        ZuulRoute zuulRoute = new ZuulRoute();
        zuulRoute.setId(id);
        zuulRoute.setUpdateTime(new Date());
        zuulRoute.setDelFlag(CommonConstants.STATUS_DEL);
        return R.success(zuulRouteService.updateById(zuulRoute));
    }

    /**
     * 编辑
     *
     * @param zuulRoute 实体
     * @return success/false
     */
    @PutMapping
    public R edit(@RequestBody ZuulRoute zuulRoute) {
        zuulRoute.setUpdateTime(new Date());
        return R.success(zuulRouteService.updateById(zuulRoute));
    }

    /**
     * 刷新配置
     *
     * @return success/fasle
     */
    @GetMapping("/apply")
    public R apply() {
        return R.success(zuulRouteService.applyZuulRoute());
    }
}
