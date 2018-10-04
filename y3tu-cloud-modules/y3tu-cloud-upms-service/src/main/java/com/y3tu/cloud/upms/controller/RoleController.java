package com.y3tu.cloud.upms.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.y3tu.cloud.common.constant.CommonConstant;
import com.y3tu.tool.web.base.controller.BaseController;
import com.y3tu.cloud.upms.model.dto.RoleDTO;
import com.y3tu.cloud.upms.model.entity.SysRole;
import com.y3tu.cloud.upms.service.SysRoleMenuService;
import com.y3tu.cloud.upms.service.SysRoleService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.y3tu.tool.web.base.pojo.Query;
import com.y3tu.tool.web.base.pojo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author liuht
 * @date 2017/11/5
 */
@SuppressWarnings("unchecked")
@RestController
@RequestMapping("/role")
public class RoleController extends BaseController {
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysRoleMenuService sysRoleMenuService;

    /**
     * 通过ID查询角色信息
     *
     * @param id ID
     * @return 角色信息
     */
    @GetMapping("/{id}")
    public SysRole role(@PathVariable Integer id) {
        return sysRoleService.selectById(id);
    }

    /**
     * 添加角色
     *
     * @param roleDto 角色信息
     * @return success、false
     */
    @PostMapping
    public R role(@RequestBody RoleDTO roleDto) {
        return R.ok(sysRoleService.insertRole(roleDto));
    }

    /**
     * 修改角色
     *
     * @param roleDto 角色信息
     * @return success/false
     */
    @PutMapping
    public R roleUpdate(@RequestBody RoleDTO roleDto) {
        return R.ok(sysRoleService.updateRoleById(roleDto));
    }

    @DeleteMapping("/{id}")
    public R roleDel(@PathVariable Integer id) {
        SysRole sysRole = sysRoleService.selectById(id);
        sysRole.setDelFlag(CommonConstant.STATUS_DEL);
        return R.ok(sysRoleService.updateById(sysRole));
    }

    /**
     * 获取角色列表
     *
     * @param deptId 部门ID
     * @return 角色列表
     */
    @GetMapping("/roleList/{deptId}")
    public List<SysRole> roleList(@PathVariable Integer deptId) {
        return sysRoleService.selectListByDeptId(deptId);

    }

    /**
     * 分页查询角色信息
     *
     * @param params 分页对象
     * @return 分页对象
     */
    @GetMapping("/rolePage")
    public Page rolePage(@RequestParam Map<String, Object> params) {
        return sysRoleService.selectwithDeptPage(new Query(params), new EntityWrapper<>());
    }

    /**
     * 更新角色菜单
     *
     * @return success、false
     */
    @PutMapping("/roleMenuUpd")
    public R roleMenuUpd(@RequestBody Map data) {
        final Integer roleId = Integer.valueOf(data.get("roleId") + "");
        final List<Integer> menuIds = (List<Integer>) data.get("menuIds");
        SysRole sysRole = sysRoleService.selectById(roleId);
        return R.ok(sysRoleMenuService.insertRoleMenus(sysRole.getRoleCode(), roleId, menuIds));
    }
}
