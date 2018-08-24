package com.y3tu.cloud.upms.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.y3tu.tool.web.base.controller.BaseController;
import com.y3tu.cloud.upms.service.PositionsService;
import com.y3tu.cloud.upms.entity.Positions;




/**
 * <p>
 * 岗位表 前端控制器
 * </p>
 *
 * @author y3tu
 * @date  2018-08-24
 */
@RestController
@RequestMapping("/upms/positions")
public class PositionsController extends BaseController<PositionsService,Positions> {

}
