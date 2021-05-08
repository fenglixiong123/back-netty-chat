
# 参考文章

Auth2角色和流程
https://www.cnblogs.com/lihaoyang/p/12045480.html

https://blog.csdn.net/q438944209/article/details/82920461

https://blog.csdn.net/w1054993544/article/details/109361170

https://segmentfault.com/a/1190000023662197?utm_source=tag-newest


# Auth2流程

![Auth2登录流程](../static/img/auth_01.png)

---

![Auth2登录流程](https://img2018.cnblogs.com/blog/702434/201912/702434-20191215212721179-1386291187.png)

---

![Auth2登录流程](../static/img/auth_02.png)


# Auth2介绍

Spring OAuth2是基于security来实现的。

Spring OAuth2分为两部分：OAuth2 Provider和OAuth2 Client。

OAuth2 Provider分为授权服务和资源服务：

* 授权服务

    * 用户认证服务
        
        验证用户登录，角色，权限等
        
    * 客户端认证服务
    
        验证各个服务模块的token等

* 资源服务

    * 资源服务端
    
        微服务本身属于资源服务提供方，称为资源服务端，本身的api被auth2保护起来
    
    * 资源客户端
    
        微服务还要调用其他微服务，也可以称为资源客户端

这两个角色可以在一个服务中，也可以不在一个服务中，也可能是一个授权服务，对应多个资源服务。

#认证中心

本服务为认证中心，其他微服务需要来次进行验证才可以通行

## Auth2的四种授权模式

* 授权码模式（authorization code）

    ~~~
        1.用户访问客户端，客户端通过用户代理向认证服务器请求授权码；
        2.用户同意授权；
        3.认证服务器通过用户代理返回授权码给客户端；
        4.客户端携带授权码向认证服务器请求访问令牌（AccessToken）；
        5.认证服务器返回访问令牌；
        6.客户端携带访问令牌向资源服务器请求资源；
        7.资源服务器返回资源。
    ~~~
  
* 简化模式（implicit）

    ~~~
        1.用户访问客户端，客户端通过用户代理向认证服务器请求授权码；
        2。用户同意授权；
        3.认证服务器返回一个重定向地址，该地址的url的Hash部分包含了令牌；
        4.用户代理向资源服务器发送请求，其中不带令牌信息；
        5.资源服务器返回一个网页，其中包含的脚本可以获取Hash中的令牌；
        6.用户代理执行脚本提取令牌；
        7.用户代理将令牌返回给客户端；
        8.客户端携带令牌向资源服务器请求资源；
        9.资源服务器返回资源。
    ~~~

* 客户端模式（client credentials）
    
    ~~~
        1.客户端向认证服务器进行身份认证，并要求一个访问令牌；
        2.认证服务器确认无误后，向客户端提供访问令牌；
        3.客户端携带令牌向资源服务器请求访问资源；
        4.资源服务器返回资源。
    ~~~
  
  常用于访问公共资源（无需登录）：网站首页
  
    ~~~
        请求参数：
        {
            grant_type:client_credentials  
            client_id:46582ae7217343a8b252e3977e7cc421  
            client_secret:cgGvf5Rotv7D76m9JaArfY3YG6fDec47  
        }
    ~~~
  
* 密码模式（resource owner password credentials）

    ~~~
        1.用户向客户端提供用户名密码；
        2.客户端将用户名和密码发给认证服务器请求令牌；
        3.认证服务器确认无误后，向客户端提供访问令牌；
        4.客户端携带令牌向资源服务器请求访问资源；
        5.资源服务器返回资源。
    ~~~
  
   常用于访问个人资源
 
     ~~~
        请求参数
        获取令牌：
        {
             grant_type:password
             client_id:46582ae7217343a8b252e3977e7cc421
             username:18565783136
             password:AC1DAdo9ZcY4dKAdtyPRzoICWZlkR7WDgtO06S5fVCUS6A/67rMxeW+2mKKbo2N1FQ==
             client_secret:cgGvf5Rotv7D76m9JaArfY3YG6fDec47
        } 
        刷新令牌：
        {
            grant_type:refresh_token
            client_id:46582ae7217343a8b252e3977e7cc421
            client_secret:cgGvf5Rotv7D76m9JaArfY3YG6fDec47
            refresh_token:1ba402f7-394b-420b-9805-39578d6176f8
        }
     ~~~

# 资源服务

其他服务提供方的微服务称为资源服务，例如：用户服务，订单服务，消息服务等
