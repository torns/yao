package com.y3tu.yao.generator.util;

import com.y3tu.tool.core.io.IoUtil;
import com.y3tu.tool.core.util.CharsetUtil;
import com.y3tu.tool.core.util.ObjectUtil;
import com.y3tu.tool.core.util.StrUtil;
import com.y3tu.tool.db.meta.DataTypeEnum;
import com.y3tu.tool.extra.template.Template;
import com.y3tu.tool.extra.template.TemplateConfig;
import com.y3tu.tool.extra.template.TemplateEngine;
import com.y3tu.tool.extra.template.TemplateUtil;
import com.y3tu.yao.generator.exception.GeneratorException;
import com.y3tu.yao.generator.model.entity.GeneratorConfig;
import com.y3tu.yao.generator.model.vo.ColumnInfo;
import com.y3tu.yao.generator.model.vo.TableInfo;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.StringWriter;
import java.time.LocalDate;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 代码生成工具类
 *
 * @author y3tu
 * @date 2019-06-27
 */
@Slf4j
public class GeneratorUtil {

    private static final String ENTITY_JAVA_VM = "Entity.java.vm";
    private static final String MAPPER_JAVA_VM = "Mapper.java.vm";
    private static final String SERVICE_JAVA_VM = "Service.java.vm";
    private static final String SERVICE_IMPL_JAVA_VM = "ServiceImpl.java.vm";
    private static final String CONTROLLER_JAVA_VM = "Controller.java.vm";
    private static final String DTO_JAVA_VM = "Dto.java.vm";
    private static final String MAPPER_XML_VM = "Mapper.xml.vm";
    private static final String INDEX_VUE_VM = "index.vue.vm";
    private static final String API_JS_VM = "api.js.vm";
    private static final String FORM_VUE_VM = "form.vue.vm";

    /**
     * 获取后端代码模板名称
     */
    private static List<String> getBackTemplateNames() {
        List<String> templateNames = new ArrayList<>();
        templateNames.add("Entity.java");
        templateNames.add("Dto.java");
        templateNames.add("Mapper.java");
        templateNames.add("Mapper.xml");
        templateNames.add("Service.java");
        templateNames.add("ServiceImpl.java");
        templateNames.add("Controller.java");
        return templateNames;
    }

    /**
     * 获取前端代码模板名称
     */
    private static List<String> getFrontTemplateNames() {
        List<String> templateNames = new ArrayList<>();
        templateNames.add("api.js");
        templateNames.add("index.vue");
        templateNames.add("form.vue");
        return templateNames;
    }

    /**
     * 生成代码
     *
     * @param tableInfo 表信息，包括字段数据
     * @param genConfig 生成代码的参数配置，如包路径，作者
     */
    public static void generatorCode(TableInfo tableInfo, GeneratorConfig genConfig, ZipOutputStream zip) {

        //是否有BigDecimal类型
        boolean hasBigDecimal = false;
        //是否有date类型
        boolean hasDate = false;
        //是否有查询字段
        boolean hasQuery = false;

        Map<String, Object> map = new HashMap();
        map.put("package", genConfig.getPack());
        map.put("moduleName", genConfig.getModuleName());
        map.put("author", genConfig.getAuthor());
        map.put("date", LocalDate.now().toString());
        map.put("tableName", tableInfo.getTableName());
        map.put("apiPrefix",genConfig.getApiPrefix());
        if(StrUtil.isNotEmpty(tableInfo.getComment())){
            map.put("comment",tableInfo.getComment());
        }else {
            map.put("comment",tableInfo.getTableName());
        }

        String className = StrUtil.toCapitalizeCamelCase(tableInfo.getTableName());
        String caseClassName = StrUtil.toCamelCase(tableInfo.getTableName());

        // 判断是否去除表前缀
        if (StrUtil.isNotEmpty(genConfig.getPrefix())) {
            className = StrUtil.toCapitalizeCamelCase(StrUtil.removePrefix(tableInfo.getTableName(), genConfig.getPrefix()));
            caseClassName = StrUtil.toCamelCase(StrUtil.removePrefix(tableInfo.getTableName(), genConfig.getPrefix()));
        }

        map.put("className", className);
        map.put("caseClassName", caseClassName);
        map.put("pathName",caseClassName);

        List<Map<String, Object>> columns = new ArrayList<>();
        List<Map<String, Object>> queryColumns = new ArrayList<>();
        for (ColumnInfo column : tableInfo.getColumns()) {
            Map<String, Object> listMap = new HashMap();

            if(StrUtil.isNotEmpty(column.getComment())){
                listMap.put("comment", column.getComment());
            }else {
                listMap.put("comment", column.getName());
            }

            //列的数据类型，转换成Java类型
            String attrType = DataTypeEnum.getJavaType(column.getTypeName());
            if (!hasBigDecimal && "BigDecimal".equals(attrType)) {
                hasBigDecimal = true;
            }
            if (!hasDate && "Date".equals(attrType)) {
                hasDate = true;
            }

            String caseColumnName = StrUtil.toCamelCase(column.getName());
            String capitalColumnName = StrUtil.toCapitalizeCamelCase(column.getName());

            listMap.put("columnType", attrType);
            listMap.put("columnName", column.getName());
            listMap.put("isNullable", column.isNullable());
            listMap.put("columnShow", column.isColumnShow());
            listMap.put("caseColumnName", caseColumnName);
            listMap.put("capitalColumnName", capitalColumnName);

            //判断是否有查询，如有则把查询的字段set进columnQuery
            if (ObjectUtil.isNotNull(column.getColumnQuery())) {
                listMap.put("columnQuery", column.getColumnQuery());
                hasQuery = true;
                queryColumns.add(listMap);
            }

            //判断是否为主键
            if (column.getName().equals(tableInfo.getPk().getName())) {
                map.put("pk", listMap);
            }
            columns.add(listMap);
        }

        map.put("hasBigDecimal", hasBigDecimal);
        map.put("hasDate", hasDate);
        map.put("hasQuery", hasQuery);
        map.put("columns", columns);
        map.put("queryColumns", queryColumns);


        //创建模板引擎
        TemplateEngine engine = TemplateUtil.createEngine(new TemplateConfig(null, TemplateConfig.ResourceMode.CLASSPATH));

        //生成后端代码
        List<String> templates = getBackTemplateNames();
        for (String templateName : templates) {
            StringWriter sw = new StringWriter();
            Template template = engine.getTemplate("generator/back/" + templateName + ".vm");
            template.render(map, sw);
            buildZip(getBackFilePath(templateName, genConfig, className), className, sw, zip);
        }

        //生成前端代码
        templates = getFrontTemplateNames();
        for (String templateName : templates) {
            StringWriter sw = new StringWriter();
            Template template = engine.getTemplate("generator/front/" + templateName + ".vm");
            template.render(map, sw);
            buildZip(getFrontFilePath(templateName, genConfig, caseClassName), className, sw, zip);
        }
    }

    private static void buildZip(String path, String className, StringWriter sw, ZipOutputStream zip) {
        try {
            //添加到zip
            zip.putNextEntry(new ZipEntry(Objects.requireNonNull(path)));
            IoUtil.write(zip, CharsetUtil.UTF_8, false, sw.toString());
            IoUtil.close(sw);
            zip.closeEntry();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new GeneratorException("渲染模板失败，表名：" + className, e);
        }
    }


    /**
     * 定义后端文件路径和名称
     */
    private static String getBackFilePath(String templateName, GeneratorConfig genConfig, String className) {

        String packagePath = "back" + File.separator + "src" + File.separator + "main" + File.separator + "java" + File.separator;
        if (StrUtil.isNotBlank(genConfig.getPack())) {
            packagePath += genConfig.getPack().replace(".", File.separator) + File.separator + genConfig.getModuleName() + File.separator;
        }

        if (ENTITY_JAVA_VM.contains(templateName)) {
            return packagePath + "model" + File.separator + "entity" + File.separator + className + ".java";
        }

        if (DTO_JAVA_VM.contains(templateName)) {
            return packagePath + "model" + File.separator + "dto" + File.separator + className + "DTO.java";
        }

        if (MAPPER_JAVA_VM.contains(templateName)) {
            return packagePath + "mapper" + File.separator + className + "Mapper.java";
        }

        if (SERVICE_JAVA_VM.contains(templateName)) {
            return packagePath + "service" + File.separator + className + "Service.java";
        }

        if (SERVICE_IMPL_JAVA_VM.contains(templateName)) {
            return packagePath + "service" + File.separator + "impl" + File.separator + className + "ServiceImpl.java";
        }

        if (CONTROLLER_JAVA_VM.contains(templateName)) {
            return packagePath + "controller" + File.separator + className + "Controller.java";
        }

        if (MAPPER_XML_VM.contains(templateName)) {
            return "back" + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "mapper" + File.separator + genConfig.getModuleName() + File.separator + className + "Mapper.xml";
        }

        return null;
    }

    /**
     * 定义前端文件路径和名称
     */
    private static String getFrontFilePath(String templateName, GeneratorConfig genConfig, String className) {

        if (INDEX_VUE_VM.contains(templateName)) {
            return "front" + File.separator + "src" + File.separator + "views" +
                    File.separator + genConfig.getModuleName() + File.separator + className + File.separator + "index.vue";
        }
        if (FORM_VUE_VM.contains(templateName)) {
            return "front" + File.separator + "src" + File.separator + "views" +
                    File.separator + genConfig.getModuleName() + File.separator + className + File.separator + "form.vue";
        }
        if (API_JS_VM.contains(templateName)) {
            return "front" + File.separator + "src" + File.separator + "api" + File.separator + className + ".js";
        }
        return null;
    }
}
