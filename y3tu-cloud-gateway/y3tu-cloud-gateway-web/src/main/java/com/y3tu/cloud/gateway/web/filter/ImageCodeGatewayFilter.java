package com.y3tu.cloud.gateway.web.filter;

import com.y3tu.cloud.gateway.web.config.FilterIgnorePropertiesConfig;
import com.y3tu.tool.core.codec.Base64Util;
import com.y3tu.tool.core.exception.UtilException;
import com.y3tu.tool.core.exception.ValidatorException;
import com.y3tu.tool.core.text.CharsetUtil;
import com.y3tu.tool.core.text.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

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
    private static final String BASIC_ = "Basic ";
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private FilterIgnorePropertiesConfig filterIgnorePropertiesConfig;

    /**
     * 从header 请求中的clientId/clientsecect
     *
     * @param header header中的参数
     * @throws UtilException if the Basic header is not present or is not valid
     *                       Base64
     */
    public static String[] extractAndDecodeHeader(String header)
            throws IOException, UtilException {

        byte[] base64Token = header.substring(6).getBytes("UTF-8");
        byte[] decoded;
        try {
            decoded = Base64Util.decode(base64Token);
        } catch (IllegalArgumentException e) {
            throw new UtilException(
                    "Failed to decode basic authentication token");
        }

        String token = new String(decoded, CharsetUtil.UTF_8);

        int delim = token.indexOf(":");

        if (delim == -1) {
            throw new UtilException("Invalid basic authentication token");
        }
        return new String[]{token.substring(0, delim), token.substring(delim + 1)};
    }

    /**
     * *从header 请求中的clientId/clientsecect
     *
     * @param request
     * @return
     * @throws IOException
     */
    public static String[] extractAndDecodeHeader(ServerHttpRequest request)
            throws IOException, UtilException {
        String header = request.getHeaders().getFirst("Authorization");

        if (header == null || !header.startsWith(BASIC_)) {
            throw new UtilException("请求头中client信息为空");
        }

        return extractAndDecodeHeader(header);
    }

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
            try {
                String[] clientInfos = extractAndDecodeHeader(request);
                if (filterIgnorePropertiesConfig.getClients().contains(clientInfos[0])) {
                    return chain.filter(exchange);
                }
            } catch (Exception e) {
                ServerHttpResponse response = exchange.getResponse();
                response.setStatusCode(HttpStatus.PRECONDITION_REQUIRED);
                return response.setComplete();
            }

            //校验验证码合法性
            try {
                checkCode(request);
            } catch (ValidatorException e) {
                ServerHttpResponse response = exchange.getResponse();
                response.setStatusCode(HttpStatus.PRECONDITION_REQUIRED);
                return response.setComplete();
            }

            return chain.filter(exchange);
        };
    }

    /**
     * 检查code
     *
     * @param request
     * @throws ValidatorException 校验异常
     */
    private void checkCode(ServerHttpRequest request) throws ValidatorException {
        String code = request.getQueryParams().getFirst("code");

        if (StringUtils.isBlank(code)) {
            throw new ValidatorException();
        }

        String randomStr = request.getQueryParams().getFirst("randomStr");
        if (StringUtils.isBlank(randomStr)) {
            throw new ValidatorException();
        }

        String key = DEFAULT_CODE_KEY + randomStr;
        if (!redisTemplate.hasKey(key)) {
            throw new ValidatorException();
        }

        Object codeObj = redisTemplate.opsForValue().get(key);

        if (codeObj == null) {
            throw new ValidatorException();
        }

        String saveCode = codeObj.toString();
        if (StringUtils.isBlank(saveCode)) {
            redisTemplate.delete(key);
            throw new ValidatorException();
        }

        if (!StringUtils.equals(saveCode, code)) {
            redisTemplate.delete(key);
            throw new ValidatorException();
        }

        redisTemplate.delete(key);
    }
}
