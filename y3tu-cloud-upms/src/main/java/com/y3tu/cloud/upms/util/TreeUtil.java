package com.y3tu.cloud.upms.util;

import com.y3tu.cloud.upms.model.dto.ResourceTreeDTO;
import com.y3tu.cloud.upms.model.entity.Resource;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 树形工具类
 *
 * @author y3tu
 * @date 2019-05-06
 */
public class TreeUtil {

    /**
     * 数组转树形结构
     *
     * @param resources
     * @param root
     * @return
     */
    public static List<ResourceTreeDTO> list2Tree(List<Resource> resources, String root) {
        // 普通对象转树节点
        List<ResourceTreeDTO> resourceList = buildTree(resources);
        List<ResourceTreeDTO> trees = new ArrayList<>();
        for (ResourceTreeDTO tree : resourceList) {
            if (root.equals(tree.getParentId())) {
                trees.add(tree);
            }

            for (ResourceTreeDTO t : resourceList) {
                if (tree.getId().equals(t.getParentId())) {
                    if (tree.getChildren() == null) {
                        tree.setChildren(new ArrayList<ResourceTreeDTO>());
                    }
                    tree.addChildren(t);
                }
            }
        }
        return trees;
    }

    /**
     * 对象转树节点
     *
     * @param resources
     * @return
     */
    public static List<ResourceTreeDTO> buildTree(List<Resource> resources) {
        List<ResourceTreeDTO> trees = new ArrayList<>();
        resources.forEach(resource -> {
            ResourceTreeDTO tree = new ResourceTreeDTO();
            BeanUtils.copyProperties(resource, tree);
            trees.add(tree);
        });
        return trees;
    }

}
