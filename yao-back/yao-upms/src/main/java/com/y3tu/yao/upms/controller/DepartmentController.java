package com.y3tu.yao.upms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.y3tu.yao.common.enums.DataStatusEnum;
import com.y3tu.yao.upms.model.entity.Department;
import com.y3tu.yao.upms.model.entity.RoleDepartment;
import com.y3tu.yao.upms.model.entity.User;
import com.y3tu.yao.upms.service.DepartmentService;
import com.y3tu.yao.upms.service.RoleDepartmentService;
import com.y3tu.yao.upms.service.UserService;
import com.y3tu.tool.core.pojo.R;
import com.y3tu.tool.core.pojo.TreeNode;
import com.y3tu.tool.core.util.TreeUtil;
import com.y3tu.tool.web.annotation.MethodMapping;
import com.y3tu.tool.web.base.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


/**
 * 部门Controller
 *
 * @author y3tu
 */
@RestController
@RequestMapping("/department")
public class DepartmentController extends BaseController<DepartmentService, Department> {

    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private UserService userService;
    @Autowired
    private RoleDepartmentService roleDepartmentService;

    /**
     * 部门树根节点id
     */
    private final static String TREE_ROOT = "0";

    /**
     * 获取部门树
     *
     * @return
     */
    @GetMapping("/tree")
    public R getDepartmentTree() {
        List<Department> list = departmentService.list(
                new QueryWrapper<Department>().eq("del_flag", DataStatusEnum.NORMAL).orderByAsc("sort"));
        List<TreeNode<Department>> treeNodeList = list.stream().map(department -> {
            TreeNode<Department> treeNode = new TreeNode<>(department.getId(), department.getName(), department.getParentId(), department);
            return treeNode;
        }).collect(Collectors.toList());
        return R.success(TreeUtil.buildList(treeNodeList, TREE_ROOT));
    }


    /**
     * 新增部门
     *
     * @param department
     * @return
     */
    @MethodMapping(method = RequestMethod.POST)
    @Override
    public R save(@RequestBody Department department) {
        departmentService.save(department);
        return R.success();
    }

    /**
     * 修改部门
     *
     * @param department
     * @return
     */
    @MethodMapping(method = RequestMethod.POST)
    @Override
    public R update(@RequestBody Department department) {
        departmentService.updateById(department);
        return R.success();
    }


    /**
     * 批量删除部门
     *
     * @param ids 部门id集合
     * @return
     */
    @DeleteMapping(value = "/delByIds/{ids}")
    @Override
    public R delByIds(@PathVariable String[] ids) {
        for (String id : ids) {
            List<User> list = userService.list(new QueryWrapper<User>().eq("department_id", id));
            if (list != null && list.size() > 0) {
                return R.warn("删除失败，包含正被用户使用关联的部门!" + id);
            }
        }
        for (String id : ids) {
            departmentService.removeById(id);
            // 删除关联数据权限
            roleDepartmentService.remove(new QueryWrapper<RoleDepartment>().eq("department_id", id));
        }
        return R.success();
    }

}
