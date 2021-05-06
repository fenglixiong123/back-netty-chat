
-- 数据库说明
-- https://andaily.com/spring-oauth-server/db_table_description.html

-- Sql脚本
-- https://github.com/spring-projects/spring-security-oauth/blob/main/spring-security-oauth2/src/test/resources/schema.sql

-- 访问令牌(redis模式不需要)
CREATE TABLE IF NOT EXISTS `oauth_access_token` (
    `token_id` varchar(255) DEFAULT NULL COMMENT '加密的access_token的值',
    `token` longblob COMMENT 'OAuth2AccessToken.java对象序列化后的二进制数据',
    `authentication_id` varchar(255) DEFAULT NULL COMMENT '加密过的username,client_id,scope',
    `user_name` varchar(255) DEFAULT NULL COMMENT '登录的用户名',
    `client_id` varchar(255) DEFAULT NULL COMMENT '客户端ID',
    `authentication` longblob COMMENT 'OAuth2Authentication.java对象序列化后的二进制数据',
    `refresh_token` varchar(255) DEFAULT NULL COMMENT '加密的refresh_token的值'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 更新令牌(redis模式不需要)
CREATE TABLE IF NOT EXISTS `oauth_refresh_token` (
    `token_id` varchar(255) DEFAULT NULL COMMENT '加密过的refresh_token的值',
    `token` longblob COMMENT 'OAuth2RefreshToken.java对象序列化后的二进制数据 ',
    `authentication` longblob COMMENT 'OAuth2Authentication.java对象序列化后的二进制数据'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 授权记录(给用户发的token记录)
CREATE TABLE IF NOT EXISTS `oauth_approvals` (
    `userId` varchar(255) DEFAULT NULL COMMENT '登录的用户名',
    `clientId` varchar(255) DEFAULT NULL COMMENT '客户端ID',
    `scope` varchar(255) DEFAULT NULL COMMENT '申请的权限范围',
    `status` varchar(10) DEFAULT NULL COMMENT '状态（Approve或Deny）',
    `expiresAt` datetime DEFAULT NULL COMMENT '过期时间',
    `lastModifiedAt` datetime DEFAULT NULL COMMENT '最终修改时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 客户端信息(必须，一般我们自己另外定义)
CREATE TABLE IF NOT EXISTS `oauth_client_details` (
    `client_id` varchar(255) NOT NULL COMMENT '用于唯一标识每一个客户端(client)也称为(appKey)',
    `resource_ids` varchar(255) DEFAULT NULL COMMENT '客户端所能访问的资源id集合,多个资源时用逗号(,)分隔',
    `client_secret` varchar(255) DEFAULT NULL COMMENT '用于指定客户端(client)的访问密匙也称为(appSecret)',
    `scope` varchar(255) DEFAULT NULL COMMENT '指定客户端申请的权限范围,可选值包括read,write,trust',
    `authorized_grant_types` varchar(255) DEFAULT NULL COMMENT '客户端支持的grant_type',
    `web_server_redirect_uri` varchar(255) DEFAULT NULL COMMENT '重定向URI',
    `authorities` varchar(255) DEFAULT NULL COMMENT '客户端所拥有的Spring Security的权限值(ROLE_USER)，多个用逗号(,)分隔',
    `access_token_validity` int(11) DEFAULT NULL COMMENT '访问令牌有效时间值(单位:秒)',
    `refresh_token_validity` int(11) DEFAULT NULL COMMENT '更新令牌有效时间值(单位:秒)',
    `additional_information` varchar(255) DEFAULT NULL COMMENT '预留字段',
    `autoapprove` varchar(255) DEFAULT NULL COMMENT '用户是否自动Approval操作'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 客户端用来记录token信息
CREATE TABLE IF NOT EXISTS `oauth_client_token` (
    `token_id` varchar(255) DEFAULT NULL COMMENT '加密的access_token值',
    `token` longblob COMMENT 'OAuth2AccessToken.java对象序列化后的二进制数据',
    `authentication_id` varchar(255) DEFAULT NULL COMMENT '加密过的username,client_id,scope',
    `user_name` varchar(255) DEFAULT NULL COMMENT '登录的用户名',
    `client_id` varchar(255) DEFAULT NULL COMMENT '客户端ID'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 授权码(授权码模式才使用)
CREATE TABLE IF NOT EXISTS `oauth_code` (
    `code` varchar(255) DEFAULT NULL COMMENT '授权码(未加密)'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



