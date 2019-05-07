package com.y3tu.cloud.auth.controller;

import com.y3tu.cloud.auth.query.OAuth2AccessTokenQuery;
import com.y3tu.cloud.common.constants.CommonConstants;
import com.y3tu.cloud.common.constants.SecurityConstants;
import com.y3tu.tool.core.pojo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

@RestController
public class AuthController {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private ConsumerTokenServices consumerTokenServices;

    @Autowired
    private RedisTokenStore redisTokenStore;

    @DeleteMapping("/token/{token}")
    public R<Boolean> removeAccessToken(@PathVariable("token") String token) {
        return new R<>(consumerTokenServices.revokeToken(token));
    }

    @GetMapping("/token")
    public R<Collection<OAuth2AccessToken>> readAllToken() {
        return new R<>(redisTokenStore.findTokensByClientId(SecurityConstants.CLOUD));
    }

    @GetMapping("/token/page")
    public R getTokenList(OAuth2AccessTokenQuery oAuth2AccessTokenQuery) {
        redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
        oAuth2AccessTokenQuery.setRecords((List) redisTemplate.opsForList().range(CommonConstants.FISHER_REDIS_LIST_LEY, oAuth2AccessTokenQuery.getStart(), oAuth2AccessTokenQuery.getEnd()));
        List<OAuth2AccessToken> all = (List) redisTemplate.opsForList().range(CommonConstants.FISHER_REDIS_LIST_LEY, 0, -1);
        if (all != null) {
            oAuth2AccessTokenQuery.setTotal(all.size());
        }
        return R.success(oAuth2AccessTokenQuery);
    }

}
