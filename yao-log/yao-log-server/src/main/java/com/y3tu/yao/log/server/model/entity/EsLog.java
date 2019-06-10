package com.y3tu.yao.log.server.model.entity;

import com.y3tu.tool.core.util.IdUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.*;

import java.io.Serializable;
import java.util.Date;


/**
 * Elasticsearch文档实体类
 *
 * @author y3tu
 */
@Data
@Document(indexName = "log", type = "log", shards = 1, replicas = 0, refreshInterval = "-1")
public class EsLog implements Serializable {

    @ApiModelProperty(value = "唯一标识")
    private String id = String.valueOf(IdUtil.createSnowflake(1, 1).nextId());

    /**
     * 日志类型
     */
    private String type;

    /**
     * 模块名
     */
    private String moduleName;

    /**
     * 操作名
     */
    private String actionName;

    /**
     * 服务ID
     */
    private String serviceId;

    /**
     * 操作IP地址
     */
    private String remoteAddr;

    /**
     * 用户代理
     */
    private String userAgent;

    /**
     * 请求URI
     */
    private String requestUri;

    /**
     * 操作方式
     */
    private String method;

    /**
     * 操作提交的数据
     */
    private String params;

    /**
     * 执行时间
     */
    private String time;

    /**
     * 异常信息
     */
    private String exception;

    /**
     * 删除标记
     */
    private String delFlag;

    /**
     * 创建者
     */
    private String createBy;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 操作状态 1 失败  0 成功
     */
    private String status;

}
