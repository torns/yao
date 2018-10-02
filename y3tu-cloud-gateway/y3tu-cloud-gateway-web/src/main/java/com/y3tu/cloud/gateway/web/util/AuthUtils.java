package com.y3tu.cloud.gateway.web.util;


import com.y3tu.tool.core.codec.Base64Util;
import com.y3tu.tool.core.exception.UtilException;
import com.y3tu.tool.core.text.CharsetUtil;
import org.springframework.http.server.reactive.ServerHttpRequest;

import java.io.IOException;

/**
 * 认证授权相关工具类
 *
 * @author y3tu
 * @date 2018/9/29
 */
public class AuthUtils {

    private static final String BASIC_ = "Basic ";

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
}
