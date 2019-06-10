package com.y3tu.yao.upms.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.y3tu.tool.web.base.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * 字典数据
 *
 * @author y3tu
 * @date 2018-12-14 14:50:02
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_dict_data")
public class DictData extends BaseEntity<DictData> {
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
    @TableField("dict_id")
    private String dictId;
    /**
     *
     */
    @TableField("sort_order")
    private BigDecimal sortOrder;
    /**
     *
     */
    private Integer status;
    /**
     *
     */
    private String title;
    /**
     *
     */
    private String value;

    /**
     * 主键值
     */
    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
