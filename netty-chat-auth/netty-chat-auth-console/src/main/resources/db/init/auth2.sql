
-- 数据库说明
-- https://andaily.com/spring-oauth-server/db_table_description.html

-- Sql脚本
-- https://github.com/spring-projects/spring-security-oauth/blob/main/spring-security-oauth2/src/test/resources/schema.sql

/**
 * 服务端系统

 */
-- 访问令牌(redis模式不需要)
-- 参考实现类：JdbcTokenStore.java
CREATE TABLE IF NOT EXISTS `oauth_access_token` (
    `authentication_id` varchar(255) DEFAULT NULL COMMENT '身份验证ID,该字段具有唯一性,根据username,client_id,scope通过MD5加密生成的',
    `client_id` varchar(255) DEFAULT NULL COMMENT '客户端ID',
    `token_id` varchar(255) DEFAULT NULL COMMENT '将access_token的值通过MD5加密后存储的',
    `token` longblob COMMENT '存储OAuth2AccessToken.java对象序列化后的二进制数据',
    `user_name` varchar(255) DEFAULT NULL COMMENT '登录时的用户名, 若客户端没有用户名(如grant_type="client_credentials"),则该值等于client_id',
    `authentication` longblob COMMENT '存储OAuth2Authentication.java对象序列化后的二进制数据',
    `refresh_token` varchar(255) DEFAULT NULL COMMENT '将refresh_token的值通过MD5加密后存储的',
    PRIMARY KEY (`authentication_id`) USING BTREE,
    UNIQUE INDEX `pk_token_id`(`token_id`) USING BTREE
    UNIQUE INDEX `pk_refresh_token`(`refresh_token`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='令牌发放表';

-- 更新令牌(redis模式不需要)
-- 参考实现类：JdbcTokenStore.java
CREATE TABLE IF NOT EXISTS `oauth_refresh_token` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `token_id` varchar(255) DEFAULT NULL COMMENT '将refresh_token的值通过MD5加密后存储的',
    `token` longblob COMMENT '存储OAuth2RefreshToken.java对象序列化后的二进制数据 ',
    `authentication` longblob COMMENT '存储OAuth2Authentication.java对象序列化后的二进制数据',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='令牌刷新表';

-- 授权记录(给用户发的token记录)
CREATE TABLE IF NOT EXISTS `oauth_approvals` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `userId` varchar(255) DEFAULT NULL COMMENT '登录的用户名',
    `clientId` varchar(255) DEFAULT NULL COMMENT '客户端ID',
    `scope` varchar(255) DEFAULT NULL COMMENT '申请的权限范围',
    `status` varchar(10) DEFAULT NULL COMMENT '状态（Approve或Deny）',
    `expiresAt` datetime DEFAULT NULL COMMENT '过期时间',
    `lastModifiedAt` datetime DEFAULT NULL COMMENT '最终修改时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='授权记录表';

-- 客户端信息(必须，一般我们自己另外定义)
-- 参考实现类：JdbcClientDetailsService.java
CREATE TABLE IF NOT EXISTS `oauth_client_details` (
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
    `autoapprove` varchar(255) DEFAULT NULL COMMENT '用户是否自动Approval操作',
    PRIMARY KEY (`client_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='客户端信息表';



-- 授权码模式下存储服务端产生的授权码
-- 只有当grant_type为"authorization_code"时,该表中才会有数据产生; 其他的grant_type没有使用该表.
-- 参考实现类：JdbcAuthorizationCodeServices.java
CREATE TABLE IF NOT EXISTS `oauth_code` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `code` varchar(255) DEFAULT NULL COMMENT '授权码(未加密)',
    `authentication` longblob COMMENT '存储AuthorizationRequestHolder.java对象序列化后的二进制数据.'
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='授权码表';


/**
 * 客户端系统
 *
 */
-- 该表用于在客户端系统中存储从服务端获取的token数据
-- 参考实现类：JdbcClientTokenServices.java
CREATE TABLE IF NOT EXISTS `oauth_client_token` (
    `token_id` varchar(255) DEFAULT NULL COMMENT '从服务器端获取到的access_token的值',
    `token` longblob COMMENT '存储OAuth2AccessToken.java对象序列化后的二进制数据',
    `authentication_id` varchar(255) DEFAULT NULL COMMENT '该字段具有唯一性,根据username,client_id,scope通过MD5加密生成的',
    `user_name` varchar(255) DEFAULT NULL COMMENT '登录的用户名',
    `client_id` varchar(255) DEFAULT NULL COMMENT '客户端ID',
    PRIMARY KEY (`authentication_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='客户端令牌表';



