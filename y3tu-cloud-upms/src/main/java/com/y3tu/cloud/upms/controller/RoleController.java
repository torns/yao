package com.y3tu.cloud.upms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.y3tu.cloud.upms.model.entity.Role;
import com.y3tu.cloud.upms.model.entity.RoleDepartment;
import com.y3tu.cloud.upms.model.entity.RolePermission;
import com.y3tu.cloud.upms.model.entity.UserRole;
import com.y3tu.cloud.upms.service.RoleDepartmentService;
import com.y3tu.cloud.upms.service.RolePermissionService;
import com.y3tu.cloud.upms.service.RoleService;
import com.y3tu.cloud.upms.service.UserRoleService;
import com.y3tu.tool.core.pojo.R;
import com.y3tu.tool.web.annotation.MethodMapping;
import com.y3tu.tool.web.base.controller.BaseController;
import com.y3tu.tool.web.base.pojo.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 角色表 前端控制器
 * </p>
 *
 * @author y3tu
 * @date 2018-08-05
 */
@RestController
@RequestMapping("/role")
public class RoleController extends BaseController<RoleService, Role> {

    @Autowired
    RoleService roleService;
    @Autowired
    private RolePermissionService rolePermissionService;
    @Autowired
    private RoleDepartmentService roleDepartmentService;
    @Autowired
    private UserRoleService userRoleService;

    @ApiOperation(value = "分页获取角色")
    @Override
    @MethodMapping(method = RequestMethod.POST)
    public R getByPage(@RequestParam Map<String, Object> params) {
        PageInfo<Role> pageInfo = service.queryPage(PageInfo.mapToPageInfo(params), params);
        for (Role role : pageInfo.getList()) {
            //角色拥有权限
            List<RolePermission> permissions = rolePermissionService.list(new QueryWrapper<RolePermission>().eq("role_id", role.getId()));
            //角色所属部门
            List<RoleDepartment> departments = roleDepartmentService.list(new QueryWrapper<RoleDepartment>().eq("role_id", role.getId()));
            role.setPermissions(permissions);
            role.setDepartments(departments);
        }
        return R.success(pageInfo);
    }

    @RequestMapping(value = "/setDefault", method = RequestMethod.POST)
    @ApiOperation(value = "设置或取消默认角色")
    public R setDefault(@RequestParam String id, @RequestParam Boolean isDefault) {

        Role role = roleService.getById(id);
        if (role == null) {
            return R.warn("角色不存在");
        }
        role.setDefaultRole(isDefault);
        roleService.updateById(role);
        return R.success("设置成功");
    }

    @RequestMapping(value = "/editRolePerm", method = RequestMethod.POST)
    @ApiOperation(value = "编辑角色分配菜单权限")
    public R editRolePerm(@RequestParam String roleId,
                          @RequestParam(required = false) String[] permIds) {

        //删除其关联权限
        rolePermissionService.remove(new QueryWrapper<RolePermission>().eq("role_id", roleId));
        //分配新权限
        for (String permId : permIds) {
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(roleId);
            rolePermission.setPermissionId(permId);
            rolePermissionService.save(rolePermission);
        }
        return R.success();
    }

    @RequestMapping(value = "/editRoleDep", method = RequestMethod.POST)
    @ApiOperation(value = "编辑角色分配数据权限")
    public R editRoleDep(@RequestParam String roleId,
                         @RequestParam Integer dataType,
                         @RequestParam(required = false) String[] depIds) {

        Role r = roleService.getById(roleId);
        r.setDataType(dataType);
        roleService.updateById(r);
        // 删除其关联数据权限
        roleDepartmentService.remove(new QueryWrapper<RoleDepartment>().eq("role_id", roleId));
        // 分配新数据权限
        for (String depId : depIds) {
            RoleDepartment roleDepartment = new RoleDepartment();
            roleDepartment.setRoleId(roleId);
            roleDepartment.setDepartmentId(depId);
            roleDepartmentService.save(roleDepartment);
        }
        return R.success();
    }

    @ApiOperation(value = "批量通过ids删除")
    @DeleteMapping(value = "/delByIds/{ids}")
    @Override
    public R delByIds(@PathVariable String[] ids) {
        for (String id : ids) {
            List<UserRole> list = userRoleService.list(new QueryWrapper<UserRole>().eq("role_id", id));
            if (list != null && list.size() > 0) {
                return R.warn("删除失败，包含正被用户使用关联的角色");
            }
        }

        for (String id : ids) {
            roleService.removeById(id);
            //删除关联菜单权限
            rolePermissionService.remove(new QueryWrapper<RolePermission>().eq("role_id", id));
            //删除关联部门数据
            roleDepartmentService.remove(new QueryWrapper<RoleDepartment>().eq("role_id", id));
        }
        return super.delByIds(ids);
    }
}
