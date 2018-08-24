package com.y3tu.cloud.upms.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.y3tu.tool.web.base.controller.BaseController;
import com.y3tu.cloud.upms.service.UsersPositionsRelationService;
import com.y3tu.cloud.upms.entity.UsersPositionsRelation;




/**
 * <p>
 * 用户和岗位关系表 前端控制器
 * </p>
 *
 * @author y3tu
 * @date  2018-08-24
 */
@RestController
@RequestMapping("/upms/users_positions_relation")
public class UsersPositionsRelationController extends BaseController<UsersPositionsRelationService,UsersPositionsRelation> {

}
