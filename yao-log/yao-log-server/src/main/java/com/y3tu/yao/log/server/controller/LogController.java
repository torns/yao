package com.y3tu.yao.log.server.controller;

import com.y3tu.yao.common.constants.ServerNameConstants;
import com.y3tu.yao.log.server.model.entity.Log;
import com.y3tu.yao.log.server.service.LogService;
import com.y3tu.tool.core.pojo.R;
import com.y3tu.tool.web.base.controller.BaseController;
import com.y3tu.tool.web.base.pojo.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 日志Controller
 *
 * @author y3tu
 */
@RestController
@RequestMapping("/log")
@Api(value = "日志controller", tags = {"系统日志操作接口"})
public class LogController extends BaseController<LogService, Log> {

    private static final String MODULE_NAME = "系统日志模块";

    @Autowired
    private LogService logService;

    @com.y3tu.yao.log.starter.annotation.Log(serviceId = ServerNameConstants.LOG_SERVER, moduleName = MODULE_NAME, actionName = "日志信息分页查询")
    @ApiOperation(value = "日志信息分页查询", notes = "日志信息分页查询", httpMethod = "GET")
    @ApiImplicitParam(name = "pageInfo", value = "日志信息查询类", required = false, dataType = "PageInfo")
    @PostMapping("/page")
    @Override
    public R page(@RequestBody PageInfo pageInfo) {
        return R.success(logService.page(pageInfo));
    }

}