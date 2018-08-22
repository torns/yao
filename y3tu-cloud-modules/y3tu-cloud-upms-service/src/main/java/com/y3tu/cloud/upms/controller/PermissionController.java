package com.y3tu.cloud.upms.controller;


import com.y3tu.cloud.upms.entity.Permission;
import com.y3tu.cloud.upms.service.PermissionService;
import com.y3tu.tool.web.base.controller.BaseController;
import com.y3tu.tool.web.base.pojo.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * <p>
 * 权限表 前端控制器
 * </p>
 *
 * @author y3tu
 * @date 2018-08-05
 */
@RestController
@RequestMapping("/upms/permission")
public class PermissionController extends BaseController<PermissionService, Permission> {

    /**
     * 根据角色获取角色的权限
     * @param role
     * @return
     */
    @GetMapping("/findPermissionByRole/{role}")
    public R findPermissionByRole(@PathVariable String role) {

        return R.ok();
    }
}
