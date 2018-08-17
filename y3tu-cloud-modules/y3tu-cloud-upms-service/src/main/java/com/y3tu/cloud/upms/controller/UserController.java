package com.y3tu.cloud.upms.controller;


import com.y3tu.cloud.upms.entity.User;
import com.y3tu.cloud.upms.service.UserService;
import com.y3tu.tool.web.base.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author y3tu
 * @date  2018-08-05
 */
@RestController
@RequestMapping("/upms/user")
public class UserController extends BaseController<UserService, User> {

    @Autowired
    UserService userService;

    @GetMapping("/findUserByUsername")
    public Object findUserByUsername(@RequestParam String username){
        return userService.findUserByUsername(username);
    }

    @GetMapping("/findUserByMobile")
    public Object findUserByMobile(@RequestParam String mobile){
        return userService.findUserByMobile(mobile);
    }
}
