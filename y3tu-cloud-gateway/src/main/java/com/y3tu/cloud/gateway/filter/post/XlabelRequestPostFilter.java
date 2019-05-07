package com.y3tu.cloud.gateway.filter.post;

import com.netflix.zuul.ZuulFilter;
import org.springframework.stereotype.Component;

import static com.y3tu.cloud.gateway.filter.MyFilterConstants.POST_REQUEST_XLABEL_ORDER;
import static com.y3tu.cloud.gateway.filter.MyFilterConstants.POST_TYPE;


/**
 * xlabel标签拦截器,主要是为了XlabelHeaderInterceptor.shutdownHystrixRequestContext
 *
 * @author liuht
 * @date 2017/12/17
 */
@Component
public class XlabelRequestPostFilter extends ZuulFilter{

    @Override
    public String filterType() {
        return POST_TYPE;
    }

    @Override
    public int filterOrder() {
        return POST_REQUEST_XLABEL_ORDER;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
//        XlabelMvcHeaderInterceptor.shutdownHystrixRequestContext();
        return null;
    }
}
