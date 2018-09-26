package com.y3tu.cloud.auth.authentication.feign;

import com.y3tu.cloud.auth.authentication.feign.fallback.ResourcesServiceFallbackImpl;
import com.y3tu.cloud.common.vo.ResourcesVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author y3tu
 */
@FeignClient(name = "y3tu-cloud-upms-service", fallback = ResourcesServiceFallbackImpl.class)
public interface ResourcesService {
    /**
     * 返回所有的资源定义内容
     *
     * @return
     */
    @GetMapping("/upms/resources/findAll")
    List<ResourcesVO> findAll();

    /**
     * 根据角色code查询到角色把对应的资源定义
     *
     * @param roleCodes
     * @return
     */
    @GetMapping("/upms/resources/findByRoleCode")
    List<ResourcesVO> findByRoleCode(@RequestParam("roleCodes") String[] roleCodes);
}
