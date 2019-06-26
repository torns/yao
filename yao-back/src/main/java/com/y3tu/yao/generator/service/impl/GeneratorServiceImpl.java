package com.y3tu.yao.generator.service.impl;

import com.y3tu.tool.web.base.service.impl.BaseServiceImpl;
import com.y3tu.yao.generator.mapper.GeneratorMapper;
import com.y3tu.yao.generator.model.entity.GeneratorConfig;
import com.y3tu.yao.generator.service.GeneratorService;
import org.springframework.stereotype.Service;

@Service("generatorService")
public class GeneratorServiceImpl extends BaseServiceImpl<GeneratorMapper,GeneratorConfig> implements GeneratorService {

}
