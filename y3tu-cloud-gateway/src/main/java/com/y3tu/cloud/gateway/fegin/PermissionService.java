package com.y3tu.cloud.gateway.fegin;

import com.y3tu.cloud.gateway.fegin.fallback.PermissionServiceFallbackImpl;
import com.y3tu.tool.web.base.pojo.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


/**
 * 角色权限服务相关
 *
 * @author y3tu
 * @date 2018/8/22
 */
@FeignClient(name = "y3tu-cloud-upms-service", fallback = PermissionServiceFallbackImpl.class)
public interface PermissionService {

    /**
     * 通过角色名查询菜单
     *
     * @param role 角色名称
     * @return 权限列表
     */
    @GetMapping(value = "/upms/permission/findPermissionByRole/{role}")
    R findPermissionByRole(@PathVariable String role);
}
