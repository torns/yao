package com.y3tu.cloud.upms.controller;

import com.y3tu.cloud.common.constants.ServiceNameConstants;
import com.y3tu.cloud.log.annotation.Log;
import com.y3tu.cloud.upms.service.LogService;
import com.y3tu.tool.core.pojo.R;
import com.y3tu.tool.web.annotation.MethodMapping;
import com.y3tu.tool.web.base.controller.BaseController;
import com.y3tu.tool.web.base.pojo.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


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
@RequestMapping("/log")
@Api(description = "日志")
public class LogController extends BaseController<LogService, com.y3tu.cloud.upms.model.entity.Log> {

    private static final String MODULE_NAME = "UPMS模块";

    @Autowired
    LogService logService;

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    @ApiOperation(value = "测试日志api")
    @Log(serviceId = ServiceNameConstants.UPMS_SERVER, moduleName = MODULE_NAME, actionName = "测试")
    public void test() throws Exception {
        logService.test();
    }

    @Override
    @MethodMapping(method = RequestMethod.POST)
    public R page(@RequestBody PageInfo pageInfo) {
        return R.success(logService.page(pageInfo));
    }

    @RequestMapping(value = "/delByIds/{ids}", method = RequestMethod.DELETE)
    @ApiOperation(value = "批量删除")
    @Override
    public R delByIds(@PathVariable String[] ids) {
        for (String id : ids) {
            logService.removeById(id);
        }
        return R.success();
    }

    @RequestMapping(value = "/delAll", method = RequestMethod.DELETE)
    @ApiOperation(value = "全部删除")
    public R delAll() {
        logService.remove(null);
        return R.success();
    }
}
