package com.y3tu.cloud.upms.controller;

import com.y3tu.cloud.common.constant.CommonConstant;
import com.y3tu.cloud.upms.model.entity.SysZuulRoute;
import com.y3tu.tool.web.base.controller.BaseController;
import com.y3tu.cloud.upms.service.SysZuulRouteService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.y3tu.tool.web.base.pojo.Query;
import com.y3tu.tool.web.base.pojo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

/**
 * <p>
 * 动态路由配置表 前端控制器
 * </p>
 *
 * @author liuht
 * @since 2018-05-15
 */
@RestController
@RequestMapping("/route")
public class ZuulRouteController extends BaseController {
    @Autowired
    private SysZuulRouteService sysZuulRouteService;

    /**
     * 通过ID查询
     *
     * @param id ID
     * @return SysZuulRoute
     */
    @GetMapping("/{id}")
    public SysZuulRoute get(@PathVariable Integer id) {
        return sysZuulRouteService.selectById(id);
    }

    /**
     * 分页查询信息
     *
     * @param params 分页对象
     * @return 分页对象
     */
    @GetMapping("/page")
    public Page page(@RequestParam Map<String, Object> params) {
        params.put(CommonConstant.DEL_FLAG, CommonConstant.STATUS_NORMAL);
        return sysZuulRouteService.selectPage(new Query(params).getPage(), new EntityWrapper<>());
    }

    /**
     * 添加
     *
     * @param sysZuulRoute 实体
     * @return success/false
     */
    @PostMapping
    public R add(@RequestBody SysZuulRoute sysZuulRoute) {
        return R.ok(sysZuulRouteService.insert(sysZuulRoute));
    }

    /**
     * 删除
     *
     * @param id ID
     * @return success/false
     */
    @DeleteMapping("/{id}")
    public R delete(@PathVariable Integer id) {
        SysZuulRoute sysZuulRoute = new SysZuulRoute();
        sysZuulRoute.setId(id);
        sysZuulRoute.setUpdateTime(new Date());
        sysZuulRoute.setDelFlag(CommonConstant.STATUS_DEL);
        return R.ok(sysZuulRouteService.updateById(sysZuulRoute));
    }

    /**
     * 编辑
     *
     * @param sysZuulRoute 实体
     * @return success/false
     */
    @PutMapping
    public R edit(@RequestBody SysZuulRoute sysZuulRoute) {
        sysZuulRoute.setUpdateTime(new Date());
        return R.ok(sysZuulRouteService.updateById(sysZuulRoute));
    }

    /**
     * 刷新配置
     *
     * @return success/fasle
     */
    @GetMapping("/apply")
    public R apply() {
        return R.ok(sysZuulRouteService.applyZuulRoute());
    }
}