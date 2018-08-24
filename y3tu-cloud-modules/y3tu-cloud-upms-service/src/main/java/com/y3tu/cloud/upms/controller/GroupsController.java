package com.y3tu.cloud.upms.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.y3tu.tool.web.base.controller.BaseController;
import com.y3tu.cloud.upms.service.GroupsService;
import com.y3tu.cloud.upms.entity.Groups;




/**
 * <p>
 * 用户组表 前端控制器
 * </p>
 *
 * @author y3tu
 * @date  2018-08-24
 */
@RestController
@RequestMapping("/upms/groups")
public class GroupsController extends BaseController<GroupsService,Groups> {

}
