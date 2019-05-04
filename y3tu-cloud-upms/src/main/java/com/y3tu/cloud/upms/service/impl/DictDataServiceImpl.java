package com.y3tu.cloud.upms.service.impl;

import com.y3tu.cloud.upms.mapper.DictDataMapper;
import com.y3tu.cloud.upms.model.entity.DictData;
import com.y3tu.cloud.upms.service.DictDataService;
import com.y3tu.tool.web.base.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;


@Service("dictDataService")
public class DictDataServiceImpl extends BaseServiceImpl<DictDataMapper, DictData> implements DictDataService {

}
