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
    @Autowired
    AdminFeignClient adminFeignClient;

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

    @GetMapping("/login")
    public void login(){
        Object object = adminFeignClient.login("test","123456");
        System.out.print(object.toString());
    }

    @GetMapping("/getAllLog")
    public void getAllLog(){
        String log = adminFeignClient.getAllLog("Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0IiwiYXV0aG9yaXRpZXMiOiJbXCLns7vnu5_nrqHnkIZcIixcIueUqOaIt-euoeeQhlwiLFwi6KeS6Imy566h55CGXCIsXCLoj5zljZXmnYPpmZDnrqHnkIZcIixcIua1i-ivleaXpeW_l1wiLFwi5pel5b-X566h55CGXCIsXCLojrflj5bmiYDmnInml6Xlv5dcIixcIuadg-mZkOaMiemSrua1i-ivlemhtVwiLFwi5p2D6ZmQ5oyJ6ZKu5rWL6K-V6aG1XCIsXCLmt7vliqDmjInpkq7mtYvor5VcIixcIue8lui-keaMiemSrua1i-ivlVwiLFwi5Yig6Zmk5oyJ6ZKu5rWL6K-VXCJdIiwiZXhwIjoxNTM0MTI5MTY3fQ.s-rtluAH4IWjdweIhBdW8hYg6-R2VCFXddVdfJHqG8ocHVEzl3VpG062XCeBy1DWPR0exWSduWHEuzlr1mIKwQ");
        System.out.println(log);
    }
}
