package com.y3tu.cloud.upms.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.y3tu.tool.web.base.controller.BaseController;
import com.y3tu.cloud.upms.service.ResourcesService;
import com.y3tu.cloud.upms.entity.Resources;

import java.util.ArrayList;
import java.util.List;


/**
 * <p>
 * 资源表 前端控制器
 * </p>
 *
 * @author y3tu
 * @date  2018-08-24
 */
@RestController
@RequestMapping("/upms/resources")
public class ResourcesController extends BaseController<ResourcesService,Resources> {
    @Autowired
    ResourcesService resourcesService;
    /**
     * 返回所有的资源定义内容
     * @return
     */
    @GetMapping("/findAll")
    Object findAll(){
       List<Resources> resourcesList = resourcesService.selectList(null);
       return resourcesList;
    }

    /**
     * 根据角色code查询到角色把对应的资源定义
     *
     * @param roleCodes
     * @return
     */
    @GetMapping("/findByRoleCode")
    Object findByRoleCode(String[] roleCodes){
        List<Resources> resourcesList = new ArrayList<>();
        for(String roleCode:roleCodes){
            resourcesList.addAll(resourcesService.findByRoleCode(roleCode));
        }
        return resourcesList;
    }
}
