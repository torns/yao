package com.y3tu.cloud.common.constants;

/**
 * 安全配置常量
 *
 * @author y3tu
 * @date 2019-05-03
 */
public interface SecurityConstants {
    /**
     * token的header key
     */
    String TOKEN_HEADER = "Authorization";

    String CLOUD = "y3tu-cloud";

    String CLOUD_PREFIX = "y3tu-cloud-";

    /**
     * 用户信息头
     */
    String USER_HEADER = "x-user-header";

    /**
     * 项目的license
     */
    String LICENSE = "made by y3tu";

    /**
     * 默认保存code的前缀
     */
    String DEFAULT_CODE_KEY = "DEFAULT_CODE_KEY";
    /**
     * 默认生成图形验证码过期时间
     */
    int DEFAULT_IMAGE_EXPIRE = 60;

    /**
     * token-uservo
     */
    String TOKEN_USER_DETAIL = "token-user-detail";

    /**
     * jwt 加密key
     */
    String SIGN_KEY = "y3tu-cloud";

    /**
     * sys_oauth_client_details 表的字段，不包括client_id、client_secret
     */
    String CLIENT_FIELDS = "client_id, client_secret, resource_ids, scope, "
            + "authorized_grant_types, web_server_redirect_uri, authorities, access_token_validity, "
            + "refresh_token_validity, additional_information, autoapprove";

    /**
     * JdbcClientDetailsService 查询语句
     */
    String BASE_FIND_STATEMENT = "select " + CLIENT_FIELDS
            + " from t_oauth_client_details";

    /**
     * 默认的查询语句
     */
    String DEFAULT_FIND_STATEMENT = BASE_FIND_STATEMENT + " order by client_id";

    /**
     * 按条件client_id 查询
     */
    String DEFAULT_SELECT_STATEMENT = BASE_FIND_STATEMENT + " where client_id = ?";


    String SPRING_SECURITY_MOBILE_KEY = "mobile";

    String SPRING_SECURITY_CODE_KEY = "code";

    /**
     * 手机验证码登录的地址
     */
    String SPRING_SECURITY_MOBILE_TOKEN_URL = "/mobile/token";


    String REDIS_CODE_PREFIX = "y3tu-cloud-code-";

    Integer REDIS_CODE_EXPIRE = 60;


    /**
     * 基础角色
     */
    String BASE_ROLE = "ROLE_USER";

    /**
     * 角色信息头
     */
    String ROLE_HEADER = "x-role-header";

    /**
     * oauth token
     */
    String OAUTH_TOKEN_URL = "/oauth/token";

    /**
     * 手机登录URL
     */
    String MOBILE_TOKEN_URL = "/mobile/token";

}
