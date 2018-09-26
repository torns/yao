package com.y3tu.cloud.upms.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.y3tu.tool.web.base.controller.BaseController;
import com.y3tu.cloud.upms.service.RolesResourcesRelationService;
import com.y3tu.cloud.upms.entity.RolesResourcesRelation;


/**
 * <p>
 * 角色和资源关系表 前端控制器
 * </p>
 *
 * @author y3tu
 * @date 2018-08-24
 */
@RestController
@RequestMapping("/upms/roles_resources_relation")
public class RolesResourcesRelationController extends BaseController<RolesResourcesRelationService, RolesResourcesRelation> {

}
