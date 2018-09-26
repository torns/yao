package com.y3tu.cloud.common.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 菜单表
 * </p>
 *
 * @author y3tu
 * @date 2018-08-24
 */
@Data
public class MenusVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 菜单id
     */
    private Long id;
    /**
     * 父菜单id
     */
    private Integer parentId;
    /**
     * 菜单类型
     */
    private String type;
    /**
     * 菜单路径
     */
    private String href;
    /**
     * 菜单图标
     */
    private String icon;
    /**
     * 菜单名称
     */
    private String name;
    /**
     * 菜单简介
     */
    private String description;
    /**
     * 菜单序列
     */
    private Integer orderNum;
    /**
     * 创建时间
     */
    private Date createdTime;
    /**
     * 更新时间
     */
    private Date updatedTime;
    /**
     * 创建人
     */
    private String createdBy;
    /**
     * 更新人
     */
    private String updatedBy;

}
