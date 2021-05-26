
# Netty-chat-admin

系统管理模块，主要用来管理用户微服务系统的各种数据

# 用户系统

* 用户系统是一个SpringCloud Auth2 Security的微服务系统

* 分为客户端、认证服务系统和各个资源服务微系统

* 认证服务提供Token令牌申请服务以及权限存储到redis中

* 资源微服务系统提供各种受保护的服务功能

* 客户端向认证服务获取到token之后，带着token向受保护的微服务系统申请资源访问，资源服务校验token以及权限之后返回资源


# 介绍

此系统为单独的系统，跟用户系统没有关系

但是访问用户系统需要申请token令牌，会使用超级用户登录，获取一个超级令牌（不会过期）

管理系统通过feign访问用户系统的内容的时候，会首先校验头中的token信息，如果通过则会直接跳过资源系统的权限校验(超级用户福利)

此管理系统也有自己单独的权限保护措施

# 多数据源

本系统采用多数据源管理，采用开源的dynamic-datasource实现多数据源访问

# 系统权限验证

采用前后端分离模式

Token+redis

核心

* AuthenticationProcessFilter
    
    登录拦截器，当客户端以用户名密码登录时候，会调用此拦截器进行拦截，调用AuthencationManager
    进行用户验证，验证通过则生成token，存储到redis中，如果redis存在此用户则直接返回此token，并刷新数据
    否则，重新生成token
    
* TokenAuthenticationFilter

    用户登录成功会获取到一个token，下次请求资源的时候带上token，会通过此过滤器进行验证，验证成功刷新token有效期

# 参数设置
    
设置token有效期：    

    com.flx.netty.chat.admin.common.security.property.TokenProperties.expireTime 

设置登录处理等信息：

    com.flx.netty.chat.admin.common.security.property.SecurityProperties   