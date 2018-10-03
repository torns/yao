package com.y3tu.cloud.gateway.filter;

import com.y3tu.cloud.gateway.config.FilterIgnorePropertiesConfig;
import com.y3tu.cloud.gateway.exception.ValidateCodeException;
import com.y3tu.tool.core.text.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;


/**
 * @author lengleng
 * @date 2018/7/4
 * 验证码处理
 */
@Slf4j
@Component
public class ImageCodeGatewayFilter extends AbstractGatewayFilterFactory {
    public static final String DEFAULT_CODE_KEY = "DEFAULT_CODE_KEY";
    public static final String OAUTH_TOKEN_URL = "/oauth/token";
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private FilterIgnorePropertiesConfig filterIgnorePropertiesConfig;


    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();

            // 不是登录请求，直接向下执行
            if (!StringUtils.containsIgnoreCase(request.getURI().getPath(), OAUTH_TOKEN_URL)) {
                return chain.filter(exchange);
            }

            // 终端设置不校验， 直接向下执行(1. 从请求参数中获取 2.从header取)
            String clientId = request.getQueryParams().getFirst("client_id");
            if (StringUtils.isNotBlank(clientId)) {
                if (filterIgnorePropertiesConfig.getClients().contains(clientId)) {
                    return chain.filter(exchange);
                }
            }

            //校验验证码合法性
            checkCode(request);

            return chain.filter(exchange);
        };
    }

    /**
     * 检查code
     *
     * @param request
     * @throws ValidateCodeException 校验异常
     */
    private void checkCode(ServerHttpRequest request) {
        String code = request.getQueryParams().getFirst("code");

        if (StringUtils.isBlank(code)) {
            throw new ValidateCodeException("验证码为空");
        }

        String randomStr = request.getQueryParams().getFirst("randomStr");
        if (StringUtils.isBlank(randomStr)) {
            throw new ValidateCodeException("随机字符为空");
        }

        String key = DEFAULT_CODE_KEY + randomStr;
        if (!redisTemplate.hasKey(key)) {
            throw new ValidateCodeException("获取redis验证码错误");
        }

        Object codeObj = redisTemplate.opsForValue().get(key);

        if (codeObj == null) {
            throw new ValidateCodeException("获取redis验证码为空");
        }

        String saveCode = codeObj.toString();
        if (StringUtils.isBlank(saveCode)) {
            redisTemplate.delete(key);
            throw new ValidateCodeException("获取redis验证码为空");
        }

        if (!StringUtils.equals(saveCode, code)) {
            redisTemplate.delete(key);
            throw new ValidateCodeException("验证码错误!");
        }

        redisTemplate.delete(key);
    }
}
