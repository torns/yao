package com.y3tu.cloud.gateway.feign.fallback;


import com.y3tu.cloud.common.vo.SysLogVO;
import com.y3tu.cloud.gateway.feign.SysLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author liuht
 * @date 2018/9/13 17:09
 */
@Slf4j
@Service
public class SysLogServiceFallbackImpl implements SysLogService {

    @Override
    public void add(SysLogVO sysLog) {
        log.error("调用{}异常{}", "addSysLog", sysLog);
    }
}
