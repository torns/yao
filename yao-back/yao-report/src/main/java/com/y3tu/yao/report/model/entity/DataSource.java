package com.y3tu.yao.report.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;
import java.util.Date;

import com.y3tu.tool.web.base.entity.BaseEntity;


/**
 * data_source 实体对象
 *
 * @author y3tu
 * @date 2019-07-17
 */
@Data
@Accessors(chain = true)
@TableName("data_source")
public class DataSource extends BaseEntity<DataSource> {

    /**
     * 数据源ID
     */
    @TableId(value = "id" , type = IdType.INPUT)
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
    @TableField("driver_class")
    private String driverClass;
    /**
     * 数据源连接字符串(JDBC)
     */
    @TableField("jdbc_url")
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
    @TableField("query_class")
    private String queryClass;
    /**
     * 报表引擎查询器使用的数据源连接池类名
     */
    @TableField("pool_class")
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
    @TableField("create_time")
    private Date createTime;
    /**
     * 记录修改时间
     */
    @TableField("update_time")
    private Date updateTime;
    /**
     * 创建人
     */
    @TableField("create_user")
    private String createUser;
    /**
     * 更新人
     */
    @TableField("update_user")
    private String updateUser;

}

