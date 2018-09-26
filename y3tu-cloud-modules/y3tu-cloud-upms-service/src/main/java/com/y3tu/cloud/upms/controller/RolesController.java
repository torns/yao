package com.y3tu.cloud.upms.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.y3tu.tool.web.base.controller.BaseController;
import com.y3tu.cloud.upms.service.RolesService;
import com.y3tu.cloud.upms.entity.Roles;


/**
 * <p>
 * 角色表 前端控制器
 * </p>
 *
 * @author y3tu
 * @date 2018-08-24
 */
@RestController
@RequestMapping("/upms/roles")
public class RolesController extends BaseController<RolesService, Roles> {

}
