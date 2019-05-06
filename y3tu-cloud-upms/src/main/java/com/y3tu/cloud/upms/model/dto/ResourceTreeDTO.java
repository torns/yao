package com.y3tu.cloud.upms.model.dto;

import com.y3tu.cloud.upms.model.entity.Resource;
import lombok.Data;

import java.util.List;

/**
 * 资源树
 * @author y3tu
 * @date 2019-05-06
 */
@Data
public class ResourceTreeDTO extends Resource {
    /**
     * 子节点
     */
    private List<ResourceTreeDTO> children;

    public void addChildren(ResourceTreeDTO tree) {
        this.children.add(tree);
    }
}
