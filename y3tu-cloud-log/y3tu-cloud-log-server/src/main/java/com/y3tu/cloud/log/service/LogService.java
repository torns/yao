package com.y3tu.cloud.log.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.y3tu.cloud.log.model.entity.Log;
import com.y3tu.cloud.log.model.query.LogQuery;

/**
 * <p>
 * 日志表 服务类
 * </p>
 */
public interface LogService extends IService<Log> {

    /**
     * 分页条件查询
     *
     * @param query
     * @return
     */
    LogQuery pageByQuery(LogQuery query);

}
