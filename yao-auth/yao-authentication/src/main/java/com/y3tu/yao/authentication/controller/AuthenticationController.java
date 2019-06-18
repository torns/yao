package com.y3tu.yao.authentication.controller;

import com.y3tu.yao.authentication.feign.UserService;
import com.y3tu.yao.authentication.service.AuthenticationService;
import com.y3tu.yao.common.constants.ServerNameConstants;
import com.y3tu.yao.common.vo.UserVO;
import com.y3tu.tool.core.exception.ErrorEnum;
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

    /**
     * 判断用户是否有访问此url的权限 内部调用 直接返回true or false
     *
     * @param url
     * @param method
     * @param request
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/hasPermission")
    public boolean hasPermission(@RequestParam String url, @RequestParam String method, HttpServletRequest request) {
        boolean hasPermission = authenticationService.decide(new HttpServletRequestAuthWrapper(request, url, method));
        return hasPermission;
    }

    @GetMapping("/user")
    public R user() {
        //获取用户认证信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() != null) {
            try {
                UserVO userVO = userService.loadUserByUsername(String.valueOf(authentication.getPrincipal()));
                return R.success(userVO);
            } catch (Exception e) {
                return R.error("服务[" + ServerNameConstants.BACK_SERVER + "]调用异常！", ErrorEnum.SERVICE_CALL_ERROR);
            }
        }
        return R.error();
    }


}