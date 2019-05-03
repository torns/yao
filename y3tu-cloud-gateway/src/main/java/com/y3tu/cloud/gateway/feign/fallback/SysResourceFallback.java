package com.y3tu.cloud.gateway.feign.fallback;

import com.y3tu.cloud.common.vo.SysResourceVO;
import com.y3tu.cloud.gateway.feign.SysResourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Set;

@Slf4j
@Service
public class SysResourceFallback implements SysResourceService {
    @Override
    public Set<SysResourceVO> listResourceByRole(String roleCode) {
        log.error("调用【y3tu-cloud-back-service】服务接口【/resource/role/{}】异常", roleCode);
        return Collections.emptySet();
    }
}
