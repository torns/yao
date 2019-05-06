package com.y3tu.cloud.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


/**
 * 忽略的url
 *
 * @author y3tu
 */
@Data
@RefreshScope
@ConfigurationProperties(prefix = "ignore")
@Component
public class FilterIgnorePropertiesConfig {

    /**
     * 忽略的url
     */
    private List<String> urls = new ArrayList<>();

    /**
     * 忽略的客户端
     */
    private List<String> client = new ArrayList<>();

}
