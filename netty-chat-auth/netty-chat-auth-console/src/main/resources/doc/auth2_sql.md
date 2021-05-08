
# auth2 数据库文件说明

Oauth2相关的5张表：

* oauth_access_token：访问令牌
* oauth_refresh_token：更新令牌  
* oauth_client_details：客户端信息  
* oauth_client_token:  客户端用来记录token信息
* oauth_code：授权码  
* oauth_approvals：授权记录  

## oauth_access_token：访问令牌



## oauth_refresh_token：更新令牌  



## oauth_client_details：客户端信息  

grant_type  

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

## oauth_code：授权码  



## oauth_approvals：授权记录  



## oauth_client_token:  客户端用来记录token信息



