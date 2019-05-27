package com.y3tu.cloud.upms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.y3tu.cloud.common.constants.CommonConstants;
import com.y3tu.cloud.common.constants.ServiceNameConstants;
import com.y3tu.cloud.common.vo.ResourceVO;
import com.y3tu.cloud.log.annotation.Log;
import com.y3tu.cloud.upms.model.dto.ResourceTreeDTO;
import com.y3tu.cloud.upms.model.entity.Resource;
import com.y3tu.cloud.upms.model.entity.Role;
import com.y3tu.cloud.upms.model.entity.RoleResource;
import com.y3tu.cloud.upms.service.ResourceService;
import com.y3tu.cloud.upms.service.RoleResourceService;
import com.y3tu.cloud.upms.service.UserRoleService;
import com.y3tu.tool.core.pojo.R;
import com.y3tu.tool.web.base.controller.BaseController;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 资源表 前端控制器
 * </p>
 *
 * @author y3tu
 * @date 2018-08-05
 */
@RestController
@RequestMapping("/resource")
public class ResourceController extends BaseController<ResourceService, Resource> {

    private static final String MODULE_NAME = "系统资源模块";


    @Autowired
    private ResourceService resourceService;
    @Autowired
    private RoleResourceService roleResourceService;
    @Autowired
    private UserRoleService userRoleService;

    /**
     * 获取当前用户的菜单树
     *
     * @return
     */
    @Log(serviceId = ServiceNameConstants.UPMS_SERVER, moduleName = MODULE_NAME, actionName = "根据token查询当前用户权限的菜单树")
    @ApiOperation(value = "获取当前用户的菜单树", notes = "根据token查询当前用户权限的菜单树", httpMethod = "GET")
    @GetMapping("/menu/tree/{userId}")
    public R getMenuTree(@PathVariable("userId") String userId) {

        List<Role> roleList = userRoleService.findByUserId(userId);
        List<String> roleCodes = roleList.stream().map(role -> role.getRoleCode()).collect(Collectors.toList());
        return R.success(resourceService.getMenuTreeByRoleCodes(roleCodes));
    }

    /**
     * 获取所有的菜单树
     *
     * @return
     */
    @Log(serviceId = ServiceNameConstants.UPMS_SERVER, moduleName = MODULE_NAME, actionName = "获取所有的菜单树")
    @ApiOperation(value = "获取当前用户的菜单树", notes = "获取所有的菜单树", httpMethod = "GET")
    @GetMapping("/menu/getAllMenuTree")
    public R getAllMenuTree() {
        return R.success(resourceService.getAllResourceTree());
    }

    /**
     * 添加权限
     *
     * @param resource
     * @return
     */
    @PostMapping(value = "/save")
    @Override
    public R save(@RequestBody Resource resource) {
        // 判断拦截请求的操作权限按钮名是否已存在
        if (CommonConstants.PERMISSION_OPERATION.equals(resource.getType())) {
            List<Resource> list = resourceService.list(new QueryWrapper<Resource>().eq("name", resource.getName()));
            if (list != null && list.size() > 0) {
                return R.warn("名称已存在");
            }
        }
        resourceService.save(resource);
        return R.success(resource);
    }

    /**
     * 更新权限
     *
     * @param resource
     * @return
     */
    @PostMapping(value = "/update")
    @Override
    public R update(@RequestBody Resource resource) {
        // 判断拦截请求的操作权限按钮名是否已存在
        if (CommonConstants.PERMISSION_OPERATION.equals(resource.getType())) {
            // 若名称修改
            Resource p = resourceService.getById(resource.getId());
            if (!p.getName().equals(resource.getName())) {
                List<Resource> list;
                list = resourceService.list(new QueryWrapper<Resource>().eq("name", resource.getName()));
                if (list != null && list.size() > 0) {
                    return R.warn("名称已存在");
                }
            }
        }
        resourceService.updateById(resource);
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
            List<RoleResource> list = roleResourceService.list(new QueryWrapper<RoleResource>().eq("resource_id", id));
            if (list != null && list.size() > 0) {
                return R.warn("删除失败，包含正被角色使用关联的菜单或权限");
            }
        }
        for (String id : ids) {
            resourceService.removeById(id);
        }
        return R.success();
    }

    /**
     * 内部服务调用 可以不返回R
     * 根据角色查询资源信息
     *
     * @param roleCode
     */
    @ApiOperation(value = "根据角色查询资源信息", notes = "根据角色查询资源信息", httpMethod = "GET")
    @ApiImplicitParam(name = "roleCode", value = "角色code", required = true, dataType = "string")
    @GetMapping("/role/{roleCode}")
    public Set<ResourceVO> listResourceByRole(@PathVariable("roleCode") String roleCode) {

        List<Resource> resources = resourceService.findResourceByRoleCode(roleCode);
        Set<ResourceVO> resourceVOS = new HashSet<>();
        resources.stream().forEach(sysResource -> {
            ResourceVO resourceVO = new ResourceVO();
            BeanUtils.copyProperties(resources, resourceVO);
            resourceVOS.add(resourceVO);
        });
        return resourceVOS;
    }
}
