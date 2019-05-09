package com.y3tu.cloud.auth.authentication.controller;

import com.y3tu.cloud.auth.authentication.service.AuthenticationService;
import com.y3tu.tool.core.pojo.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@Slf4j
public class AuthenticationController {
    @Autowired
    AuthenticationService authenticationService;

    @RequestMapping(method = RequestMethod.POST, value = "/auth/decide")
    public boolean decide(@RequestParam String url, @RequestParam String method, HttpServletRequest request) {
        boolean decide = authenticationService.decide(new HttpServletRequestAuthWrapper(request, url, method));
        return decide;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/auth/hasPermission")
    public boolean hasPermission(HttpServletRequest request) {
        boolean hasPermission = authenticationService.hasPermission(request);
        return hasPermission;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/auth/ignoreAuthentication")
    public boolean ignoreAuthentication(@RequestParam String url) {
        boolean ignore = authenticationService.ignoreAuthentication(url);
        return ignore;
    }


}