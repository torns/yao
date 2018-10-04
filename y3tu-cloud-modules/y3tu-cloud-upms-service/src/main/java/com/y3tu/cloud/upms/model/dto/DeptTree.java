package com.y3tu.cloud.upms.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author liuht
 * @date 2018/1/20
 * 部门树
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DeptTree extends TreeNode {
    private String name;
}
