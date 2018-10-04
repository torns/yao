##access_token存储表
DROP TABLE IF EXISTS oauth_access_token;
CREATE TABLE oauth_access_token
(
  token_id          CHARACTER VARYING(256) COMMENT 'MD5加密的access_token的值',
  token             blob COMMENT 'OAuth2AccessToken.java对象序列化后的二进制数据',
  authentication_id CHARACTER VARYING(256) COMMENT 'MD5加密过的username,client_id,scope',
  user_name         CHARACTER VARYING(256) COMMENT '登录的用户名',
  client_id         CHARACTER VARYING(256) COMMENT '客户端ID',
  authentication    blob COMMENT 'OAuth2Authentication.java对象序列化后的二进制数据',
  refresh_token     CHARACTER VARYING(256) COMMENT 'MD5加密后的refresh_token的值'
)
  COMMENT '访问令牌表';

##refresh_token存储表
DROP TABLE IF EXISTS oauth_refresh_token;
CREATE TABLE oauth_refresh_token
(
  token_id       CHARACTER VARYING(256) COMMENT 'MD5加密过的refresh_token的值',
  token          blob COMMENT 'OAuth2RefreshToken.java对象序列化后的二进制数据',
  authentication blob COMMENT 'OAuth2Authentication.java对象序列化后的二进制数据'
)
  COMMENT '更新令牌表';

##授权记录表
DROP TABLE IF EXISTS oauth_approvals;
CREATE TABLE oauth_approvals
(
  userid         CHARACTER VARYING(256) COMMENT '登录的用户名',
  clientid       CHARACTER VARYING(256) COMMENT '客户端ID',
  scope          CHARACTER VARYING(256) COMMENT '申请的权限',
  status         CHARACTER VARYING(10) COMMENT '状态（Approve或Deny',
  expiresat      DATE COMMENT '过期时间',
  lastmodifiedat DATE COMMENT '最终修改时间'
)
  COMMENT '授权记录表';

##授权码表
DROP TABLE IF EXISTS oauth_code;
CREATE TABLE oauth_code
(
  code           CHARACTER VARYING(256) COMMENT '授权码(未加密)',
  authentication blob COMMENT 'AuthorizationRequestHolder.java对象序列化后的二进制数据'
)
  COMMENT '授权码表';

##client用户表
DROP TABLE IF EXISTS oauth_client_details;
CREATE TABLE oauth_client_details
(
  client_id               CHARACTER VARYING(256) NOT NULL PRIMARY KEY
  COMMENT '客户端ID',
  resource_ids            CHARACTER VARYING(256) COMMENT '资源ID集合,多个资源时用逗号(,)分隔',
  client_secret           CHARACTER VARYING(256) COMMENT '客户端密匙',
  scope                   CHARACTER VARYING(256) COMMENT '客户端申请的权限范围',
  authorized_grant_types  CHARACTER VARYING(256) COMMENT '客户端支持的grant_type',
  web_server_redirect_uri CHARACTER VARYING(256) COMMENT '重定向URI',
  authorities             CHARACTER VARYING(256) COMMENT '客户端所拥有的Spring Security的权限值，多个用逗号(,)分隔',
  access_token_validity   INTEGER COMMENT '访问令牌有效时间值(单位:秒)',
  refresh_token_validity  INTEGER COMMENT '更新令牌有效时间值(单位:秒)',
  additional_information  CHARACTER VARYING(4096) COMMENT '预留字段',
  autoapprove             CHARACTER VARYING(256) COMMENT '用户是否自动Approval操作'
)
  COMMENT '客户端信息';

##客户端授权令牌表
DROP TABLE IF EXISTS oauth_client_token;
CREATE TABLE oauth_client_token
(
  token_id          CHARACTER VARYING(256) COMMENT 'MD5加密的access_token值',
  token             blob COMMENT 'OAuth2AccessToken.java对象序列化后的二进制数据',
  authentication_id CHARACTER VARYING(256) COMMENT 'MD5加密过的username,client_id,scope',
  user_name         CHARACTER VARYING(256) COMMENT '登录的用户名',
  client_id         CHARACTER VARYING(256) COMMENT '客户端ID'
)
  COMMENT '客户端授权令牌表';



