package com.y3tu.cloud.log.starter.autoconfigure;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author y3tu
 */
@Configuration
@ConditionalOnClass(LogAspect.class)
public class LogAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(LogAspect.class)
    public LogAspect logAspect() {
        return new LogAspect();
    }

}
