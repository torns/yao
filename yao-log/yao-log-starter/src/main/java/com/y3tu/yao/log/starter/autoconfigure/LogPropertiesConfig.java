package com.y3tu.yao.log.starter.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 *
 * @author y3tu
 */
@Data
@ConfigurationProperties(prefix = "yao.log")
@Component
public class LogPropertiesConfig {
    /**
     * 默认保存模式为数据库
     * db 数据库保存
     * es elasticSearch保存
     */
    String saveMode;
}
