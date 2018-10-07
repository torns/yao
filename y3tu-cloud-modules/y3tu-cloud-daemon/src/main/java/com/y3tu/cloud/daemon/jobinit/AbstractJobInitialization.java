package com.y3tu.cloud.daemon.jobinit;

import com.dangdang.ddframe.job.api.ElasticJob;
import com.dangdang.ddframe.job.api.JobType;
import com.dangdang.ddframe.job.api.dataflow.DataflowJob;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.JobTypeConfiguration;
import com.dangdang.ddframe.job.event.rdb.JobEventRdbConfiguration;
import com.dangdang.ddframe.job.executor.handler.JobProperties;
import com.dangdang.ddframe.job.lite.api.listener.AbstractDistributeOnceElasticJobListener;
import com.dangdang.ddframe.job.lite.api.listener.ElasticJobListener;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import com.y3tu.cloud.daemon.config.ElasticJobProperties;
import com.y3tu.tool.core.text.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.util.CollectionUtils;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author y3tu
 * @date 2018/10/7
 */
public abstract class AbstractJobInitialization implements ApplicationContextAware {

    protected ApplicationContext applicationContext;


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    protected void initJob(String jobName, JobType jobType, ElasticJobProperties.JobConfiguration configuration) {
        ElasticJob elasticJob = this.registerElasticJob(jobName, configuration.getJobClass(), jobType);
        ZookeeperRegistryCenter regCenter = this.getZookeeperRegistryCenter(configuration.getRegistryCenterRef());
        JobCoreConfiguration jobCoreConfiguration = this.getJobCoreConfiguration(jobName, configuration);
        JobTypeConfiguration jobTypeConfiguration = this.getJobTypeConfiguration(jobName, jobCoreConfiguration);
        LiteJobConfiguration liteJobConfiguration = this.getLiteJobConfiguration(jobTypeConfiguration, configuration);
        JobEventRdbConfiguration jobEventRdbConfiguration = this.getJobEventRdbConfiguration(configuration.getEventTraceRdbDataSource());
        ElasticJobListener[] elasticJobListeners = this.creatElasticJobListeners(configuration.getListener());
        elasticJobListeners = null == elasticJobListeners ? new ElasticJobListener[0] : elasticJobListeners;
        if (null == jobEventRdbConfiguration) {
            (new SpringJobScheduler(elasticJob, regCenter, liteJobConfiguration, elasticJobListeners)).init();
        } else {
            (new SpringJobScheduler(elasticJob, regCenter, liteJobConfiguration, jobEventRdbConfiguration, elasticJobListeners)).init();
        }

    }

    public abstract JobTypeConfiguration getJobTypeConfiguration(String jobName, JobCoreConfiguration jobCoreConfiguration);

    private ElasticJob registerElasticJob(String jobName, String strClass, JobType jobType) {
        switch (jobType) {
            case SIMPLE:
                return (ElasticJob) this.registerBean(jobName, strClass, SimpleJob.class);
            case DATAFLOW:
                return (ElasticJob) this.registerBean(jobName, strClass, DataflowJob.class);
            default:
                return null;
        }
    }

    private ZookeeperRegistryCenter getZookeeperRegistryCenter(String registryCenterRef) {
        if (StringUtils.isBlank(registryCenterRef)) {
            registryCenterRef = "elasticJobRegistryCenter";
        }

        if (!this.applicationContext.containsBean(registryCenterRef)) {
            throw new RuntimeException("not exist ZookeeperRegistryCenter [" + registryCenterRef + "] !");
        } else {
            return (ZookeeperRegistryCenter) this.applicationContext.getBean(registryCenterRef, ZookeeperRegistryCenter.class);
        }
    }

    private JobEventRdbConfiguration getJobEventRdbConfiguration(String eventTraceRdbDataSource) {
        if (StringUtils.isBlank(eventTraceRdbDataSource)) {
            return null;
        } else if (!this.applicationContext.containsBean(eventTraceRdbDataSource)) {
            throw new RuntimeException("not exist datasource [" + eventTraceRdbDataSource + "] !");
        } else {
            DataSource dataSource = (DataSource) this.applicationContext.getBean(eventTraceRdbDataSource);
            return new JobEventRdbConfiguration(dataSource);
        }
    }

    private LiteJobConfiguration getLiteJobConfiguration(JobTypeConfiguration jobTypeConfiguration, ElasticJobProperties.JobConfiguration jobConfiguration) {
        return LiteJobConfiguration.newBuilder((JobTypeConfiguration) Objects.requireNonNull(jobTypeConfiguration)).monitorExecution(jobConfiguration.isMonitorExecution()).monitorPort(jobConfiguration.getMonitorPort()).maxTimeDiffSeconds(jobConfiguration.getMaxTimeDiffSeconds()).jobShardingStrategyClass(jobConfiguration.getJobShardingStrategyClass()).reconcileIntervalMinutes(jobConfiguration.getReconcileIntervalMinutes()).disabled(jobConfiguration.isDisabled()).overwrite(jobConfiguration.isOverwrite()).build();
    }

    protected JobCoreConfiguration getJobCoreConfiguration(String jobName, ElasticJobProperties.JobConfiguration jobConfiguration) {
        JobCoreConfiguration.Builder builder = JobCoreConfiguration.newBuilder(jobName, jobConfiguration.getCron(), jobConfiguration.getShardingTotalCount()).shardingItemParameters(jobConfiguration.getShardingItemParameters()).jobParameter(jobConfiguration.getJobParameter()).failover(jobConfiguration.isFailover()).misfire(jobConfiguration.isMisfire()).description(jobConfiguration.getDescription());
        if (StringUtils.isNotBlank(jobConfiguration.getJobExceptionHandler())) {
            builder.jobProperties(JobProperties.JobPropertiesEnum.JOB_EXCEPTION_HANDLER.getKey(), jobConfiguration.getJobExceptionHandler());
        }

        if (StringUtils.isNotBlank(jobConfiguration.getExecutorServiceHandler())) {
            builder.jobProperties(JobProperties.JobPropertiesEnum.EXECUTOR_SERVICE_HANDLER.getKey(), jobConfiguration.getExecutorServiceHandler());
        }

        return builder.build();
    }

    private ElasticJobListener[] creatElasticJobListeners(ElasticJobProperties.JobConfiguration.Listener listener) {
        if (null == listener) {
            return null;
        } else {
            List<ElasticJobListener> elasticJobListeners = new ArrayList(2);
            ElasticJobListener elasticJobListener = (ElasticJobListener) this.registerBean(listener.getListenerClass(), listener.getListenerClass(), ElasticJobListener.class);
            if (null != elasticJobListener) {
                elasticJobListeners.add(elasticJobListener);
            }

            AbstractDistributeOnceElasticJobListener distributedListener = (AbstractDistributeOnceElasticJobListener) this.registerBean(listener.getDistributedListenerClass(), listener.getDistributedListenerClass(), AbstractDistributeOnceElasticJobListener.class, listener.getStartedTimeoutMilliseconds(), listener.getCompletedTimeoutMilliseconds());
            if (null != distributedListener) {
                elasticJobListeners.add(distributedListener);
            }

            if (CollectionUtils.isEmpty(elasticJobListeners)) {
                return null;
            } else {
                ElasticJobListener[] elasticJobListenerArray = new ElasticJobListener[elasticJobListeners.size()];

                for (int i = 0; i < elasticJobListeners.size(); ++i) {
                    elasticJobListenerArray[i] = (ElasticJobListener) elasticJobListeners.get(i);
                }

                return elasticJobListenerArray;
            }
        }
    }

    protected <T> T registerBean(String beanName, String strClass, Class<T> tClass, Object... constructorArgValue) {
        if (StringUtils.isBlank(strClass)) {
            return null;
        } else {
            if (StringUtils.isBlank(beanName)) {
                beanName = strClass;
            }

            if (this.applicationContext.containsBean(beanName)) {
                return this.applicationContext.getBean(beanName, tClass);
            } else {
                BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.rootBeanDefinition(strClass);
                beanDefinitionBuilder.setScope("prototype");
                Object[] var6 = constructorArgValue;
                int var7 = constructorArgValue.length;

                for (int var8 = 0; var8 < var7; ++var8) {
                    Object argValue = var6[var8];
                    beanDefinitionBuilder.addConstructorArgValue(argValue);
                }

                this.getDefaultListableBeanFactory().registerBeanDefinition(beanName, beanDefinitionBuilder.getBeanDefinition());
                return this.applicationContext.getBean(beanName, tClass);
            }
        }
    }

    private DefaultListableBeanFactory getDefaultListableBeanFactory() {
        return (DefaultListableBeanFactory) ((ConfigurableApplicationContext) this.applicationContext).getBeanFactory();
    }
}
