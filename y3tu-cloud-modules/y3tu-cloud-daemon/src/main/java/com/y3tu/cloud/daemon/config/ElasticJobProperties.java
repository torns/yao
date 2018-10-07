package com.y3tu.cloud.daemon.config;

import com.dangdang.ddframe.job.api.JobType;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author y3tu
 * @date 2018/10/7
 */
@Data
@ConfigurationProperties(prefix = "spring.elasticjob")
public class ElasticJobProperties {
    private ZkConfiguration zookeeper;
    private Map<String, SimpleConfiguration> simples = new LinkedHashMap();
    private Map<String, DataflowConfiguration> dataflows = new LinkedHashMap();
    private Map<String, ScriptConfiguration> scripts = new LinkedHashMap();

    @Data
    public static class JobConfiguration {
        private String jobClass;
        private String registryCenterRef = "elasticJobRegistryCenter";
        private String cron;
        private int shardingTotalCount = 1;
        private String shardingItemParameters = "0=A";
        private String jobInstanceId;
        private String jobParameter;
        private boolean monitorExecution = true;
        private int monitorPort = -1;
        private int maxTimeDiffSeconds = -1;
        private boolean failover = false;
        private boolean misfire = true;
        private String jobShardingStrategyClass;
        private String description;
        private boolean disabled = false;
        private boolean overwrite = true;
        private String jobExceptionHandler;
        private String executorServiceHandler;
        private int reconcileIntervalMinutes = 10;
        private String eventTraceRdbDataSource;
        private ElasticJobProperties.JobConfiguration.Listener listener;

        @Data
        public static class Listener {
            String listenerClass;
            String distributedListenerClass;
            Long startedTimeoutMilliseconds = 9223372036854775807L;
            Long completedTimeoutMilliseconds = 9223372036854775807L;
        }
    }

    @Data
    public static class ScriptConfiguration extends JobConfiguration {
        private final JobType jobType;
        private String scriptCommandLine;

        public ScriptConfiguration() {
            this.jobType = JobType.SCRIPT;
        }
    }

    @Data
    public static class DataflowConfiguration extends JobConfiguration {
        private final JobType jobType;
        private boolean streamingProcess;

        public DataflowConfiguration() {
            this.jobType = JobType.DATAFLOW;
            this.streamingProcess = false;
        }
    }

    @Data
    public static class SimpleConfiguration extends JobConfiguration {
        private final JobType jobType;

        public SimpleConfiguration() {
            this.jobType = JobType.SIMPLE;
        }
    }

    @Data
    public static class ZkConfiguration {
        private String serverLists;
        private String namespace;
        private int baseSleepTimeMilliseconds = 1000;
        private int maxSleepTimeMilliseconds = 3000;
        private int maxRetries = 3;
        private int connectionTimeoutMilliseconds = 15000;
        private int sessionTimeoutMilliseconds = 60000;
        private String digest;
    }
}
