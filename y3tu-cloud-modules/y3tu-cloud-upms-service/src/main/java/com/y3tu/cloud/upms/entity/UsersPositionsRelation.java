package com.y3tu.cloud.upms.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;

import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import com.y3tu.tool.web.base.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户和岗位关系表
 * </p>
 *
 * @author y3tu
 * @date 2018-08-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("users_positions_relation")
public class UsersPositionsRelation extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 关系id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 用户id
     */
    @TableField("user_id")
    private Integer userId;
    /**
     * 岗位id
     */
    @TableField("position_id")
    private Integer positionId;
    /**
     * 创建时间
     */
    @TableField("created_time")
    private Date createdTime;
    /**
     * 更新时间
     */
    @TableField("updated_time")
    private Date updatedTime;
    /**
     * 创建人
     */
    @TableField("created_by")
    private String createdBy;
    /**
     * 更新人
     */
    @TableField("updated_by")
    private String updatedBy;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
