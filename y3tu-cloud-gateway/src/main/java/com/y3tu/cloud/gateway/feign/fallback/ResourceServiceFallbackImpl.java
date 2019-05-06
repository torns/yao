package com.y3tu.cloud.gateway.feign.fallback;

import com.y3tu.cloud.common.vo.ResourceVO;
import com.y3tu.cloud.gateway.feign.ResourceService;
import com.y3tu.tool.core.collection.CollectionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;


/**
 * @author y3tu
 */
@Slf4j
@Service
public class ResourceServiceFallbackImpl implements ResourceService {


    @Override
    public Set<ResourceVO> listResourceByRole(String roleCode) {
        log.error("调用{}异常{}", "listResourceByRole", roleCode);
        return CollectionUtil.newHashSet();
    }
}
