package com.y3tu.cloud.upms.controller;

import com.y3tu.cloud.upms.model.dto.DeptTree;
import com.y3tu.cloud.upms.model.entity.SysDept;
import com.y3tu.cloud.upms.service.SysDeptService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.y3tu.tool.web.base.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;


/**
 * <p>
 * 部门管理 前端控制器
 * </p>
 *
 * @author liuht
 * @since 2018-01-20
 */
@RestController
@RequestMapping("/dept")
public class DeptController extends BaseController {
    @Autowired
    private SysDeptService sysDeptService;

    /**
     * 通过ID查询
     *
     * @param id ID
     * @return SysDept
     */
    @GetMapping("/{id}")
    public SysDept get(@PathVariable Integer id) {
        return sysDeptService.selectById(id);
    }


    /**
     * 返回树形菜单集合
     *
     * @return 树形菜单
     */
    @GetMapping(value = "/tree")
    public List<DeptTree> getTree() {
        SysDept condition = new SysDept();
        condition.setDelFlag("0");
        return sysDeptService.selectListTree(new EntityWrapper<>(condition));
    }

    /**
     * 添加
     *
     * @param sysDept 实体
     * @return success/false
     */
    @PostMapping
    public Boolean add(@RequestBody SysDept sysDept) {
        return sysDeptService.insertDept(sysDept);
    }

    /**
     * 删除
     *
     * @param id ID
     * @return success/false
     */
    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable Integer id) {
        return sysDeptService.deleteDeptById(id);
    }

    /**
     * 编辑
     *
     * @param sysDept 实体
     * @return success/false
     */
    @PutMapping
    public Boolean edit(@RequestBody SysDept sysDept) {
        sysDept.setUpdateTime(new Date());
        return sysDeptService.updateDeptById(sysDept);
    }
}
