package com.y3tu.cloud.upms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.y3tu.tool.web.base.controller.BaseController;
import com.y3tu.cloud.upms.service.UsersService;
import com.y3tu.cloud.upms.entity.Users;




/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author y3tu
 * @date  2018-08-24
 */
@RestController
@RequestMapping("/upms/users")
public class UsersController extends BaseController<UsersService,Users> {
    @Autowired
    UsersService usersService;

    @GetMapping("/findUserByUsername")
    public Object findUserByUsername(@RequestParam String username){
        return usersService.findUserByUsername(username);
    }

    @GetMapping("/findUserByMobile")
    public Object findUserByMobile(@RequestParam String mobile){
        return usersService.findUserByMobile(mobile);
    }
}
