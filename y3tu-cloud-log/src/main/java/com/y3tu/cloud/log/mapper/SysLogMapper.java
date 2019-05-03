package com.y3tu.cloud.log.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.y3tu.cloud.log.model.entity.SysLog;
import com.y3tu.cloud.log.model.query.SysLogQuery;


public interface SysLogMapper extends BaseMapper<SysLog> {

    /**
     * 日志信息分页查询
     *
     * @param query
     */
    IPage<SysLog> pageByQuery(SysLogQuery query);
}
