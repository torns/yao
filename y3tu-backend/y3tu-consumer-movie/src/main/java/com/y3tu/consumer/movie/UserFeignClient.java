package com.y3tu.consumer.movie;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author y3tu
 * @date 2018/5/7
 */
@FeignClient(name="y3tu-provider-user")
public interface UserFeignClient {

    @RequestMapping(value = "/user/{id}",method = RequestMethod.GET)
    public User findById(@PathVariable("id") Long id);

}
