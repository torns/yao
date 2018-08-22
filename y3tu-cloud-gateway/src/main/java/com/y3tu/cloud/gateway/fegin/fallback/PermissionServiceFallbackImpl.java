package com.y3tu.cloud.gateway.fegin.fallback;

import com.y3tu.cloud.common.vo.PermissionVO;
import com.y3tu.cloud.gateway.fegin.PermissionService;
import com.y3tu.tool.core.collection.SetUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * @author y3tu
 * @date 2018/8/22
 */
@Slf4j
@Service
public class PermissionServiceFallbackImpl implements PermissionService {
    @Override
    public Set<PermissionVO> findPermissionByRole(String role) {
        log.error("调用{}异常{}", "findPermissionByRole", role);
        return SetUtil.newHashSet();
    }
}
