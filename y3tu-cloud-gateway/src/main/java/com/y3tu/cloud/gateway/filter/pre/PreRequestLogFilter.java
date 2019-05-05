package com.y3tu.cloud.gateway.filter.pre;


import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Enumeration;

import static com.y3tu.cloud.gateway.filter.MyFilterConstants.PRE_REQUEST_LOG_ORDER;
import static com.y3tu.cloud.gateway.filter.MyFilterConstants.PRE_TYPE;


/**
 * 请求日志记录
 *
 * @author y3tu
 */
@Component
public class PreRequestLogFilter extends ZuulFilter {

    private static final Log log = LogFactory.getLog(PreRequestLogFilter.class);

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
        //todo
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
