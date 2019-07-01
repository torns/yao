package com.y3tu.yao.generator.util;

import com.y3tu.tool.core.util.StrUtil;
import com.y3tu.yao.generator.model.entity.GeneratorConfig;
import com.y3tu.yao.generator.model.vo.ColumnInfo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 代码生成工具类
 *
 * @author y3tu
 * @date 2019-06-27
 */
public class GeneratorUtil {

    private static final String TIMESTAMP = "Timestamp";

    private static final String BIGDECIMAL = "BigDecimal";

    private static final String PK = "PRI";

    /**
     * 生成代码
     *
     * @param columnInfos 字段数据
     * @param genConfig   生成代码的参数配置，如包路径，作者
     * @param tableName   表名
     */
    public static void generatorCode(List<ColumnInfo> columnInfos, GeneratorConfig genConfig, String tableName) {
        Map<String, Object> map = new HashMap();
        map.put("package", genConfig.getPack());
        map.put("moduleName", genConfig.getModuleName());
        map.put("author", genConfig.getAuthor());
        map.put("date", LocalDate.now().toString());
        map.put("tableName", tableName);
        String className = StrUtil.toCapitalizeCamelCase(tableName);
        map.put("className", className);
        map.put("changeClassName", StrUtil.toCamelCase(tableName));
        map.put("hasTimestamp", false);
        map.put("hasBigDecimal", false);
        map.put("hasQuery", false);

        List<Map<String, Object>> columns = new ArrayList<>();
        List<Map<String, Object>> queryColumns = new ArrayList<>();
        for (ColumnInfo column : columnInfos) {
            Map<String,Object> listMap = new HashMap();
            listMap.put("comment",column.getComment());
            listMap.put("columnKey",column.getColumnKey());


            columns.add(listMap);
        }
    }

}
