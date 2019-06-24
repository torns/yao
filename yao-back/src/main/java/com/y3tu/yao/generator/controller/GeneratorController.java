package com.y3tu.yao.generator.controller;

import com.y3tu.tool.core.pojo.R;
import org.springframework.web.bind.annotation.*;

/**
 * 代码生成器Controller
 *
 * @author y3tu
 * @date 2019-06-24
 */
@RestController
@RequestMapping("generator")
public class GeneratorController {

    /**
     * 查询数据库表的分页数据
     *
     * @return 表的分页数据
     */
    @PostMapping("/pageTable")
    public R pageTable() {

        return R.success();
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
