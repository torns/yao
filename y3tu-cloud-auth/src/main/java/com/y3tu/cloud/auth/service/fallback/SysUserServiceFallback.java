package com.y3tu.cloud.auth.service.fallback;

import com.y3tu.cloud.auth.service.SysUserService;
import com.y3tu.cloud.common.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 */
@Slf4j
@Service
public class SysUserServiceFallback implements SysUserService {

    @Override
    public UserVO loadUserByUsername(String username) {
        log.error("调用loadUserByUsername方法异常，参数：{}", username);
       return null;
    }

    @Override
    public UserVO loadUserByMobile(String mobile) {
        log.error("调用loadUserByMobile方法异常，参数：{}", mobile);
        return null;
    }
}
