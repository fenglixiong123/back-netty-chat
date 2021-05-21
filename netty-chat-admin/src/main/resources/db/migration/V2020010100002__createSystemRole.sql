SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for basic_bucket_part
-- ----------------------------
CREATE TABLE IF NOT EXISTS `web_admin`.`system_role`  (
    `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
    `code` varchar(64) DEFAULT NULL COMMENT '角色编号',
    `name` varchar(64) NOT NULL COMMENT '角色名称',
    `order` int(11) DEFAULT NULL COMMENT '排序',

    `state` varchar(32) NOT NULL DEFAULT 'effective' COMMENT '状态',
    `create_user` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '创建者',
    `create_time` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
    `update_user` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '最后更新者',
    `update_time` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '最后更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `uk_role_code`(`code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic comment '角色信息';

SET FOREIGN_KEY_CHECKS = 1;