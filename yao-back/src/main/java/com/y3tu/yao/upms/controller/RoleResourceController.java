package com.y3tu.yao.upms.controller;

import com.y3tu.yao.upms.model.entity.RoleResource;
import com.y3tu.yao.upms.service.RoleResourceService;
import com.y3tu.tool.web.base.controller.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * <p>
 * 角色权限关联表 前端控制器
 * </p>
 *
 * @author y3tu
 */
@RestController
@RequestMapping("/sys/role_resource")
public class RoleResourceController extends BaseController<RoleResourceService, RoleResource> {

}
