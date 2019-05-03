package com.y3tu.cloud.gateway.feign;

import com.y3tu.cloud.common.vo.SysResourceVO;
import com.y3tu.cloud.gateway.config.FeignConfig;
import com.y3tu.cloud.gateway.feign.fallback.SysResourceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Set;

@FeignClient(name = "y3tu-cloud-back-service", fallback = SysResourceFallback.class, configuration = FeignConfig.class)
public interface SysResourceService {

    /**
     * 根据角色查询资源信息
     *
     * @param roleCode
     * @return
     */
    @GetMapping("/resource/role/{roleCode}")
    Set<SysResourceVO> listResourceByRole(@PathVariable("roleCode") String roleCode);


}
