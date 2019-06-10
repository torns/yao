package com.y3tu.yao.upms.service;

import com.y3tu.yao.upms.emums.DictType;
import com.y3tu.yao.upms.model.entity.Dict;
import com.y3tu.tool.web.base.service.BaseService;

import java.util.List;
import java.util.Map;

/**
 * ${comments}
 *
 * @author y3tu
 * @date 2018-12-14 14:43:24
 */
public interface DictService extends BaseService<Dict> {
    /**
     * 根据字典类型，字典code，字典值查询字典
     *
     * @param dictType
     * @param code
     * @param value
     * @return
     */
    Map getDictByCodeAndValue(DictType dictType, String code, Object value);

    /**
     * 根据sqlCode获取字典数据
     *
     * @param sqlCode
     * @return
     */
    List<Map> getListBySqlCode(String sqlCode);
}

