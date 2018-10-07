package com.y3tu.cloud.daemon.listener;

import com.dangdang.ddframe.job.executor.ShardingContexts;

/**
 * @author lengleng
 * @date 2018/7/24
 * 任务监听器
 */
public class ElasticJobListener implements com.dangdang.ddframe.job.lite.api.listener.ElasticJobListener {

	@Override
	public void beforeJobExecuted(ShardingContexts shardingContexts) {
		System.out.println(shardingContexts.getJobName() + " | MyElasticJobListener beforeJobExecuted");
	}

	@Override
	public void afterJobExecuted(ShardingContexts shardingContexts) {
		System.out.println(shardingContexts.getJobName() + " | MyElasticJobListener afterJobExecuted");
	}
}
