package com.y3tu.cloud.upms.service.impl;

import com.y3tu.cloud.upms.mapper.DictSqlMapper;
import com.y3tu.cloud.upms.model.entity.DictSql;
import com.y3tu.cloud.upms.service.DictSqlService;
import com.y3tu.tool.web.base.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;


@Service("dictSqlService")
public class DictSqlServiceImpl extends BaseServiceImpl<DictSqlMapper, DictSql> implements DictSqlService {

}
