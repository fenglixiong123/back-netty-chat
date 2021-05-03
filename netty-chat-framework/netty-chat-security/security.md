
# 参考文章

https://blog.csdn.net/q438944209/article/details/82920461




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


