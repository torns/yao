package com.y3tu.cloud.daemon.jobinit;

import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.JobTypeConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.y3tu.cloud.daemon.config.ElasticJobProperties;

import java.util.Iterator;
import java.util.Map;

/**
 * @author y3tu
 * @date 2018/10/7
 */
public class SimpleJobInitialization extends AbstractJobInitialization {

    private Map<String, ElasticJobProperties.SimpleConfiguration> simpleConfigurationMap;

    public SimpleJobInitialization(final Map<String, ElasticJobProperties.SimpleConfiguration> simpleConfigurationMap) {
        this.simpleConfigurationMap = simpleConfigurationMap;
    }

    public void init() {
        Iterator var1 = this.simpleConfigurationMap.keySet().iterator();

        while (var1.hasNext()) {
            String jobName = (String) var1.next();
            ElasticJobProperties.SimpleConfiguration configuration = (ElasticJobProperties.SimpleConfiguration) this.simpleConfigurationMap.get(jobName);
            this.initJob(jobName, configuration.getJobType(), configuration);
        }

    }

    public JobTypeConfiguration getJobTypeConfiguration(String jobName, JobCoreConfiguration jobCoreConfiguration) {
        ElasticJobProperties.SimpleConfiguration configuration = (ElasticJobProperties.SimpleConfiguration) this.simpleConfigurationMap.get(jobName);
        return new SimpleJobConfiguration(jobCoreConfiguration, configuration.getJobClass());
    }
}
