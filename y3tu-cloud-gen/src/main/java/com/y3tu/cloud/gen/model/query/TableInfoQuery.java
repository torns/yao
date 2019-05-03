package com.y3tu.cloud.gen.model.query;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.y3tu.cloud.gen.model.entity.TableInfo;
import lombok.Data;


@Data
public class TableInfoQuery extends Page<TableInfo> {

    private String tableName;


}
