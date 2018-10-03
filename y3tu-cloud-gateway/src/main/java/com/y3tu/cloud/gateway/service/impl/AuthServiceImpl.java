package com.y3tu.cloud.gateway.service.impl;

import com.y3tu.cloud.gateway.config.FilterIgnorePropertiesConfig;
import com.y3tu.cloud.gateway.feign.AuthProvider;
import com.y3tu.cloud.gateway.service.IAuthService;
import com.y3tu.tool.core.text.StringUtils;
import com.y3tu.tool.web.base.pojo.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.InvalidSignatureException;
import org.springframework.security.jwt.crypto.sign.MacSigner;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author y3tu
 * @date 2018/8/24
 */
@Service
@Slf4j
public class AuthServiceImpl implements IAuthService {

    @Autowired
    private AuthProvider authProvider;
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

    @Autowired
    private FilterIgnorePropertiesConfig filterIgnorePropertiesConfig;


    @Override
    public R authenticate(String authentication, String url, String method) {
        return authProvider.auth(authentication, url, method);
    }

    @Override
    public boolean ignoreAuthentication(String url) {
        return filterIgnorePropertiesConfig.getUrls().stream().anyMatch(ignoreUrl -> url.startsWith(StringUtils.trim(ignoreUrl)));
    }

    @Override
    public boolean hasPermission(R r) {
        return Boolean.valueOf(r.getStatus() == R.Status.OK);
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
    public boolean hasPermission(String authentication, String url, String method) {
        //token是否有效
        if (invalidJwtAccessToken(authentication)) {
            return Boolean.FALSE;
        }
        //从认证服务获取是否有权限
        return hasPermission(authenticate(authentication, url, method));
    }

    @Override
    public Jwt getJwt(String authentication) {
        return JwtHelper.decode(StringUtils.substring(authentication, BEARER_BEGIN_INDEX));
    }
}