package com.y3tu.yao.upms.service;

import com.y3tu.yao.upms.model.entity.Department;
import com.y3tu.tool.web.base.service.BaseService;

import java.util.List;

/**
 * ${comments}
 *
 * @author y3tu
 * @date 2018-11-26 19:47:53
 */
public interface DepartmentService extends BaseService<Department> {
    /**
     * 通过父id获取 升序
     *
     * @param parentId 父Id
     * @param openDataFilter 是否开启数据权限
     * @return
     */
    List<Department> findByParentIdOrderBySortOrder(String parentId, boolean openDataFilter);
}

