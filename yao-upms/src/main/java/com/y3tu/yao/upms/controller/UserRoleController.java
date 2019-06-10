package com.y3tu.yao.upms.controller;

import com.y3tu.yao.upms.model.entity.UserRole;
import com.y3tu.yao.upms.service.UserRoleService;
import com.y3tu.tool.web.base.controller.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * <p>
 * 用户角色关联表 前端控制器
 * </p>
 *
 * @author y3tu
 * @date  2018-08-07
 */
@RestController
@RequestMapping("/sys/user_role")
public class UserRoleController extends BaseController<UserRoleService,UserRole> {

}
