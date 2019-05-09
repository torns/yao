package com.y3tu.cloud.auth.authentication.feign;


import com.y3tu.cloud.auth.authentication.feign.fallback.ResourceServiceFallbackImpl;
import com.y3tu.cloud.common.constants.ServiceNameConstants;
import com.y3tu.cloud.common.vo.ResourceVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Set;

/**
 * @author y3tu
 */
@FeignClient(name = ServiceNameConstants.UPMS_SERVER, fallback = ResourceServiceFallbackImpl.class)
public interface ResourceService {
    /**
     * 通过角色名查询菜单
     *
     * @param roleCode 角色编码
     * @return 菜单列表
     */
    @GetMapping("/role/{roleCode}")
    Set<ResourceVO> listResourceByRole(@PathVariable("roleCode") String roleCode);
}
