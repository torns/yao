package com.y3tu.cloud.gateway.feign;


import com.y3tu.cloud.common.constant.ServiceNameConstants;
import com.y3tu.cloud.common.vo.SysLogVO;
import com.y3tu.cloud.gateway.feign.fallback.SysLogServiceFallbackImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 系统日志Service
 *
 * @author liuht
 * @date 2018/9/13 17:06
 */
@FeignClient(name = ServiceNameConstants.UPMS_SERVICE, fallback = SysLogServiceFallbackImpl.class)
public interface SysLogService {

    /**
     * 添加日志
     *
     * @param log 日志实体
     */
    @PostMapping("/log")
    void add(@RequestBody SysLogVO log);
}
