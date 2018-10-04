package com.y3tu.cloud.upms.controller;


import com.y3tu.cloud.common.constant.CommonConstant;
import com.y3tu.cloud.upms.model.entity.SysLog;
import com.y3tu.tool.core.exception.ServerException;
import com.y3tu.tool.web.base.controller.BaseController;
import com.y3tu.cloud.upms.service.SysLogService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.y3tu.tool.web.base.pojo.Query;
import com.y3tu.tool.web.base.pojo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

/**
 * <p>
 * 日志表 前端控制器
 * </p>
 *
 * @author liuht
 * @since 2017-11-20
 */
@RestController
@RequestMapping("/log")
public class LogController extends BaseController {
    @Autowired
    private SysLogService sysLogService;

    /**
     * 分页查询日志信息
     *
     * @param params 分页对象
     * @return 分页对象
     */
    @GetMapping("/logPage")
    public Page logPage(@RequestParam Map<String, Object> params) {
        params.put(CommonConstant.DEL_FLAG, CommonConstant.STATUS_NORMAL);
        return sysLogService.selectPage(new Query(params).getPage(), new EntityWrapper<>());
    }

    /**
     * 根据ID
     *
     * @param id ID
     * @return success/false
     */
    @DeleteMapping("/{id}")
    public R delete(@PathVariable Long id) {
        return R.ok(sysLogService.updateByLogId(id));
    }

    /**
     * 添加日志
     *
     * @param log    日志实体
     * @param result 错误信息
     */
    @PostMapping
    public void add(@Valid @RequestBody SysLog log, BindingResult result) {
        if (result.hasErrors()) {
            throw new ServerException(result.getAllErrors().get(0).getDefaultMessage());
        }
        sysLogService.insert(log);
    }
}
