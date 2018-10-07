package com.y3tu.cloud.daemon.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.y3tu.cloud.daemon.entity.ExecutionLog;
import com.y3tu.cloud.daemon.service.ExecutionLogService;
import com.y3tu.tool.web.base.pojo.Query;
import com.y3tu.tool.web.base.pojo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


/**
 * @author lengleng
 * @date 2018-08-03 22:15:56
 */
@RestController
@RequestMapping("/executionlog")
public class ExecutionLogController {
    @Autowired
    private ExecutionLogService executionLogService;


    /**
     * 列表
     *
     * @param params
     * @return
     */
    @GetMapping("/page")
    public Page page(@RequestParam Map<String, Object> params) {
        return executionLogService.selectPage(new Query(params).getPage(), new EntityWrapper<>());
    }


    /**
     * 信息
     *
     * @param id
     * @return R
     */
    @GetMapping("/{id}")
    public R info(@PathVariable("id") String id) {
        ExecutionLog executionLog = executionLogService.selectById(id);

        return R.ok(executionLog);
    }

    /**
     * 保存
     *
     * @param executionLog
     * @return R
     */
    @PostMapping("/save")
    public R save(@RequestBody ExecutionLog executionLog) {
        executionLogService.insert(executionLog);

        return R.ok(Boolean.TRUE);
    }

    /**
     * 修改
     *
     * @param executionLog
     * @return R
     */
    @PutMapping("/update")
    public R update(@RequestBody ExecutionLog executionLog) {
        executionLogService.updateById(executionLog);

        return R.ok(Boolean.TRUE);
    }

    /**
     * 删除
     *
     * @param ids
     * @return R
     */
    @DeleteMapping("/delete")
    public R delete(@RequestBody String[] ids) {
        executionLogService.deleteBatchIds(Arrays.asList(ids));

        return R.ok(Boolean.TRUE);
    }

}
