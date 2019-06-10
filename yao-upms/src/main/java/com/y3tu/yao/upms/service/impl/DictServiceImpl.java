package com.y3tu.yao.upms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.y3tu.yao.upms.emums.DictType;
import com.y3tu.yao.upms.mapper.DictMapper;
import com.y3tu.yao.upms.model.entity.Dict;
import com.y3tu.yao.upms.model.entity.DictData;
import com.y3tu.yao.upms.model.entity.DictSql;
import com.y3tu.yao.upms.service.DictDataService;
import com.y3tu.yao.upms.service.DictService;
import com.y3tu.yao.upms.service.DictSqlService;
import com.y3tu.tool.core.bean.BeanUtil;
import com.y3tu.tool.core.exception.BusinessException;
import com.y3tu.tool.db.DbUtil;
import com.y3tu.tool.web.base.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;


@Service("dictService")
public class DictServiceImpl extends BaseServiceImpl<DictMapper, Dict> implements DictService {

    @Autowired
    DictDataService dictDataService;
    @Autowired
    DictSqlService dictSqlService;
    @Autowired
    DataSource dataSource;

    @Override
    public Map getDictByCodeAndValue(DictType dictType, String code, Object value) {
        if (dictType == DictType.DICT) {

            Dict dict = this.getOne(new QueryWrapper<Dict>().eq("code", code));
            List<DictData> dictDataList = dictDataService.list(new QueryWrapper<DictData>().eq("dict_id", dict.getId()));
            for (DictData dictData : dictDataList) {
                if (value.toString().equals(dictData.getValue())) {
                    return BeanUtil.beanToMap(dictData);
                }
            }
        } else {
            List<Map> mapList = getListBySqlCode(code);
            for (Map map : mapList) {
                if (value.equals(map.get("value").toString())) {
                    return map;
                }
            }
        }
        return null;
    }

    @Override
    public List<Map> getListBySqlCode(String sqlCode) {
        List<Map> list = null;
        try {
            DictSql dictSql = dictSqlService.getOne(new QueryWrapper<DictSql>().eq("sql_code", sqlCode));
            list = DbUtil.use(dataSource).query(dictSql.getSqlText(), Map.class);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new BusinessException(e);
        }
        return list;
    }
}
