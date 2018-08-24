package com.y3tu.cloud.auth.authentication.feign.fallback;

import com.y3tu.cloud.auth.authentication.feign.ResourcesService;
import com.y3tu.cloud.common.vo.ResourcesVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ResourcesServiceFallbackImpl implements ResourcesService {


    @Override
    public List<ResourcesVO> findAll() {
        log.error("调用{}异常:{}", "findAll");
        return null;
    }

    @Override
    public List<ResourcesVO> findByRoleCode(String[] roleCodes) {
        log.error("调用{}异常:{}", "findByRoleCode",roleCodes);
        return null;
    }
}
