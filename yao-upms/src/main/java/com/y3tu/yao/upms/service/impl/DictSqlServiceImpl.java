package com.y3tu.yao.upms.service.impl;

import com.y3tu.yao.upms.mapper.DictSqlMapper;
import com.y3tu.yao.upms.model.entity.DictSql;
import com.y3tu.yao.upms.service.DictSqlService;
import com.y3tu.tool.web.base.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;


@Service("dictSqlService")
public class DictSqlServiceImpl extends BaseServiceImpl<DictSqlMapper, DictSql> implements DictSqlService {

}
