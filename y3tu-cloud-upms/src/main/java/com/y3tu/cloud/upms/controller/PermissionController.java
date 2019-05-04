package com.y3tu.cloud.upms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.y3tu.cloud.common.constants.CommonConstants;
import com.y3tu.cloud.upms.model.dto.UserDTO;
import com.y3tu.cloud.upms.model.entity.Permission;
import com.y3tu.cloud.upms.model.entity.RolePermission;
import com.y3tu.cloud.upms.service.PermissionService;
import com.y3tu.cloud.upms.service.RolePermissionService;
import com.y3tu.tool.core.pojo.R;
import com.y3tu.tool.web.base.controller.BaseController;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 权限表 前端控制器
 * </p>
 *
 * @author y3tu
 * @date 2018-08-05
 */
@RestController
@RequestMapping("/permission")
public class PermissionController extends BaseController<PermissionService, Permission> {
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private RolePermissionService rolePermissionService;
    @Autowired
    private StringRedisTemplate redisTemplate;

    @RequestMapping(value = "/getMenuList", method = RequestMethod.GET)
    @ApiOperation(value = "获取用户页面菜单数据")
    public R getAllMenuList(@RequestBody UserDTO userDTO) {

        List<Permission> list = new ArrayList<>();

        // 用户所有权限 已排序去重
        list = permissionService.findByUserId(userDTO.getId());

        List<Permission> menuList = new ArrayList<>();
        //筛选一级页面
        for (Permission p : list) {
            if (CommonConstants.PERMISSION_PAGE.equals(p.getType()) && CommonConstants.LEVEL_ONE.equals(p.getLevel())) {
                menuList.add(p);
            }
        }
        //筛选二级页面
        List<Permission> secondMenuList = new ArrayList<>();
        for (Permission p : list) {
            if (CommonConstants.PERMISSION_PAGE.equals(p.getType()) && CommonConstants.LEVEL_TWO.equals(p.getLevel())) {
                secondMenuList.add(p);
            }
        }
        //筛选二级页面拥有的按钮权限
        List<Permission> buttonPermissions = new ArrayList<>();
        for (Permission p : list) {
            if (CommonConstants.PERMISSION_OPERATION.equals(p.getType()) && CommonConstants.LEVEL_THREE.equals(p.getLevel())) {
                buttonPermissions.add(p);
            }
        }

        //匹配二级页面拥有权限
        for (Permission p : secondMenuList) {
            List<String> permTypes = new ArrayList<>();
            List<Permission> buttonPermissionList = new ArrayList<>();
            for (Permission pe : buttonPermissions) {
                if (p.getId().equals(pe.getParentId())) {
                    permTypes.add(pe.getButtonType());
                    buttonPermissionList.add(pe);
                }
            }
            p.setPermTypes(permTypes);
            p.setChildren(buttonPermissionList);
        }
        //匹配一级页面拥有二级页面
        for (Permission p : menuList) {
            List<Permission> secondMenu = new ArrayList<>();
            for (Permission pe : secondMenuList) {
                if (p.getId().equals(pe.getParentId())) {
                    secondMenu.add(pe);
                }
            }
            p.setChildren(secondMenu);
        }

        return R.success(menuList);
    }

    /**
     * 获取权限菜单树
     *
     * @return
     */
    @Override
    @GetMapping(value = "/getAll")
    public R getAll() {
        //一级
        List<Permission> list = permissionService.list(new QueryWrapper<Permission>().eq("level", CommonConstants.LEVEL_ONE).orderByAsc("sort_order"));
        //二级
        for (Permission p1 : list) {
            List<Permission> children1 = permissionService.list(new QueryWrapper<Permission>().eq("parent_id", p1.getId()).orderByAsc("sort_order"));
            p1.setChildren(children1);
            //三级
            for (Permission p2 : children1) {
                List<Permission> children2 = permissionService.list(new QueryWrapper<Permission>().eq("parent_id", p2.getId()).orderByAsc("sort_order"));
                p2.setChildren(children2);
            }
        }
        return R.success(list);
    }

    /**
     * 添加权限
     *
     * @param permission
     * @return
     */
    @PostMapping(value = "/save")
    @Override
    public R save(@RequestBody Permission permission) {
        // 判断拦截请求的操作权限按钮名是否已存在
        if (CommonConstants.PERMISSION_OPERATION.equals(permission.getType())) {
            List<Permission> list = permissionService.list(new QueryWrapper<Permission>().eq("title", permission.getTitle()));
            if (list != null && list.size() > 0) {
                return R.warn("名称已存在");
            }
        }
        permissionService.save(permission);
        return R.success(permission);
    }

    /**
     * 更新权限
     *
     * @param permission
     * @return
     */
    @PostMapping(value = "/update")
    @Override
    public R update(@RequestBody Permission permission) {
        // 判断拦截请求的操作权限按钮名是否已存在
        if (CommonConstants.PERMISSION_OPERATION.equals(permission.getType())) {
            // 若名称修改
            Permission p = permissionService.getById(permission.getId());
            if (!p.getTitle().equals(permission.getTitle())) {
                List<Permission> list;
                list = permissionService.list(new QueryWrapper<Permission>().eq("title", permission.getTitle()));
                if (list != null && list.size() > 0) {
                    return R.warn("名称已存在");
                }
            }
        }
        permissionService.updateById(permission);
        return R.success();
    }

    /**
     * 批量删除权限
     *
     * @param ids
     * @return
     */
    @ApiOperation(value = "批量通过ids删除")
    @DeleteMapping(value = "/delByIds/{ids}")
    @Override
    public R delByIds(@PathVariable String[] ids) {
        for (String id : ids) {
            List<RolePermission> list = rolePermissionService.list(new QueryWrapper<RolePermission>().eq("permission_id", id));
            if (list != null && list.size() > 0) {
                return R.warn("删除失败，包含正被角色使用关联的菜单或权限");
            }
        }
        for (String id : ids) {
            permissionService.removeById(id);
        }
        return R.success();
    }
}
