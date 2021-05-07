
# 参考文章

Auth2角色和流程
https://www.cnblogs.com/lihaoyang/p/12045480.html

https://blog.csdn.net/q438944209/article/details/82920461

https://blog.csdn.net/w1054993544/article/details/109361170

https://segmentfault.com/a/1190000023662197?utm_source=tag-newest

# Auth2的四种授权模式

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

  
# 安全服务

也称为资源服务

每个微服务都是资源服务

微服务中资源服务的99%的API都需要被保护

本服务需要被引入到各个微服务资源中，zuul网关需要排除不需要保护

# 介绍

Spring OAuth2是基于security来实现的。

Spring OAuth2分为两部分：OAuth2 Provider和OAuth2 Client。

OAuth2 Provider的角色被分为
* 授权服务（Authorization Service）
* 资源服务（Resource Service）。
    * 资源服务端
    
        自身作为资源服务提供方，本身的api被auth2保护起来
        
    * 资源客户端
    
        自身如果调用其他资源，本身也是客户端
        

这两个角色可以在一个服务中，也可以不在一个服务中，也可能是一个授权服务，对应多个资源服务。

我采用的就是授权服务和资源服务分离的方式。

# 配置

配置类都写在公用的代码中，一般来说，我的所有微服务都需要这个资源服务配置。当然也存在极个别不需要这个配置的服务（比如zuul网关服务），这个时候，可以在启动类中，使用@Filter将该配置排除：

~~~
@EnableEurekaClient
@SpringBootApplication
@ComponentScan(value = { "com.boco.fyk.auth.**", "com.boco.fyk.core.**" }, excludeFilters = {
		@Filter(type = FilterType.ASSIGNABLE_TYPE, classes = { OAuth2ResourceConfig.class,
				OAuth2ClientConfig.class }) })
@MapperScan("com.boco.fyk.auth.business.dao")
public class ZuulApplication {
	......
}
~~~

## 资源服务端

资源本身作为服务提供方，为资源服务端，提供服务

## 资源客户端

应用中，存在应用与应用之间的相互调用（通过Feign来实现）。

比如：A资源服务调用B资源服务的方法，而B也可能调用A。  
所以，这里在资源服务中配置客户端信息，以便可以访问被OAuth2保护起来的资源


