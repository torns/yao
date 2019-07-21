package com.y3tu.yao.report.controller;

import com.y3tu.tool.web.base.controller.BaseController;
import org.springframework.web.bind.annotation.*;

import com.y3tu.yao.report.model.entity.DataSource;
import com.y3tu.yao.report.service.DataSourceService;


/**
 * data_source Controller
 *
 * @author y3tu
 * @date 2019-07-17
 */
@RestController
@RequestMapping("/report/dataSource")
public class DataSourceController extends BaseController<DataSourceService, DataSource> {

}
