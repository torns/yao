package com.y3tu.cloud.upms.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.y3tu.tool.web.base.entity.BaseEntity;
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
 * @date  2018-08-05
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("log")
public class Log extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
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
    /**
     * 结果 0：正常 1：错误
     */
	@TableField("result_type")
	private Integer resultType;
    /**
     * 结果信息
     */
	@TableField("result_msg")
	private String resultMsg;


	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
