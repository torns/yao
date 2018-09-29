package com.y3tu.cloud.auth.authorization.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

/**
 * Security message
 *
 * @author y3tu
 * @date 2018/9/29
 */
public class SecurityMessageLocal {
    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.addBasenames("classpath:messages/security/messages_zh_CN");
        return messageSource;
    }
}
