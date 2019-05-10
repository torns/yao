package com.y3tu.cloud.auth.authentication.controller;

import com.y3tu.cloud.auth.authentication.feign.UserService;
import com.y3tu.cloud.auth.authentication.service.AuthenticationService;
import com.y3tu.cloud.common.exception.AuthExceptionEnum;
import com.y3tu.cloud.common.vo.UserVO;
import com.y3tu.tool.core.pojo.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@Slf4j
public class AuthenticationController {
    @Autowired
    AuthenticationService authenticationService;
    @Autowired
    UserService userService;
    @Autowired
    HttpServletRequest request;

    @RequestMapping(method = RequestMethod.POST, value = "/hasPermission")
    public R hasPermission(@RequestParam String url, @RequestParam String method, HttpServletRequest request) {
        boolean hasPermission = authenticationService.decide(new HttpServletRequestAuthWrapper(request, url, method));
        return R.success(hasPermission);
    }

    @GetMapping("/user")
    public R user() {
        //获取用户认证信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() != null) {
            UserVO userVO = userService.loadUserByUsername(String.valueOf(authentication.getPrincipal()));
            return R.success(userVO);
        }
        return R.error(AuthExceptionEnum.ACCESS_DENIED);
    }


}