package com.y3tu.cloud.gen.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.y3tu.cloud.gen.mapper.TableInfoMapper;
import com.y3tu.cloud.gen.model.entity.TableInfo;
import com.y3tu.cloud.gen.model.query.TableInfoQuery;
import com.y3tu.cloud.gen.service.TableInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class TableInfoServiceImpl extends ServiceImpl<TableInfoMapper, TableInfo> implements TableInfoService {

    @Autowired
    private TableInfoMapper tableInfoMapper;

    @Override
    public TableInfoQuery pageByQuery(TableInfoQuery query) {
        tableInfoMapper.pageByQuery(query);
        return query;
    }

    @Override
    public TableInfo getOne(String tableName) {
        return tableInfoMapper.getOne(tableName);
    }
}
