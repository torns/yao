package com.y3tu.cloud.gateway.feign;

import com.y3tu.cloud.common.constants.ServiceNameConstants;
import com.y3tu.cloud.common.vo.ResourceVO;
import com.y3tu.cloud.gateway.feign.fallback.ResourceServiceFallbackImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Set;

/**
 * @author y3tu
 */
@FeignClient(name = ServiceNameConstants.UPMS_SERVICE, fallback = ResourceServiceFallbackImpl.class)
public interface ResourceService {
    /**
     * 根据角色查询资源信息
     * @param roleCode
     * @return
     */
    @GetMapping("/resource/role/{roleCode}")
    Set<ResourceVO> listResourceByRole(@PathVariable("roleCode") String roleCode);
}
