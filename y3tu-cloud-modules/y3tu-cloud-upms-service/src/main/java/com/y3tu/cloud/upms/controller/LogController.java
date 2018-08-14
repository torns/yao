package com.y3tu.cloud.upms.controller;

import com.y3tu.cloud.upms.entity.Log;
import com.y3tu.cloud.upms.service.LogService;
import com.y3tu.tool.web.base.controller.BaseController;
import com.y3tu.tool.web.lock.RedisDistributedLockTemplate;
import com.y3tu.tool.web.util.RedisUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * <p>
 * 日志 前端控制器
 * </p>
 *
 * @author y3tu
 * @date 2018-08-05
 */
@Slf4j
@RestController
@RequestMapping("/upms/log")
@Api(description = "日志")
public class LogController extends BaseController<LogService, Log> {

    @Autowired
    LogService logService;

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    @ApiOperation(value = "测试日志api")
    public void test()throws Exception {

        logService.test();

    }
}
