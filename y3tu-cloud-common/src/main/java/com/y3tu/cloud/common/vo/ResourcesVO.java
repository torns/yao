package com.y3tu.cloud.common.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 资源表
 * </p>
 *
 * @author y3tu
 * @date 2018-08-24
 */
@Data
public class ResourcesVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 资源id
     */
    private Long id;
    /**
     * 资源编码
     */
    private String code;
    /**
     * 资源类型
     */
    private String type;
    /**
     * 资源名称
     */
    private String name;
    /**
     * 资源路径
     */
    private String url;
    /**
     * 请求方法
     */
    private String method;
    /**
     * 资源简介
     */
    private String description;
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
