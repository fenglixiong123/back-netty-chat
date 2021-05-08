
# auth2 数据库文件说明

Oauth2相关的5张表：

* oauth_access_token：访问令牌
* oauth_refresh_token：更新令牌  
* oauth_client_details：客户端信息  
* oauth_client_token:  客户端用来记录token信息
* oauth_code：授权码  
* oauth_approvals：授权记录  

## oauth_access_token：访问令牌

    * `authentication_id` varchar(255) DEFAULT NULL COMMENT '身份验证ID,该字段具有唯一性,根据username,client_id,scope通过MD5加密生成的',
    * `client_id` varchar(255) DEFAULT NULL COMMENT '客户端ID',
    * `token_id` varchar(255) DEFAULT NULL COMMENT '将access_token的值通过MD5加密后存储的',
    * `token` longblob COMMENT '存储OAuth2AccessToken.java对象序列化后的二进制数据',
    * `user_name` varchar(255) DEFAULT NULL COMMENT '登录时的用户名, 若客户端没有用户名(如grant_type="client_credentials"),则该值等于client_id',
    * `authentication` longblob COMMENT '存储OAuth2Authentication.java对象序列化后的二进制数据',
    * `refresh_token` varchar(255) DEFAULT NULL COMMENT '将refresh_token的值通过MD5加密后存储的',

## oauth_refresh_token：更新令牌  

    * `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    * `token_id` varchar(255) DEFAULT NULL COMMENT '将refresh_token的值通过MD5加密后存储的',
    * `token` longblob COMMENT '存储OAuth2RefreshToken.java对象序列化后的二进制数据 ',
    * `authentication` longblob COMMENT '存储OAuth2Authentication.java对象序列化后的二进制数据'

## oauth_client_details：客户端信息  

    * `client_id` varchar(255) NOT NULL COMMENT '用于唯一标识每一个客户端(client)也称为(appKey)',
    * `resource_ids` varchar(255) DEFAULT NULL COMMENT '客户端所能访问的资源id集合,多个资源时用逗号(,)分隔',
    
        客户端可以访问的资源id集合
    
    * `client_secret` varchar(255) DEFAULT NULL COMMENT '用于指定客户端(client)的访问密匙也称为(appSecret)',
    
        客户端秘钥，必须加密(BCY)，不然报错
    
    * `scope` varchar(255) DEFAULT NULL COMMENT '指定客户端申请的权限范围,可选值包括read,write,trust',
    
        可选值："all","read","write"
        权限有哪些,如果这两配置了该参数，客户端发请求可以不带参数，使用配置的参数
    
    * `authorized_grant_types` varchar(255) DEFAULT NULL COMMENT '客户端支持的grant_type',
          
          说明OAuth2支持的grant_type(授权方式)与功能  
          
          1.authorization_code 
              -- 授权码模式(即先登录获取code,再获取token)  
          2.password 
              -- 密码模式(将用户名,密码传过去,直接获取token)  
          3.refresh_token 
              -- 刷新access_token  
          4.implicit 
              -- 简化模式(在redirect_uri 的Hash传递token; Auth客户端运行在浏览器中,如JS,Flash)  
          5.client_credentials 
              -- 客户端模式(无用户,用户向客户端注册,然后客户端以自己的名义向'服务端'获取资源)  
    
    * `web_server_redirect_uri` varchar(255) DEFAULT NULL COMMENT '客户端重定向URI',
            
          授权码模式下必须配置
          密码模式下不用配置
          得到授权码后返回地址            
            
    * `authorities` varchar(255) DEFAULT NULL COMMENT '客户端所拥有的SpringSecurity的权限值(ROLE_USER)，多个用逗号(,)分隔',
    * `access_token_validity` int(11) DEFAULT NULL COMMENT '访问令牌有效时间值(单位:秒)',
    * `refresh_token_validity` int(11) DEFAULT NULL COMMENT '更新令牌有效时间值(单位:秒)',
    * `additional_information` varchar(255) DEFAULT NULL COMMENT '预留字段,JSON格式',
    * `autoapprove` varchar(255) DEFAULT NULL COMMENT '用户是否自动Approval操作'
    
        授权码模式下可用，是否自动跳过用户授权确认页面

## oauth_code：授权码  

    * `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    * `code` varchar(255) DEFAULT NULL COMMENT '授权码(未加密)',
    * `authentication` longblob COMMENT '存储AuthorizationRequestHolder.java对象序列化后的二进制数据.'

## oauth_approvals：授权记录  

    * `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    * `userId` varchar(255) DEFAULT NULL COMMENT '登录的用户名',
    * `clientId` varchar(255) DEFAULT NULL COMMENT '客户端ID',
    * `scope` varchar(255) DEFAULT NULL COMMENT '申请的权限范围',
    * `status` varchar(10) DEFAULT NULL COMMENT '状态（Approve或Deny）',
    * `expiresAt` datetime DEFAULT NULL COMMENT '过期时间',
    * `lastModifiedAt` datetime DEFAULT NULL COMMENT '最终修改时间'

## oauth_client_token:  客户端用来记录token信息

    * `token_id` varchar(255) DEFAULT NULL COMMENT '从服务器端获取到的access_token的值',
    * `token` longblob COMMENT '存储OAuth2AccessToken.java对象序列化后的二进制数据',
    * `authentication_id` varchar(255) DEFAULT NULL COMMENT '该字段具有唯一性,根据username,client_id,scope通过MD5加密生成的',
    * `user_name` varchar(255) DEFAULT NULL COMMENT '登录的用户名',
    * `client_id` varchar(255) DEFAULT NULL COMMENT '客户端ID'

