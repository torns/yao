DROP TABLE IF EXISTS `t_oauth_client_details`;
CREATE TABLE `t_oauth_client_details` (
                                        `client_id` varchar(40) NOT NULL,
                                        `resource_ids` varchar(256) DEFAULT NULL,
                                        `client_secret` varchar(256) DEFAULT NULL,
                                        `scope` varchar(256) DEFAULT NULL,
                                        `authorized_grant_types` varchar(256) DEFAULT NULL,
                                        `web_server_redirect_uri` varchar(256) DEFAULT NULL,
                                        `authorities` varchar(256) DEFAULT NULL,
                                        `access_token_validity` int(11) DEFAULT NULL,
                                        `refresh_token_validity` int(11) DEFAULT NULL,
                                        `additional_information` varchar(4096) DEFAULT NULL,
                                        `autoapprove` varchar(256) DEFAULT NULL,
                                        PRIMARY KEY (`client_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of sys_oauth_client_details
-- ----------------------------
INSERT INTO `t_oauth_client_details` VALUES ('sso-demo1', null, '$2a$10$pgzIKH1c2/7jS2eYk0bK9OnShYXvWiZXd3firLJkaF2q9Ct.WVtSe', 'read,write', 'password,refresh_token,authorization_code', '', null, null, null, null, 'true');
INSERT INTO `t_oauth_client_details` VALUES ('sso-demo2', '', '$2a$10$0fl9mlBjfg0E48Thhe73Sekw0qdR9OTvXLl3nn8isgwc1LnDyuSD2', 'read,write', 'password,refresh_token,authorization_code', '', '', null, null, null, 'true');
INSERT INTO `t_oauth_client_details` VALUES ('taroco', null, '$2a$10$drYsSntNKIr.cCiAQip0uOp5VtZl2FWZ4WvNYgLcMb19ri66mVzRS', 'server', 'password,refresh_token,authorization_code', null, null, null, null, null, 'false');
