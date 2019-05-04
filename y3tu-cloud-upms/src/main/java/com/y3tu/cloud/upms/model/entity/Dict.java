package com.y3tu.cloud.upms.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.y3tu.tool.web.base.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;


/**
 * 字典dict
 *
 * @author y3tu
 * @date 2018-12-14 14:43:24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_dict")
public class Dict extends BaseEntity<Dict> {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private String id;
    /**
     *
     */
    @TableField("create_by")
    private String createBy;
    /**
     *
     */
    @TableField("create_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /**
     *
     */
    @TableField("del_flag")
    private Integer delFlag;
    /**
     *
     */
    @TableField("update_by")
    private String updateBy;
    /**
     *
     */
    @TableField("update_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    /**
     *
     */
    private String description;
    /**
     *
     */
    private String title;
    /**
     * 字段分类 0：普通字典 1：sql字典
     */
    private Integer type;
    /**
     * 字典编码 唯一
     */
    private String code;

    /**
     * 主键值
     */
    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
