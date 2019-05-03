package com.y3tu.cloud.gen.service.impl;

import com.y3tu.cloud.gen.mapper.ColumnInfoMapper;
import com.y3tu.cloud.gen.mapper.TableInfoMapper;
import com.y3tu.cloud.gen.model.dto.BuildConfigDTO;
import com.y3tu.cloud.gen.model.entity.ColumnInfo;
import com.y3tu.cloud.gen.model.entity.TableInfo;
import com.y3tu.cloud.gen.service.SysGenService;
import com.y3tu.cloud.gen.util.GenUtil;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.zip.ZipOutputStream;


@Service
public class SysGenServiceImpl implements SysGenService {

    @Autowired
    private TableInfoMapper tableInfoMapper;

    @Autowired
    private ColumnInfoMapper columnInfoMapper;

    @Override
    public byte[] genCodeByTableName(BuildConfigDTO buildConfigDTO) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);

        for (String tableName : buildConfigDTO.getTableName()) {
            //查询表信息
            TableInfo table = tableInfoMapper.getOne(tableName);
            //查询列信息
            List<ColumnInfo> columns = columnInfoMapper.listByTableName(tableName);
            //生成代码
            GenUtil.generatorCode(buildConfigDTO, table, columns, zip);
        }
        IOUtils.closeQuietly(zip);
        return outputStream.toByteArray();

    }
}
