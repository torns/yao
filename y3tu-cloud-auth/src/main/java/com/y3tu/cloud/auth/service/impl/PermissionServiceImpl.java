package com.y3tu.cloud.auth.service.impl;

import com.y3tu.cloud.auth.feign.UserService;
import com.y3tu.cloud.auth.service.PermissionService;
import com.y3tu.cloud.common.vo.PermissionVO;
import com.y3tu.tool.core.collection.CollectionUtil;
import com.y3tu.tool.core.text.StringUtils;
import com.y3tu.tool.web.base.pojo.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @author y3tu
 */
@Slf4j
@Service("permissionService")
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private UserService userService;

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {

        Object principal = authentication.getPrincipal();
        List<SimpleGrantedAuthority> grantedAuthorityList = (List<SimpleGrantedAuthority>) authentication.getAuthorities();
        boolean hasPermission = false;

        if (principal != null) {
            if (CollectionUtil.isEmpty(grantedAuthorityList)) {
                log.warn("角色列表为空：{}", authentication.getPrincipal());
                return hasPermission;
            }

            List<PermissionVO> urls = new ArrayList<>();
            for (SimpleGrantedAuthority authority : grantedAuthorityList) {
                if (!StringUtils.equals(authority.getAuthority(), "ROLE_USER")) {
                    R r = userService.findPermissionByRole(authority.getAuthority());
                    List<PermissionVO> permissionVOS = (List<PermissionVO>) r.get("data");
                    if (CollectionUtil.isNotEmpty(permissionVOS)) {
                        CollectionUtil.addAll(urls, permissionVOS,null);
                    }
                }
            }

            for (PermissionVO permissionVO : urls) {
                if (StringUtils.isNotEmpty(permissionVO.getPath()) && antPathMatcher.match(permissionVO.getPath(), request.getRequestURI())) {
                    hasPermission = true;
                    break;
                }
            }
        }
        return true;
    }
}
