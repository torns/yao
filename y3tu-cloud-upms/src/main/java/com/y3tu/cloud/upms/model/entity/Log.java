package com.y3tu.cloud.upms.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.y3tu.tool.web.base.entity.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 日志
 * </p>
 *
 * @author y3tu
 * @date 2018-08-05
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_log")
public class Log extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId
    private String id;
    /**
     * 创建人
     */
    @TableField("create_by")
    private String createBy;
    /**
     * 创建时间
     */
    @TableField("create_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /**
     * 是否删除 0:否;1:是
     */
    @TableField("del_flag")
    private Integer delFlag;
    /**
     * 更新人
     */
    @TableField("update_by")
    private String updateBy;
    /**
     * 更新时间
     */
    @TableField("update_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    /**
     * 消耗时间
     */
    @TableField("cost_time")
    private Integer costTime;
    /**
     * 操作ip
     */
    private String ip;
    /**
     * ip详细信息
     */
    @TableField("ip_info")
    private String ipInfo;
    /**
     * 操作名称
     */
    private String name;
    /**
     * 请求参数
     */
    @TableField("request_param")
    private String requestParam;
    /**
     * 请求类型
     */
    @TableField("request_type")
    private String requestType;
    /**
     * 请求url地址
     */
    @TableField("request_url")
    private String requestUrl;
    /**
     * 用户
     */
    private String username;

    @TableField("log_type")
    @ApiModelProperty(value = "日志类型 0登陆日志 1操作日志")
    private Integer logType;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
