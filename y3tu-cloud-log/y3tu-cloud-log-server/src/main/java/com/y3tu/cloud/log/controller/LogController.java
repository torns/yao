package com.y3tu.cloud.log.controller;

import com.y3tu.cloud.common.constants.ServiceNameConstants;
import com.y3tu.cloud.log.annotation.Log;
import com.y3tu.cloud.log.model.query.LogQuery;
import com.y3tu.cloud.log.service.LogService;
import com.y3tu.tool.core.pojo.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/log")
@Api(value = "日志controller", tags = {"系统日志操作接口"})
public class LogController {

    private static final String MODULE_NAME = "系统日志模块";

    @Autowired
    private LogService logService;

    @Log(serviceId = ServiceNameConstants.LOG_SERVER, moduleName = MODULE_NAME, actionName = "日志信息分页查询")
    @ApiOperation(value = "日志信息分页查询", notes = "日志信息分页查询", httpMethod = "GET")
    @ApiImplicitParam(name = "logQuery", value = "日志信息查询类", required = false, dataType = "LogQuery")
    @GetMapping("/page")
    public R<LogQuery> pageByQuery(LogQuery logQuery) {
        return new R<>(logService.pageByQuery(logQuery));
    }

}