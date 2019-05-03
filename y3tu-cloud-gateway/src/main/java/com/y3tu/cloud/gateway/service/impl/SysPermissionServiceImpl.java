package com.y3tu.cloud.gateway.service.impl;

import com.y3tu.cloud.common.vo.SysResourceVO;
import com.y3tu.cloud.gateway.feign.SysResourceService;
import com.y3tu.cloud.gateway.service.SysPermissionService;
import com.y3tu.tool.core.collection.CollectionUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service("permissionService")
@Slf4j
public class SysPermissionServiceImpl implements SysPermissionService {

    @Autowired
    private SysResourceService sysResourceService;

    @Override
    public Boolean hasPermission(HttpServletRequest request, Authentication authentication) {
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        Object principal = authentication.getPrincipal();
        boolean hasPermission = false;
        List<SimpleGrantedAuthority> grantedAuthorityList = (List<SimpleGrantedAuthority>) authentication.getAuthorities();
        if (principal != null) {
            if (CollectionUtil.isEmpty(grantedAuthorityList)) {
                log.warn("角色列表为空：{}", authentication.getPrincipal());
                return hasPermission;
            }
            Set<SysResourceVO> urls = new HashSet<>();
            for (SimpleGrantedAuthority authority : grantedAuthorityList) {
                // TODO 角色与菜单权限的关联关系需要缓存提高访问效率
                Set<SysResourceVO> menuVOSet = sysResourceService.listResourceByRole(authority.getAuthority());
                if (CollectionUtil.isNotEmpty(menuVOSet)) {
                    CollectionUtil.addAll(urls, menuVOSet);
                }
            }
            String uri = request.getRequestURI();
            for (SysResourceVO menu : urls) {
                if (StringUtils.isNotEmpty(menu.getUrl()) && antPathMatcher.match(menu.getUrl(), uri)
                        && request.getMethod().equalsIgnoreCase(menu.getMethod())) {
                    hasPermission = true;
                    break;
                }
            }
        }
        return hasPermission;
    }
}