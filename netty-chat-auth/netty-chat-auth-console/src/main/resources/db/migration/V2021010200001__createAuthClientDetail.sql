SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for oauth_client_details
-- ----------------------------

-- 客户端信息
-- 参考实现类：JdbcClientDetailsService.java
CREATE TABLE IF NOT EXISTS `web_auth`.`oauth_client_details` (
    `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
    `client_id` varchar(255) NOT NULL COMMENT '用于唯一标识每一个客户端(client)也称为(appKey)',
    `resource_ids` varchar(255) DEFAULT NULL COMMENT '客户端所能访问的资源id集合,多个资源时用逗号(,)分隔',
    `client_secret` varchar(255) DEFAULT NULL COMMENT '用于指定客户端(client)的访问密匙也称为(appSecret)',
    `scope` varchar(255) DEFAULT NULL COMMENT '指定客户端申请的权限范围,可选值包括read,write,trust',
    `authorized_grant_types` varchar(255) DEFAULT NULL COMMENT '客户端支持的grant_type',
    `web_server_redirect_uri` varchar(255) DEFAULT NULL COMMENT '客户端重定向URI',
    `authorities` varchar(255) DEFAULT NULL COMMENT '客户端所拥有的SpringSecurity的权限值(ROLE_USER)，多个用逗号(,)分隔',
    `access_token_validity` int(11) DEFAULT NULL COMMENT '访问令牌有效时间值(单位:秒)',
    `refresh_token_validity` int(11) DEFAULT NULL COMMENT '更新令牌有效时间值(单位:秒)',
    `additional_information` varchar(255) DEFAULT NULL COMMENT '预留字段,JSON格式',
    `auto_approve` varchar(255) DEFAULT NULL COMMENT '用户是否自动Approval操作',

    `state` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'effective' COMMENT '状态',
    `create_user` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'web_chat' COMMENT '创建者',
    `create_time` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
    `update_user` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'web_chat' COMMENT '最后更新者',
    `update_time` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '最后更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `uk_client_id`(`client_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT = 1 DEFAULT CHARSET=utf8 COMMENT='客户端信息表';

SET FOREIGN_KEY_CHECKS = 1;