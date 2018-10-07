package com.y3tu.cloud.daemon.jobinit;

import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.JobTypeConfiguration;
import com.dangdang.ddframe.job.config.dataflow.DataflowJobConfiguration;
import com.y3tu.cloud.daemon.config.ElasticJobProperties;

import java.util.Iterator;
import java.util.Map;

/**
 * @author y3tu
 * @date 2018/10/7
 */
public class DataflowJobInitialization extends AbstractJobInitialization {
    private Map<String, ElasticJobProperties.DataflowConfiguration> dataflowConfigurationMap;

    public DataflowJobInitialization(final Map<String, ElasticJobProperties.DataflowConfiguration> dataflowConfigurationMap) {
        this.dataflowConfigurationMap = dataflowConfigurationMap;
    }

    public void init() {
        Iterator var1 = this.dataflowConfigurationMap.keySet().iterator();

        while (var1.hasNext()) {
            String jobName = (String) var1.next();
            ElasticJobProperties.DataflowConfiguration configuration = (ElasticJobProperties.DataflowConfiguration) this.dataflowConfigurationMap.get(jobName);
            this.initJob(jobName, configuration.getJobType(), configuration);
        }

    }

    @Override
    public JobTypeConfiguration getJobTypeConfiguration(String jobName, JobCoreConfiguration jobCoreConfiguration) {
        ElasticJobProperties.DataflowConfiguration configuration = (ElasticJobProperties.DataflowConfiguration) this.dataflowConfigurationMap.get(jobName);
        return new DataflowJobConfiguration(jobCoreConfiguration, configuration.getJobClass(), configuration.isStreamingProcess());
    }
}
