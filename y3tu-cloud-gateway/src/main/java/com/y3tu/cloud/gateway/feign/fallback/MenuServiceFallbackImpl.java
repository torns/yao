package com.y3tu.cloud.gateway.feign.fallback;

import com.y3tu.cloud.common.vo.MenuVO;
import com.y3tu.cloud.gateway.feign.MenuService;
import com.y3tu.tool.core.collection.CollectionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;


/**
 * @author y3tu
 */
@Slf4j
@Service
public class MenuServiceFallbackImpl implements MenuService {
    @Override
    public Set<MenuVO> findMenuByRole(String role) {
        log.error("调用{}异常{}", "findMenuByRole", role);
        return CollectionUtil.newHashSet();
    }
}
