##用户表
DROP TABLE IF EXISTS users;
CREATE TABLE users
(
  id                      SERIAL PRIMARY KEY COMMENT '用户ID',
  username                VARCHAR(100) NOT NULL COMMENT '用户名',
  password                VARCHAR(100) NOT NULL comment '用户密码密文',
  name                    VARCHAR(200) comment '用户姓名',
  mobile                  VARCHAR(20) comment '用户手机',
  enabled                 BOOLEAN comment '是否有效用户',
  account_non_expired     BOOLEAN comment '账号是否未过期',
  credentials_non_expired BOOLEAN comment '密码是否未过期',
  account_non_locked      BOOLEAN comment '账号是否锁定',
  created_time            TIMESTAMP    NOT NULL DEFAULT now() comment '创建时间',
  updated_time            TIMESTAMP    NOT NULL DEFAULT now() comment '更新时间',
  created_by              VARCHAR(100) NOT NULL comment '创建人',
  updated_by              VARCHAR(100) NOT NULL comment '更新人'
)comment '用户表';
CREATE UNIQUE INDEX ux_users_username
  ON users (username);
CREATE UNIQUE INDEX ux_users_mobile
  ON users (mobile);

##用户组表
DROP TABLE IF EXISTS groups;
CREATE TABLE groups
(
  id           SERIAL PRIMARY KEY comment '用户组id',
  parent_id    INT          NOT NULL comment '用户组父id',
  name         VARCHAR(200) comment '用户组名称',
  description   VARCHAR(500) comment '用户组简介',
  created_time TIMESTAMP    NOT NULL DEFAULT now() comment '创建时间',
  updated_time TIMESTAMP    NOT NULL DEFAULT now() comment '更新时间',
  created_by   VARCHAR(100) NOT NULL comment '创建人',
  updated_by   VARCHAR(100) NOT NULL comment '更新人'
)comment '用户组表';


##用户和组关系表
DROP TABLE IF EXISTS users_groups_relation;
CREATE TABLE users_groups_relation
(
  id           SERIAL PRIMARY KEY comment '关系id',
  user_id      INT          NOT NULL comment '用户id',
  group_id     INT          NOT NULL comment '用户组id',
  created_time TIMESTAMP    NOT NULL DEFAULT now() comment '创建时间',
  updated_time TIMESTAMP    NOT NULL DEFAULT now() comment '更新时间',
  created_by   VARCHAR(100) NOT NULL comment '创建人',
  updated_by   VARCHAR(100) NOT NULL comment '更新人'
)comment '用户和组关系表';


##岗位表
DROP TABLE IF EXISTS positions;
CREATE TABLE positions
(
  id           SERIAL PRIMARY KEY comment '岗位id',
  name         VARCHAR(200) comment '岗位名称',
  description   VARCHAR(500) comment '岗位简介',
  created_time TIMESTAMP    NOT NULL DEFAULT now() comment '创建时间',
  updated_time TIMESTAMP    NOT NULL DEFAULT now() comment '更新时间',
  created_by   VARCHAR(100) NOT NULL comment '创建人',
  updated_by   VARCHAR(100) NOT NULL comment '更新人'
) comment '岗位表';

##用户和岗位关系表
DROP TABLE IF EXISTS users_positions_relation;
CREATE TABLE users_positions_relation
(
  id           SERIAL PRIMARY KEY comment '关系id',
  user_id      INT          NOT NULL comment '用户id',
  position_id  INT          NOT NULL comment '岗位id',
  created_time TIMESTAMP    NOT NULL DEFAULT now() comment '创建时间',
  updated_time TIMESTAMP    NOT NULL DEFAULT now() comment '更新时间',
  created_by   VARCHAR(100) NOT NULL comment '创建人',
  updated_by   VARCHAR(100) NOT NULL comment '更新人'
) comment '用户和岗位关系表';


##角色表
DROP TABLE IF EXISTS roles;
CREATE TABLE roles
(
  id           SERIAL PRIMARY KEY comment '角色id',
  code         VARCHAR(100) NOT NULL comment '角色编码',
  name         VARCHAR(200) comment '角色名称',
  description   VARCHAR(500) comment '角色简介',
  created_time TIMESTAMP    NOT NULL DEFAULT now() comment '创建时间',
  updated_time TIMESTAMP    NOT NULL DEFAULT now() comment '更新时间',
  created_by   VARCHAR(100) NOT NULL comment '创建人',
  updated_by   VARCHAR(100) NOT NULL comment '更新人'
) comment '角色表';


#用户和角色关系表
DROP TABLE IF EXISTS users_roles_relation;
CREATE TABLE users_roles_relation
(
  id           SERIAL PRIMARY KEY comment '关系id',
  user_id      INT          NOT NULL comment '用户id',
  role_id      INT          NOT NULL comment '角色id',
  created_time TIMESTAMP    NOT NULL DEFAULT now() comment '创建时间',
  updated_time TIMESTAMP    NOT NULL DEFAULT now() comment '更新时间',
  created_by   VARCHAR(100) NOT NULL comment '创建人',
  updated_by   VARCHAR(100) NOT NULL comment '更新人'
)comment '用户和角色关系表';


##菜单表
DROP TABLE IF EXISTS menus;
CREATE TABLE menus
(
  id           SERIAL PRIMARY KEY comment '菜单id',
  parent_id    INT          NOT NULL comment '父菜单id',
  type         VARCHAR(100) comment '菜单类型',
  href         VARCHAR(200) comment '菜单路径',
  icon         VARCHAR(200) comment '菜单图标',
  name         VARCHAR(200) comment '菜单名称',
  description   VARCHAR(500) comment '菜单简介',
  order_num    INTEGER comment '菜单序列',
  created_time TIMESTAMP    NOT NULL DEFAULT now() comment '创建时间',
  updated_time TIMESTAMP    NOT NULL DEFAULT now() comment '更新时间',
  created_by   VARCHAR(100) NOT NULL comment '创建人',
  updated_by   VARCHAR(100) NOT NULL comment '更新人'
)comment '菜单表';

##角色和菜单关系表
DROP TABLE IF EXISTS roles_menus_relation;
CREATE TABLE roles_menus_relation
(
  id           SERIAL PRIMARY KEY comment '关系id',
  menu_id      INT          NOT NULL comment '菜单id',
  role_id      INT          NOT NULL comment '角色id',
  created_time TIMESTAMP    NOT NULL DEFAULT now() comment '创建时间',
  updated_time TIMESTAMP    NOT NULL DEFAULT now() comment '更新时间',
  created_by   VARCHAR(100) NOT NULL comment '创建人',
  updated_by   VARCHAR(100) NOT NULL comment '更新人'
)comment '角色和菜单关系表';

##资源表
DROP TABLE IF EXISTS resources;
CREATE TABLE resources
(
  id           SERIAL PRIMARY KEY comment '资源id',
  code         VARCHAR(100) comment '资源编码',
  type         VARCHAR(100) comment '资源类型',
  name         VARCHAR(200) comment '资源名称',
  url          VARCHAR(200) comment '资源路径',
  method       VARCHAR(20) comment '请求方法',
  description  VARCHAR(500) comment '资源简介',
  created_time TIMESTAMP    NOT NULL DEFAULT now() comment '创建时间',
  updated_time TIMESTAMP    NOT NULL DEFAULT now()comment '更新时间',
  created_by   VARCHAR(100) NOT NULL comment '创建人',
  updated_by   VARCHAR(100) NOT NULL comment '更新人'
) comment '资源表';
CREATE UNIQUE INDEX ux_resources_code
  ON resources (code);

##角色和资源关系表
DROP TABLE IF EXISTS roles_resources_relation;
CREATE TABLE roles_resources_relation
(
  id           SERIAL PRIMARY KEY comment '关系id',
  resource_id  INT          NOT NULL comment '资源id',
  role_id      INT          NOT NULL comment '角色id',
  created_time TIMESTAMP    NOT NULL DEFAULT now() comment '创建时间',
  updated_time TIMESTAMP    NOT NULL DEFAULT now() comment '更新时间',
  created_by   VARCHAR(100) NOT NULL comment '创建人',
  updated_by   VARCHAR(100) NOT NULL comment '更新人'
)comment '角色和资源关系表';
