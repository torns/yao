package com.y3tu.cloud.daemon.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.y3tu.cloud.daemon.entity.StatusTraceLog;
import com.y3tu.cloud.daemon.service.StatusTraceLogService;
import com.y3tu.tool.web.base.pojo.Query;
import com.y3tu.tool.web.base.pojo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


/**
 * @author lengleng
 * @date 2018-08-03 22:15:45
 */
@RestController
@RequestMapping("/statustracelog")
public class StatusTraceLogController {
    @Autowired
    private StatusTraceLogService statusTraceLogService;


    /**
     * 列表
     *
     * @param params
     * @return
     */
    @GetMapping("/page")
    public Page page(@RequestParam Map<String, Object> params) {
        return statusTraceLogService.selectPage(new Query(params).getPage(), new EntityWrapper<>());
    }


    /**
     * 信息
     *
     * @param id
     * @return R
     */
    @GetMapping("/{id}")
    public R info(@PathVariable("id") String id) {
        StatusTraceLog statusTraceLog = statusTraceLogService.selectById(id);

        return R.ok(statusTraceLog);
    }

    /**
     * 保存
     *
     * @param statusTraceLog
     * @return R
     */
    @PostMapping("/save")
    public R save(@RequestBody StatusTraceLog statusTraceLog) {
        statusTraceLogService.insert(statusTraceLog);

        return R.ok(Boolean.TRUE);
    }

    /**
     * 修改
     *
     * @param statusTraceLog
     * @return R
     */
    @PutMapping("/update")
    public R update(@RequestBody StatusTraceLog statusTraceLog) {
        statusTraceLogService.updateById(statusTraceLog);

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
        statusTraceLogService.deleteBatchIds(Arrays.asList(ids));

        return R.ok(Boolean.TRUE);
    }

}
