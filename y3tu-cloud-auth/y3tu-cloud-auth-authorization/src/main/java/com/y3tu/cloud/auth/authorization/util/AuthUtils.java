package com.y3tu.cloud.auth.authorization.util;

import com.netflix.client.ClientException;
import com.y3tu.cloud.auth.authorization.exception.AuthException;
import com.y3tu.cloud.common.constant.CommonConstant;
import com.y3tu.tool.core.codec.Base64Util;
import com.y3tu.tool.core.exception.DefaultError;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.rmi.ServerException;

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
     * @throws ServerException if the Basic header is not present or is not valid
     *                         Base64
     */
    public static String[] extractAndDecodeHeader(String header)
            throws IOException {

        byte[] base64Token = header.substring(6).getBytes("UTF-8");
        byte[] decoded;
        try {
            decoded = Base64Util.decode(base64Token);
        } catch (IllegalArgumentException e) {
            throw new AuthException("Failed to decode basic authentication token", DefaultError.PARAMETER_ANNOTATION_NOT_MATCH);
        }

        String token = new String(decoded, CommonConstant.UTF8);

        int delim = token.indexOf(":");

        if (delim == -1) {
            throw new AuthException("Invalid basic authentication token" + DefaultError.PARAMETER_NOT_MATCH_RULE);
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
    public static String[] extractAndDecodeHeader(HttpServletRequest request)
            throws IOException {
        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith(BASIC_)) {
            throw new AuthException("请求头中client信息为空" + DefaultError.PARAMETER_NOT_MATCH_RULE);
        }

        return extractAndDecodeHeader(header);
    }
}
