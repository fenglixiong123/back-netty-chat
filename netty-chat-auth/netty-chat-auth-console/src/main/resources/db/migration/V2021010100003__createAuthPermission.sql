SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for basic_bucket_part
-- ----------------------------
CREATE TABLE IF NOT EXISTS `web_auth`.`auth_permission`  (
    `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
    `pid` bigint(20) UNSIGNED NOT NULL DEFAULT '0' COMMENT '父权限id',
    `code` varchar(64) NOT NULL COMMENT '权限编码',
    `name` varchar(64) DEFAULT NULL COMMENT '权限名称',
    `path` varchar(255) DEFAULT NULL COMMENT '权限路径',
    `method` varchar(32) DEFAULT 'POST' COMMENT '请求方法',
    `icon` varchar(64) DEFAULT NULL COMMENT '权限图标',
    `order` int(11) DEFAULT NULL COMMENT '排序',

    `state` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'effective' COMMENT '状态',
    `create_user` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '创建者',
    `create_time` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
    `update_user` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '最后更新者',
    `update_time` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '最后更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `uk_user_name`(`code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;