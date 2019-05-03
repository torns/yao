package com.y3tu.cloud.back.controller;

import com.y3tu.cloud.back.model.dto.SysRoleDTO;
import com.y3tu.cloud.back.model.entity.SysRole;
import com.y3tu.cloud.back.model.query.SysRoleQuery;
import com.y3tu.cloud.back.service.SysRoleService;
import com.y3tu.cloud.common.annotation.SysLog;
import com.y3tu.cloud.common.constants.ServiceNameConstants;
import com.y3tu.tool.core.pojo.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/role")
@Api(value = "角色controller", tags = {"角色操作接口"})
public class SysRoleController {

    private static final String MODULE_NAME = "系统角色模块";


    @Autowired
    private SysRoleService sysRoleService;

    @SysLog(serviceId = ServiceNameConstants.USER_SERVICE, moduleName = MODULE_NAME, actionName = "添加角色")
    @ApiOperation(value = "添加角色", notes = "角色信息", httpMethod = "POST")
    @ApiImplicitParam(name = "sysRoleDTO", value = "角色信息", required = true, dataType = "SysRoleDTO")
    @PostMapping
    public R<Boolean> save(@RequestBody SysRoleDTO sysRoleDTO) {
        return new R<>(sysRoleService.save(sysRoleDTO));
    }

    @SysLog(serviceId = ServiceNameConstants.USER_SERVICE, moduleName = MODULE_NAME, actionName = "修改角色")
    @ApiOperation(value = "修改角色", notes = "角色信息", httpMethod = "PUT")
    @ApiImplicitParam(name = "sysRoleDTO", value = "角色信息", required = true, dataType = "SysRoleDTO")
    @PutMapping
    public R<Boolean> update(@RequestBody SysRoleDTO sysRoleDTO) {
        return new R<>(sysRoleService.updateById(sysRoleDTO));
    }

    @SysLog(serviceId = ServiceNameConstants.USER_SERVICE, moduleName = MODULE_NAME, actionName = "删除角色")
    @ApiOperation(value = "删除角色", notes = "删除角色信息", httpMethod = "DELETE")
    @ApiImplicitParam(name = "id", value = "角色id", required = true, dataType = "integer")
    @DeleteMapping("/{id}")
    public R<Boolean> delete(@PathVariable("id") Integer id) {
        return new R<>(sysRoleService.deleteById(id));
    }

    @SysLog(serviceId = ServiceNameConstants.USER_SERVICE, moduleName = MODULE_NAME, actionName = "查询角色信息以及相关联的资源信息")
    @ApiOperation(value = "查询角色信息", notes = "查询角色信息以及相关联的资源信息", httpMethod = "GET")
    @ApiImplicitParam(name = "id", value = "角色id", required = true, dataType = "integer")
    @GetMapping("/{id}")
    public R<SysRoleDTO> getSysRoleInfo(@PathVariable("id") Integer id) {
        return new R<>(sysRoleService.getRoleInfoWithResourceById(id));
    }

    @SysLog(serviceId = ServiceNameConstants.USER_SERVICE, moduleName = MODULE_NAME, actionName = "角色信息分页查询")
    @ApiOperation(value = "角色信息分页查询", notes = "角色信息分页查询", httpMethod = "GET")
    @ApiImplicitParam(name = "sysRoleQuery", value = "角色信息查询类", required = false, dataType = "SysRoleQuery")
    @GetMapping("/page")
    public R<SysRoleQuery> pageByQuery(SysRoleQuery sysRoleQuery) {
        return new R<>(sysRoleService.pageByQuery(sysRoleQuery));
    }

    @SysLog(serviceId = ServiceNameConstants.USER_SERVICE, moduleName = MODULE_NAME, actionName = "查询所有角色信息")
    @ApiOperation(value = "查询所有角色信息", notes = "查询角色信息", httpMethod = "GET")
    @GetMapping
    public R<List<SysRole>> listRole() {
        return new R<>(sysRoleService.listSysRole());
    }
}
