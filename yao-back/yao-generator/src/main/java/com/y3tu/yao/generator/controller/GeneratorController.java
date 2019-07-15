package com.y3tu.yao.generator.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.y3tu.tool.core.io.IoUtil;
import com.y3tu.tool.core.pojo.R;
import com.y3tu.tool.core.util.StrUtil;
import com.y3tu.tool.db.meta.MetaUtil;
import com.y3tu.tool.db.meta.Table;
import com.y3tu.yao.generator.exception.GeneratorException;
import com.y3tu.yao.generator.model.entity.GeneratorConfig;
import com.y3tu.yao.generator.model.vo.ColumnInfo;
import com.y3tu.yao.generator.model.vo.TableInfo;
import com.y3tu.yao.generator.service.GeneratorService;
import com.y3tu.yao.generator.util.GeneratorUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipOutputStream;

/**
 * 代码生成器Controller
 *
 * @author y3tu
 * @date 2019-06-24
 */
@RestController
@RequestMapping("generator")
@Slf4j
public class GeneratorController {

    @Autowired
    DataSource dataSource;

    @Autowired
    GeneratorService generatorService;

    /**
     * 查询数据库所有表的信息
     *
     * @return 表的分页数据
     */
    @PostMapping("/getTables")
    public R getTables(@RequestBody(required = false) String dataSourceId) {
        List<Map> tableList = new ArrayList<>();
        if (StrUtil.isEmpty(dataSourceId)) {
            //如果没有选定数据源 默认查下自身服务所连接的数据源
            tableList = MetaUtil.getTables(dataSource);
        } else {
            //如果有选定数据源，则查下选定数据源下的所有表数据
            //todo
        }
        return R.success(tableList);
    }

    /**
     * 根据表名获取表的字段信息
     *
     * @param tableName
     * @return
     */
    @GetMapping("/getTable/{tableName}")
    public R tableColumn(@PathVariable("tableName") String tableName) {
        Table table = MetaUtil.getTableMeta(dataSource, tableName);
        return R.success(table);
    }

    /**
     * 获取代码生成配置信息
     *
     * @return
     */
    @GetMapping("/getGeneratorConfig")
    public R getGeneratorConfig() {
        GeneratorConfig generatorConfig = generatorService.getOne(new QueryWrapper<>());
        return R.success(generatorConfig);
    }

    /**
     * 更新代码生成配置
     *
     * @param generatorConfig
     * @return
     */
    @PostMapping("updateGeneratorConfig")
    public R updateGeneratorConfig(@RequestBody GeneratorConfig generatorConfig) {
        generatorService.updateById(generatorConfig);
        return R.success();
    }

    /**
     * 生成代码
     *
     * @param columnInfos
     * @return
     */
    @PostMapping(value = "/build")
    public void build(@RequestBody List<ColumnInfo> columnInfos, @RequestParam String tableName, HttpServletResponse response) {

        Table table = MetaUtil.getTableMeta(dataSource, tableName);
        TableInfo tableInfo = new TableInfo();
        tableInfo.setTableName(tableName);

        Set<String> pkNames = table.getPkNames();
        for (ColumnInfo columnInfo : columnInfos) {
            for (String pkName : pkNames) {
                if (columnInfo.getName().equals(pkName)) {
                    tableInfo.setPk(columnInfo);
                }
            }
        }

        tableInfo.setComment(table.getRemarks());
        tableInfo.setColumns(columnInfos);

        GeneratorConfig generatorConfig = generatorService.getOne(new QueryWrapper<>());

        //文件生成和下载设置
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ZipOutputStream zip = new ZipOutputStream(outputStream);
            GeneratorUtil.generatorCode(tableInfo, generatorConfig, zip);
            IoUtil.close(zip);
            byte[] data = outputStream.toByteArray();

            response.reset();
            response.setHeader("Content-Disposition", String.format("attachment; filename=%s.zip", tableName));
            response.addHeader("Content-Length", "" + data.length);
            response.setContentType("application/octet-stream; charset=UTF-8");

            IoUtil.write(response.getOutputStream(), Boolean.TRUE, data);

        } catch (Exception e) {
            log.error(e.getMessage());
            throw new GeneratorException("代码生成失败!");
        }

    }


}
