package com.y3tu.consumer.movie;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author y3tu
 * @date 2018/5/7
 */
@RestController
@Slf4j
public class MovieController {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private LoadBalancerClient loadBalancerClient;
    @Autowired
    private UserFeignClient userFeignClient;

    @GetMapping("/user/{id}")
    public User findUserById(@PathVariable Long id){
        //return restTemplate.getForObject("http://y3tu-provider-user/user/"+id,User.class);
        return userFeignClient.findById(id);
    }

    @GetMapping("/log-instance")
    public void logUserInterface(){
        ServiceInstance serviceInstance = loadBalancerClient.choose("y3tu-provider-user");
        log.info("{}:{}:{}",serviceInstance.getServiceId(),serviceInstance.getHost(),serviceInstance.getPort());
    }
}
