package com.y3tu.cloud.auth.authentication.config;

import com.y3tu.cloud.auth.authentication.feign.ResourcesService;
import com.y3tu.cloud.common.vo.ResourcesVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * 获取资源
 */
@Component
@Slf4j
public class LoadResourceDefine {
    @Autowired
    ResourcesService resourcesService;
    @Autowired
    private HandlerMappingIntrospector mvcHandlerMappingIntrospector;

    @Bean
    public Map<RequestMatcher, ConfigAttribute> resourceConfigAttributes() {
        List<ResourcesVO> resources = resourcesService.findAll();
        Map<RequestMatcher, ConfigAttribute> map = resources.stream()
                .collect(Collectors.toMap(
                        resource -> {
                            MvcRequestMatcher mvcRequestMatcher = new MvcRequestMatcher(mvcHandlerMappingIntrospector, resource.getUrl());
                            mvcRequestMatcher.setMethod(HttpMethod.resolve(resource.getMethod()));
                            return mvcRequestMatcher;
                        },
                        resource -> new SecurityConfig(resource.getCode())
                        )
                );
        log.debug("resourceConfigAttributes:{}", map);
        return map;
    }
}
