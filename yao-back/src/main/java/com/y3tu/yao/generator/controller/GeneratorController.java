package com.y3tu.yao.generator.controller;

import com.y3tu.tool.core.pojo.R;
import com.y3tu.tool.core.util.StrUtil;
import com.y3tu.tool.db.meta.MetaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

/**
 * 代码生成器Controller
 *
 * @author y3tu
 * @date 2019-06-24
 */
@RestController
@RequestMapping("generator")
public class GeneratorController {

    @Autowired
    DataSource dataSource;
    /**
     * 查询数据库表的分页数据
     *
     * @return 表的分页数据
     */
    @PostMapping("/pageTable")
    public R pageTable(@RequestBody String dataSourceId) {
        List<String> tableList = new ArrayList<>();
        if(StrUtil.isEmpty(dataSourceId)){
            //如果没有选定数据源 默认查下自身服务所连接的数据源
            tableList = MetaUtil.getTables(dataSource);
        }else {
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
    @GetMapping("/tableColumn/{tableName}")
    public R tableColumn(@PathVariable("tableName") String tableName) {
        return R.success();
    }


}
