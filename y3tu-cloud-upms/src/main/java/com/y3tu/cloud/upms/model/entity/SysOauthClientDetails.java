package com.y3tu.cloud.upms.model.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.y3tu.tool.web.base.entity.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * oauth2客户端记录表
 * </p>
 *
 * @author y3tu
 */
@TableName("sys_oauth_client_details")
@Accessors(chain = true)
@Data
public class SysOauthClientDetails extends BaseEntity<SysOauthClientDetails> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "client_id", type = IdType.INPUT)
    private String clientId;
    @TableField("resource_ids")
    private String resourceIds;
    @TableField("client_secret")
    private String clientSecret;
    private String scope;
    @TableField("authorized_grant_types")
    private String authorizedGrantTypes;
    @TableField("web_server_redirect_uri")
    private String webServerRedirectUri;
    private String authorities;
    @TableField("access_token_validity")
    private Integer accessTokenValidity;
    @TableField("refresh_token_validity")
    private Integer refreshTokenValidity;
    @TableField("additional_information")
    private String additionalInformation;
    private String autoapprove;

    @Override
    protected Serializable pkVal() {
        return this.clientId;
    }

    @Override
    public String toString() {
        return "SysOauthClientDetails{" +
                ", clientId=" + clientId +
                ", resourceIds=" + resourceIds +
                ", clientSecret=" + clientSecret +
                ", scope=" + scope +
                ", authorizedGrantTypes=" + authorizedGrantTypes +
                ", webServerRedirectUri=" + webServerRedirectUri +
                ", authorities=" + authorities +
                ", accessTokenValidity=" + accessTokenValidity +
                ", refreshTokenValidity=" + refreshTokenValidity +
                ", additionalInformation=" + additionalInformation +
                ", autoapprove=" + autoapprove +
                "}";
    }
}
