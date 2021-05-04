# back-netty-chat

Netty聊天系统

    一个全栈的聊天系统，用了各种技术栈
    
启动环境：

1.下载windows版本的consul，启动consul

consul agent -dev

2.启动redis

    

# 一、技术栈：  
 
## 前端：

* html
* es6
* css
* layui
* webpack

## 后端：

* SpringCloud
* SpringBoot
* SpringSession
* nacos
* redis
* netty
* mysql

# 二、项目框架

|--->netty-chat-parent 父项目目录  
|--->netty-chat-common 项目公共类  
|--->netty-chat-admin 管理系统  
|--->netty-chat-sso 登录系统  



# 三、相关组件

## 1.注册中心


选择consul作为服务注册中心  
下载地址：https://www.consul.io/downloads

# 四、前端设计

分为用户前端和管理前端

# 五、后端设计

分为用户后端和管理后端

## 1.管理后台

用于管理聊天过程中一些用户数据还有一些统计数据

### 1.1 管理登录

用于用户系统的实现以及聊天系统的实现

### 1.2 管理登录



## 2.用户后台

### 2.2 用户登录

传统的session认证

## 其他技术点讲解

### 1.properties配置文件装载问题

 * 正常使用配置文件的方式有：
 
 * 1.直接注解方式(需要被IOC容器管理)
 
 *      a.properties类加注解@ConfigurationProperties(prefix = "flx.swagger")//配置文件注解
 *      b.@Componet //可以被Spring IOC管理
 
 * 2.配置类方式(需要被IOC容器管理)
 
 *      a.properties类加注解@ConfigurationProperties(prefix = "flx.swagger")
 *      b.项目配置类上加@EnableConfigurationProperties({SwaggerInfoProperties.class})
 
 * 3.直接扫描包的形式
 
 *      @ConfigurationPropertiesScan(basePackages = {"com.flx.netty.chat.common.property"})
 *      basePackages为自定义properties文件的位置


