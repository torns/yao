package com.y3tu.cloud.upms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.y3tu.cloud.common.constants.CommonConstants;
import com.y3tu.cloud.common.enums.DataStatusEnum;
import com.y3tu.cloud.upms.model.entity.Department;
import com.y3tu.cloud.upms.model.entity.RoleDepartment;
import com.y3tu.cloud.upms.model.entity.User;
import com.y3tu.cloud.upms.service.DepartmentService;
import com.y3tu.cloud.upms.service.RoleDepartmentService;
import com.y3tu.cloud.upms.service.UserService;
import com.y3tu.tool.core.collection.CollectionUtil;
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
     * 获取部门树
     *
     * @return
     */
    @GetMapping("/tree")
    public R getDepartmentTree() {
        List<Department> list = departmentService.list(new QueryWrapper<Department>().eq("del_flag", DataStatusEnum.NORMAL));
        List<TreeNode<Department>> treeNodeList = list.stream().map(department -> {
            TreeNode<Department> treeNode = new TreeNode<>(department.getId(), department.getName(), department.getParentId(), department);
            return treeNode;
        }).collect(Collectors.toList());
        return R.success(TreeUtil.buildList(treeNodeList, "0"));
    }


    /**
     * 根据父id获取部门信息
     *
     * @param parentId
     * @param openDataFilter
     * @return
     */
    @GetMapping(value = "/getByParentId/{parentId}")
    public R getByParentId(@PathVariable String parentId, @RequestParam(required = false, defaultValue = "true") Boolean openDataFilter) {
        List<Department> list = CollectionUtil.newArrayList();

        list = departmentService.findByParentIdOrderBySortOrder(parentId, true);

        return R.success(polishList(list));
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
        //如果不是添加的一级 判断设置上级为父节点标识
        if (!CommonConstants.PARENT_ID.equals(department.getParentId())) {
            Department parent = departmentService.getById(department.getParentId());
            if (parent.getIsParent() == null || !parent.getIsParent()) {
                parent.setIsParent(true);
                departmentService.updateById(parent);
            }
        }
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

    /**
     * 部门名模糊搜索
     *
     * @param title          部门名
     * @param openDataFilter 是否开始数据权限过滤
     * @return
     */
    @MethodMapping(value = "/search", method = RequestMethod.GET)
    public R searchByTitle(@RequestParam String title, @RequestParam(required = false, defaultValue = "true") Boolean openDataFilter) {
        List<Department> list = departmentService.list(new QueryWrapper<Department>().like("title", title).orderByAsc("sort_order"));
        return R.success(polishList(list));
    }

    /**
     * 修改部门父级
     *
     * @param list
     * @return
     */
    private List<Department> polishList(List<Department> list) {
        list.forEach(item -> {
            if (!CommonConstants.PARENT_ID.equals(item.getParentId())) {
                Department parent = departmentService.getById(item.getParentId());
                item.setParentName(parent.getName());
            } else {
                item.setParentName("一级部门");
            }
        });
        return list;
    }
}
