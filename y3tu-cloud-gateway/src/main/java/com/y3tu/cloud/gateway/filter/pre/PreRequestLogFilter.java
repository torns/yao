package com.y3tu.cloud.gateway.filter.pre;


import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.y3tu.cloud.common.constant.LogType;
import com.y3tu.cloud.common.constant.SecurityConstants;
import com.y3tu.cloud.common.vo.SysLogVO;
import com.y3tu.cloud.gateway.feign.SysLogService;
import com.y3tu.tool.core.text.StringUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.Enumeration;

import static com.y3tu.cloud.gateway.filter.MyFilterConstants.PRE_REQUEST_LOG_ORDER;
import static com.y3tu.cloud.gateway.filter.MyFilterConstants.PRE_TYPE;


/**
 * 请求日志记录
 *
 * @author liuht
 * @date 2017/12/17
 */
@Component
public class PreRequestLogFilter extends ZuulFilter {

    private static final Log log = LogFactory.getLog(PreRequestLogFilter.class);

    @Autowired
    private SysLogService logService;

    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return PRE_REQUEST_LOG_ORDER;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        final RequestContext ctx = RequestContext.getCurrentContext();
        final HttpServletRequest request = ctx.getRequest();
        log.info(String.format("send %s request to %s", request.getMethod(), request.getRequestURL().toString()));
        addLog(request, ctx);
        return null;
    }

    /**
     * 添加系统日志
     *
     * @param request 请求对象
     * @param ctx     RequestContext
     */
    private void addLog(HttpServletRequest request, RequestContext ctx) {
        final SysLogVO log = new SysLogVO();
        log.setCreateTime(new Date());
        log.setRemoteAddr(request.getRemoteAddr());
        log.setRequestUri(request.getRequestURI());
        log.setMethod(request.getMethod());

        if (StringUtils.containsAnyIgnoreCase(request.getRequestURI(),
                SecurityConstants.OAUTH_TOKEN_URL, SecurityConstants.MOBILE_TOKEN_URL)) {
            // 记录登录日志
            log.setType(LogType.Login.name());
            log.setTitle(LogType.Login.name());
            log.setParams(queryParam(request));
            log.setCreateBy(request.getParameter("username"));
            logService.add(log);
        } else {
            if (!HttpMethod.GET.matches(request.getMethod())) {
                // 记录操作日志
                log.setType(LogType.Operation.name());
                log.setTitle(LogType.Operation.name());
                log.setParams(queryBody(request));
                log.setCreateBy(ctx.getZuulRequestHeaders().get(SecurityConstants.USER_HEADER));
                logService.add(log);
            }
        }
    }

    /**
     * 获取请求body
     */
    private String queryBody(HttpServletRequest request) {
        try {
            ServletInputStream stream = request.getInputStream();
            if (stream != null) {
                return IOUtils.toString(stream, Charset.defaultCharset());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取get 参数
     */
    private String queryParam(HttpServletRequest request) {
        StringBuilder params = new StringBuilder();
        final Enumeration<String> names = request.getParameterNames();
        while (names.hasMoreElements()) {
            String name = names.nextElement();
            params.append(name);
            params.append("=");
            if ("password".equals(name)) {
                params.append("******");
            } else {
                params.append(request.getParameter(name));
            }
            params.append("&");
        }
        if (params.length() > 0) {
            params.delete(params.length() - 1, params.length());
            return params.toString();
        }
        return null;
    }
}
