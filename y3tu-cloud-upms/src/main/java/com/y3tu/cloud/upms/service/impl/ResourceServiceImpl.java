package com.y3tu.cloud.upms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.y3tu.cloud.common.constants.CommonConstants;
import com.y3tu.cloud.common.enums.DataStatusEnum;
import com.y3tu.cloud.common.enums.ResourceTypeEnum;
import com.y3tu.cloud.upms.mapper.ResourceMapper;
import com.y3tu.cloud.upms.model.entity.Resource;
import com.y3tu.cloud.upms.service.ResourceService;
import com.y3tu.tool.core.pojo.TreeNode;
import com.y3tu.tool.core.util.StrUtil;
import com.y3tu.tool.core.util.TreeUtil;
import com.y3tu.tool.web.base.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 资源表 服务实现类
 * </p>
 *
 * @author y3tu
 * @since 2018-08-05
 */
@Service
public class ResourceServiceImpl extends BaseServiceImpl<ResourceMapper, Resource> implements ResourceService {

    @Autowired
    ResourceMapper resourceMapper;

    @Override
    public List<TreeNode<Resource>> getMenuTreeByRoleCodes(List<String> roleCodes) {
        // 1、首选获取所有角色的资源集合
        Set<Resource> resources = getResourceRoleCodes(roleCodes);
        // 2、找出类型为菜单类型的 然后排序
        List<Resource> newResources = resources.stream()
                .filter(resource -> ResourceTypeEnum.MENU.getCode() == resource.getType() || ResourceTypeEnum.TOP_MENU.getCode() == resource.getType())
                .sorted(Comparator.comparingInt(Resource::getSort))
                .collect(Collectors.toList());
        // 3、构建树
        List<TreeNode<Resource>> treeNodeList = newResources.stream().map(resource -> {
            TreeNode<Resource> treeNode = new TreeNode<>(resource.getId(), resource.getName(), resource.getParentId(), resource);
            return treeNode;
        }).collect(Collectors.toList());
        return TreeUtil.buildList(treeNodeList, CommonConstants.TREE_ROOT);
    }

    @Override
    public Set<Resource> getResourceRoleCodes(List<String> roleCodes) {
        Set<Resource> sysResources = new HashSet<>();
        roleCodes.forEach(roleCode -> {
            sysResources.addAll(resourceMapper.findResourceByRoleCode(roleCode));
        });
        return sysResources;
    }

    @Override
    public List<TreeNode<Resource>> getAllResourceTree() {
        QueryWrapper<Resource> query = new QueryWrapper();
        query.eq("del_flag", DataStatusEnum.NORMAL.getCode());
        List<Resource> resources = resourceMapper.selectList(query);
        List<TreeNode<Resource>> treeNodeList = resources.stream().map(resource -> {
            TreeNode<Resource> treeNode = new TreeNode<>(resource.getId(), resource.getName(), resource.getParentId(), resource);
            return treeNode;
        }).collect(Collectors.toList());
        return TreeUtil.buildList(treeNodeList, CommonConstants.TREE_ROOT);
    }

    @Override
    public Boolean deleteResource(Integer id) {
        // 伪删除
        Resource resource = super.getById(id);
        resource.setDelFlag(DataStatusEnum.DELETE.getCode());
        super.updateById(resource);

        Resource resource1 = new Resource();
        resource.setDelFlag(DataStatusEnum.DELETE.getCode());
        UpdateWrapper<Resource> wrapper = new UpdateWrapper<>();
        wrapper.lambda().eq(Resource::getParentId, resource1.getId());
        super.update(resource1, wrapper);
        return true;
    }

    /**
     * todo 使用缓存减少数据查询压力
     */
    @Override
    public List<Resource> findResourceByRoleCode(String roleCode) {
        return resourceMapper.findResourceByRoleCode(roleCode);
    }

    @Override
    public List<String> findPermission(List<String> roles) {
        List<Resource> resourcesVoList = new ArrayList<>();
        //循环遍历所有角色
        for (String role : roles) {
            //查询出每个角色对应的资源
            List<Resource> menuVos = findResourceByRoleCode(role);
            //然后放到资源集合中
            resourcesVoList.addAll(menuVos);
        }
        List<String> permissions = new ArrayList<>();
        //循环遍历资源集合
        for (Resource menuVo : resourcesVoList) {
            if (StrUtil.isNotEmpty(menuVo.getPermission())) {
                String permission = menuVo.getPermission();
                permissions.add(permission);
            }
        }
        return permissions;

    }
}
