package com.y3tu.cloud.gen.controller;

import com.y3tu.cloud.common.constants.ServiceNameConstants;
import com.y3tu.cloud.gen.model.dto.BuildConfigDTO;
import com.y3tu.cloud.gen.model.query.TableInfoQuery;
import com.y3tu.cloud.gen.service.SysGenService;
import com.y3tu.cloud.gen.service.TableInfoService;
import com.y3tu.cloud.log.annotation.Log;
import com.y3tu.tool.core.io.IoUtil;
import com.y3tu.tool.core.pojo.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Controller
@Api(value = "代码生成controller", tags = {"代码生成接口管理"})
public class SysGenController {

    private static final String MODULE_NAME = "代码生成模块";


    @Autowired
    private TableInfoService tableInfoService;

    @Autowired
    private SysGenService sysGenService;

    @Log(serviceId = ServiceNameConstants.GEN_SERVER, moduleName = MODULE_NAME, actionName = "分页查询数据库中所有的表信息")
    @ApiOperation(value = "分页查询数据库中所有的表信息", notes = "分页查询数据库中所有的表信息", httpMethod = "GET")
    @ApiImplicitParam(name = "query", value = "表信息查询条件", required = false, dataType = "TableInfoQuery")
    @ResponseBody
    @GetMapping("/code/page")
    public R<TableInfoQuery> page(TableInfoQuery query) {
        return new R<>(tableInfoService.pageByQuery(query));
    }


    @Log(serviceId = ServiceNameConstants.GEN_SERVER, moduleName = MODULE_NAME, actionName = "根据表名称生成代码  返回zip包")
    @ApiOperation(value = "根据表名称生成代码", notes = "根据表名称生成代码  返回zip包", httpMethod = "POST")
    @ApiImplicitParam(name = "buildConfigDTO", value = "表配置", required = true, dataType = "BuildConfigDTO")
    @PostMapping("/code/build")
    public void code(@RequestBody BuildConfigDTO buildConfigDTO, HttpServletResponse response) throws IOException {

        byte[] data = sysGenService.genCodeByTableName(buildConfigDTO);
        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=\"code.zip\"");
        response.addHeader("Content-Length", "" + data.length);
        response.setContentType("application/octet-stream; charset=UTF-8");
        IoUtil.write(response.getOutputStream(), true, data);
    }

}
