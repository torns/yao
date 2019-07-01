package com.y3tu.yao.upms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.y3tu.yao.upms.mapper.DepartmentMapper;
import com.y3tu.yao.upms.model.entity.Department;
import com.y3tu.yao.upms.service.DepartmentService;
import com.y3tu.tool.web.base.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author y3tu
 */
@Service("departmentService")
public class DepartmentServiceImpl extends BaseServiceImpl<DepartmentMapper, Department> implements DepartmentService {

    @Override
    public List<Department> findByParentIdOrderBySortOrder(String parentId, boolean openDataFilter) {
        // 数据权限
        // List<String> depIds = securityUtil.getDepartmentIds();
        List<String> depIds = null;
        if (depIds != null && depIds.size() > 0 && openDataFilter) {
            return this.list(new QueryWrapper<Department>().eq("parent_id", parentId).in("id").orderByAsc("sort_order"));
        }
        return this.list(new QueryWrapper<Department>().eq("parent_id", parentId).orderByAsc("sort_order"));
    }
}
