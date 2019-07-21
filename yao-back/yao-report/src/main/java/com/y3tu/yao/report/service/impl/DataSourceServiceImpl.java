package com.y3tu.yao.report.service.impl;

import org.springframework.stereotype.Service;

import com.y3tu.yao.report.mapper.DataSourceMapper;
import com.y3tu.yao.report.model.entity.DataSource;
import com.y3tu.yao.report.service.DataSourceService;
import com.y3tu.tool.web.base.service.impl.BaseServiceImpl;

/**
 * data_source service实现类
 *
 * @author y3tu
 * @date 2019-07-17
 */
@Service("DataSourceService")
public class DataSourceServiceImpl extends BaseServiceImpl<DataSourceMapper, DataSource> implements DataSourceService {

}
