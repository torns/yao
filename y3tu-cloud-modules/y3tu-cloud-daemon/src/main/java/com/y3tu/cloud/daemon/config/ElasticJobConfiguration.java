package com.y3tu.cloud.daemon.config;

import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import com.y3tu.cloud.daemon.jobinit.DataflowJobInitialization;
import com.y3tu.cloud.daemon.jobinit.ScriptJobInitialization;
import com.y3tu.cloud.daemon.jobinit.SimpleJobInitialization;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author y3tu
 * @date 2018/10/7
 */
@Data
@EnableConfigurationProperties({ElasticJobProperties.class})
@Configuration
public class ElasticJobConfiguration {
    public static final String DEFAULT_REGISTRY_CENTER_NAME = "elasticJobRegistryCenter";
    @Autowired
    private ElasticJobProperties elasticJobProperties;

    @Bean(name = "elasticJobRegistryCenter")
    @ConditionalOnMissingBean
    public ZookeeperRegistryCenter elasticJobRegistryCenter() {
        ElasticJobProperties.ZkConfiguration regCenterProperties = this.elasticJobProperties.getZookeeper();
        ZookeeperConfiguration zookeeperConfiguration = new ZookeeperConfiguration(regCenterProperties.getServerLists(), regCenterProperties.getNamespace());
        zookeeperConfiguration.setBaseSleepTimeMilliseconds(regCenterProperties.getBaseSleepTimeMilliseconds());
        zookeeperConfiguration.setConnectionTimeoutMilliseconds(regCenterProperties.getConnectionTimeoutMilliseconds());
        zookeeperConfiguration.setMaxSleepTimeMilliseconds(regCenterProperties.getMaxSleepTimeMilliseconds());
        zookeeperConfiguration.setSessionTimeoutMilliseconds(regCenterProperties.getSessionTimeoutMilliseconds());
        zookeeperConfiguration.setMaxRetries(regCenterProperties.getMaxRetries());
        zookeeperConfiguration.setDigest(regCenterProperties.getDigest());
        return new ZookeeperRegistryCenter(zookeeperConfiguration);
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnBean({ZookeeperRegistryCenter.class})
    public SimpleJobInitialization simpleJobInitialization() {
        return new SimpleJobInitialization(this.elasticJobProperties.getSimples());
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnBean({ZookeeperRegistryCenter.class})
    public DataflowJobInitialization dataflowJobInitialization() {
        return new DataflowJobInitialization(this.elasticJobProperties.getDataflows());
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnBean({ZookeeperRegistryCenter.class})
    public ScriptJobInitialization scriptJobInitialization() {
        return new ScriptJobInitialization(this.elasticJobProperties.getScripts());
    }
}
