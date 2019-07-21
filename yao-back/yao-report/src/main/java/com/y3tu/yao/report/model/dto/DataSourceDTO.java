package com.y3tu.yao.report.model.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;
import java.io.Serializable;

/**
 * data_source Dto对象
 *
 * @author y3tu
 * @date 2019-07-17
 */
@Data
@Accessors(chain = true)
public class DataSourceDTO implements Serializable {
    /**
     * 数据源ID
     */
    private String id;
    /**
     * 数据源名称
     */
    private String name;
    /**
     * 数据源类型 mysql oracle
     */
    private String type;
    /**
     * 数据源驱动类
     */
    private String driverClass;
    /**
     * 数据源连接字符串(JDBC)
     */
    private String jdbcUrl;
    /**
     * 数据源登录用户名
     */
    private String user;
    /**
     * 数据源登录密码
     */
    private String password;
    /**
     * 获取报表引擎查询器类名
     */
    private String queryClass;
    /**
     * 报表引擎查询器使用的数据源连接池类名
     */
    private String poolClass;
    /**
     * 说明备注
     */
    private String comment;
    /**
     * 数据源配置选项(JSON格式）
     */
    private String options;
    /**
     * 记录创建时间
     */
    private Date createTime;
    /**
     * 记录修改时间
     */
    private Date updateTime;
    /**
     * 创建人
     */
    private String createUser;
    /**
     * 更新人
     */
    private String updateUser;
}
