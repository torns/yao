package com.y3tu.cloud.upms.controller;

import com.y3tu.cloud.upms.model.entity.RolePermission;
import com.y3tu.cloud.upms.service.RolePermissionService;
import com.y3tu.tool.web.base.controller.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * <p>
 * 角色权限关联表 前端控制器
 * </p>
 *
 * @author y3tu
 * @date  2018-08-05
 */
@RestController
@RequestMapping("/sys/role_permission")
public class RolePermissionController extends BaseController<RolePermissionService,RolePermission> {

}
