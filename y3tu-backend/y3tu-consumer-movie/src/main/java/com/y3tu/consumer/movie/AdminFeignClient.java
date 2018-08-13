package com.y3tu.consumer.movie;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author y3tu
 * @date 2018/8/13
 */
@FeignClient("y3tu-admin")
public interface AdminFeignClient {

    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    @RequestMapping(value = "/admin/login", method = RequestMethod.POST)
    public Object login(@RequestParam("username") String username, @RequestParam("password") String password);

    @RequestMapping(value = "/admin/sys/log/getAll", method = RequestMethod.GET)
    String getAllLog(@RequestHeader("accessToken")String accessToken);
}
