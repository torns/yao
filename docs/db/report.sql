DROP TABLE IF EXISTS `datasource`;
CREATE TABLE `datasource`
(
  `id`            int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '数据源ID',
  `name`          varchar(50)           DEFAULT NULL COMMENT '数据源名称',
  `type`          varchar(128)          DEFAULT NULL COMMENT '数据源类型 1：mysql   2：oracle',
  `driver_class`  varchar(100)          DEFAULT NULL COMMENT '数据源驱动类',
  `jdbc_url`      varchar(500)          DEFAULT NULL COMMENT '数据源连接字符串(JDBC)',
  `user`          varchar(50)           DEFAULT NULL COMMENT '数据源登录用户名',
  `password`      varchar(100)          DEFAULT NULL COMMENT '数据源登录密码',
  `queryer_class` varchar(100)          DEFAULT NULL COMMENT '获取报表引擎查询器类名',
  `pool_class`    varchar(100)          DEFAULT NULL COMMENT '报表引擎查询器使用的数据源连接池类名',
  `comment`       varchar(100)          DEFAULT NULL COMMENT '说明备注',
  `options`       varchar(1000)         DEFAULT NULL COMMENT '数据源配置选项(JSON格式）',
  `create_time`   timestamp        NULL DEFAULT '1980-01-01 01:01:01' COMMENT '记录创建时间',
  `update_time`   timestamp        NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录修改时间',
  `create_user`   varchar(255)          DEFAULT NULL COMMENT '创建人',
  `update_user`   varchar(255)          DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`),
  KEY `AK_uk_uid` (`type`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 59
  DEFAULT CHARSET = utf8 COMMENT ='数据源配置信息表';

DROP TABLE IF EXISTS `report`;
CREATE TABLE `report`
(
  `id`           int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '报表ID',
  `uid`          varchar(128)              DEFAULT NULL COMMENT '报表唯一ID,由接口调用方传入',
  `category_id`  int(11)          NOT NULL COMMENT '报表分类id',
  `ds_id`        int(11)          NOT NULL COMMENT '数据源ID',
  `name`         varchar(50)      NOT NULL COMMENT '报表名称',
  `sql_text`     text             NOT NULL COMMENT '报表SQL语句',
  `meta_columns` text             NOT NULL COMMENT '报表列集合元数据(JSON格式)',
  `query_params` text             NOT NULL COMMENT '查询条件列属性集合(JSON格式)',
  `options`      text             NOT NULL COMMENT '报表配置选项(JSON格式)',
  `status`       int(11)          NOT NULL COMMENT '报表状态（1表示锁定，0表示编辑)',
  `sequence`     int(11)          NOT NULL COMMENT '报表节点在其父节点中的顺序',
  `comment`      varchar(500)     NOT NULL COMMENT '说明备注',
  `create_user`  varchar(50)      NOT NULL COMMENT '创建用户',
  `gmt_created`  timestamp        NOT NULL DEFAULT '1980-01-01 01:01:01' COMMENT '记录创建时间',
  `gmt_modified` timestamp        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '记录修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_category_id_name` (`category_id`, `name`),
  UNIQUE KEY `uk_uid` (`uid`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 721
  DEFAULT CHARSET = utf8 COMMENT ='报表信息表';

DROP TABLE IF EXISTS `report_category`;
CREATE TABLE `report_category`
(
  `id`           int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '报表ID',
  `parent_id`    int(11)          NOT NULL DEFAULT '0' COMMENT '父分类',
  `name`         varchar(50)      NOT NULL COMMENT '名称',
  `path`         varchar(500)     NOT NULL COMMENT '树型结构路径从根id到当前id的路径',
  `has_child`    tinyint(1)       NOT NULL COMMENT '是否为子类别1为是，0为否',
  `status`       int(11)          NOT NULL COMMENT '状态（1表示启用，0表示禁用，默认为0)',
  `sequence`     int(11)          NOT NULL COMMENT '节点在其父节点中的顺序',
  `comment`      varchar(500)     NOT NULL COMMENT '说明备注',
  `gmt_created`  timestamp        NOT NULL DEFAULT '1980-01-01 01:01:01' COMMENT '创建时间',
  `gmt_modified` timestamp        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_parent_id_name` (`parent_id`, `name`),
  KEY `ix_path` (`path`(255))
) ENGINE = InnoDB
  AUTO_INCREMENT = 717
  DEFAULT CHARSET = utf8 COMMENT ='报表类别表';

DROP TABLE IF EXISTS `report_history`;
CREATE TABLE `report_history` (
                                `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '报表历史记录id',
                                `report_id` int(11) NOT NULL COMMENT '报表ID',
                                `uid` varchar(128) NOT NULL,
                                `category_id` int(11) NOT NULL COMMENT '报表分类id',
                                `ds_id` int(11) NOT NULL COMMENT '数据源ID',
                                `name` varchar(50) NOT NULL COMMENT '报表名称',
                                `sql_text` text NOT NULL COMMENT '报表SQL语句',
                                `meta_columns` text NOT NULL COMMENT '报表列集合元数据(JSON格式)',
                                `query_params` text NOT NULL COMMENT '查询条件列属性集合(JSON格式)',
                                `options` text NOT NULL COMMENT '报表配置选项(JSON格式)',
                                `status` int(11) NOT NULL COMMENT '报表状态（1表示锁定，0表示编辑)',
                                `sequence` int(11) NOT NULL COMMENT '报表节点在其父节点中的顺序',
                                `comment` varchar(500) NOT NULL COMMENT '说明备注',
                                `author` varchar(50) NOT NULL COMMENT '创建用户',
                                `gmt_created` timestamp NOT NULL DEFAULT '1980-01-01 01:01:01' COMMENT '记录创建时间',
                                `gmt_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '记录修改时间',
                                PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='报表历史记录表';


DROP TABLE IF EXISTS `report_upload`;
CREATE TABLE `report_upload` (
                               `id` int(14) NOT NULL AUTO_INCREMENT COMMENT '主键',
                               `name` varchar(30) DEFAULT NULL COMMENT '名称',
                               `host` varchar(80) DEFAULT NULL COMMENT 'ip',
                               `Port` varchar(10) DEFAULT NULL COMMENT '端口',
                               `Username` varchar(30) DEFAULT NULL COMMENT '用户名',
                               `password` varchar(30) DEFAULT NULL COMMENT '密码',
                               `Path` varchar(80) DEFAULT NULL COMMENT '推送路径',
                               PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='报表上传配置表';