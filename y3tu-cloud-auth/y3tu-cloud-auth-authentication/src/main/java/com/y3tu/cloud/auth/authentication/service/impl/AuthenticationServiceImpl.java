package com.y3tu.cloud.auth.authentication.service.impl;

import com.y3tu.cloud.auth.authentication.feign.ResourceService;
import com.y3tu.cloud.auth.authentication.service.AuthenticationService;
import com.y3tu.cloud.common.config.FilterIgnorePropertiesConfig;
import com.y3tu.cloud.common.constants.SecurityConstants;
import com.y3tu.cloud.common.vo.ResourceVO;
import com.y3tu.tool.core.collection.CollectionUtil;
import com.y3tu.tool.core.collection.IterUtil;
import com.y3tu.tool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.InvalidSignatureException;
import org.springframework.security.jwt.crypto.sign.MacSigner;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @author y3tu
 * @date 2019-05-09
 */
@Service
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {


    @Autowired
    ResourceService resourceService;
    @Autowired
    private FilterIgnorePropertiesConfig filterIgnorePropertiesConfig;

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    /**
     * Authorization认证开头是"bearer "
     */
    private static final int BEARER_BEGIN_INDEX = 7;

    /**
     * jwt token 密钥，主要用于token解析，签名验证
     */
    @Value("${spring.security.oauth2.jwt.signingKey}")
    private String signingKey;

    /**
     * jwt验签
     */
    private MacSigner verifier;

    @Override
    public boolean decide(HttpServletRequest authRequest) {
        log.debug("正在访问的url是:{}，method:{}", authRequest.getServletPath(), authRequest.getMethod());

        // 前端跨域OPTIONS请求预检放行 也可通过前端配置代理实现
        // 在这里放行具有一定风险,也可通过前端配置代理实现
        if (HttpMethod.OPTIONS.name().equalsIgnoreCase(authRequest.getMethod())) {
            return true;
        }
        //获取用户认证信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Object principal = authentication.getPrincipal();
        List<SimpleGrantedAuthority> grantedAuthorityList = (List<SimpleGrantedAuthority>) authentication.getAuthorities();
        boolean hasPermission = false;

        if (principal != null) {
            if (CollectionUtil.isEmpty(grantedAuthorityList)) {
                log.warn("角色列表为空：{}", authentication.getPrincipal());
                return hasPermission;
            }

            Set<ResourceVO> urls = new HashSet<>();
            for (SimpleGrantedAuthority authority : grantedAuthorityList) {
                if (!StrUtil.equals(authority.getAuthority(), "ROLE_USER")) {
                    // TODO 角色与菜单权限的关联关系需要缓存提高访问效率
                    Set<ResourceVO> resourceVOS = resourceService.listResourceByRole(authority.getAuthority());
                    if (IterUtil.isNotEmpty((resourceVOS))) {
                        CollectionUtil.addAll(urls, resourceVOS, null);
                    }
                }
            }

            for (ResourceVO resourceVO : urls) {
                if (StrUtil.isNotEmpty(resourceVO.getUrl()) && antPathMatcher.match(resourceVO.getUrl(), authRequest.getRequestURI())
                        && authRequest.getMethod().equalsIgnoreCase(resourceVO.getMethod())) {
                    hasPermission = true;
                    break;
                }
            }
        }
        return hasPermission;
    }

    @Override
    public Jwt getJwt(String authentication) {
        return JwtHelper.decode(StrUtil.subPre(authentication, BEARER_BEGIN_INDEX));
    }

    @Override
    public boolean invalidJwtAccessToken(String authentication) {
        verifier = Optional.ofNullable(verifier).orElse(new MacSigner(signingKey));
        //是否无效true表示无效
        boolean invalid = Boolean.TRUE;

        try {
            Jwt jwt = getJwt(authentication);
            jwt.verifySignature(verifier);
            invalid = Boolean.FALSE;
        } catch (InvalidSignatureException | IllegalArgumentException ex) {
            log.warn("user token has expired or signature error ");
        }
        return invalid;
    }

    @Override
    public boolean ignoreAuthentication(String url) {
        return filterIgnorePropertiesConfig.getUrls().stream().anyMatch(ignoreUrl -> url.startsWith(StrUtil.trim(ignoreUrl)));
    }

    @Override
    public boolean hasPermission(HttpServletRequest authRequest) {
        String authentication = authRequest.getHeader(SecurityConstants.TOKEN_HEADER);
        //token是否有效
        if (invalidJwtAccessToken(authentication)) {
            return Boolean.FALSE;
        }
        //从认证服务获取是否有权限
        return decide(authRequest);
    }
}
