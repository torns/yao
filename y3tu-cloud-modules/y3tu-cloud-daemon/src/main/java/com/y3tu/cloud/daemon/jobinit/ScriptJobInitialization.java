package com.y3tu.cloud.daemon.jobinit;

import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.JobTypeConfiguration;
import com.dangdang.ddframe.job.config.script.ScriptJobConfiguration;
import com.y3tu.cloud.daemon.config.ElasticJobProperties;

import java.util.Iterator;
import java.util.Map;

/**
 * @author y3tu
 * @date 2018/10/7
 */
public class ScriptJobInitialization extends AbstractJobInitialization {

    private Map<String, ElasticJobProperties.ScriptConfiguration> scriptConfigurationMap;

    public ScriptJobInitialization(final Map<String, ElasticJobProperties.ScriptConfiguration> scriptConfigurationMap) {
        this.scriptConfigurationMap = scriptConfigurationMap;
    }

    public void init() {
        Iterator var1 = this.scriptConfigurationMap.keySet().iterator();

        while(var1.hasNext()) {
            String jobName = (String)var1.next();
            ElasticJobProperties.ScriptConfiguration configuration = (ElasticJobProperties.ScriptConfiguration)this.scriptConfigurationMap.get(jobName);
            this.initJob(jobName, configuration.getJobType(), configuration);
        }

    }

    @Override
    public JobTypeConfiguration getJobTypeConfiguration(String jobName, JobCoreConfiguration jobCoreConfiguration) {
        ElasticJobProperties.ScriptConfiguration configuration = (ElasticJobProperties.ScriptConfiguration)this.scriptConfigurationMap.get(jobName);
        return new ScriptJobConfiguration(jobCoreConfiguration, configuration.getScriptCommandLine());
    }
}
