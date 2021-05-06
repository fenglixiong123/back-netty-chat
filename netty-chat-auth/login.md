
# 分布式微服务登录认证

### 参考资料：

https://zhuanlan.zhihu.com/p/86937325


## 传统方式

1.单服务时候（保持全局会话）

Session方式

* 用户登录时：可以将用户登录状态保存在session中也就是服务器内存中（Cookie是用户唯一标识）  
* 用户访问资源时：比对用户header中携带的（Cookie对应的）sessionId是否过期或者有效

2.多服务时候（保持全局和局部会话）

HttpSession方式

* 用户登录时：可以将用户信息保存在redis中，向模块A颁发token令牌以及有效时间等信息
* 用户访问资源时：各个模块会进入sso认证中心进行认证，认证成功获得sso系统颁发的token令牌，再次验证令牌是否有效，
                如果有效则保存令牌创建局部会话，然后则进入资源，认证失败，进入登录页面

## JWT

### 1.介绍

Token存储在客户端（无会话）    
无状态和可扩展性

### 2. 组成

JSON Web Token由三部分组成，它们之间用圆点(.)连接。这三部分分别是：

一个典型的JWT看起来是这个样子的：

header.payload.signature

#### 2.1 Header 头信息

    {
      "alg": "RS256",//加密算法
      "typ": "JWT"//声明类型
    }

#### 2.2 Payload 数据包

    案例：

    {
      "sub": "1234567890",  
      "iat": 1527523017,
      "ip": "127.0.0.1",
      "uuid": "ff1212f5-d8d1-4496-bf41-d2dda73de19a"
    }

    * 标准声明
    
        iss: string; // JWT的签发者
        sub: string; // JWT所面向的用户
        aud: string; // 接收JWT的一方
        exp: number; // JWT的过期时间
        nbf: number; // 在xxx日期之间，该JWT都是可用的
        iat: number; // 该JWT签发的时间
        jti: number; //JWT的唯一身份标识
          
        标准中建议使用，但是不强制
    
    * 公共声明
    
        一般添加用户的相关信息或其他业务需要的必要信息
        
        公共的声明可以添加任何的信息，不建议添加敏感信息，因为该部分在客户端可解密
    
    * 私有声明

        私有声明是 JWT 提供者添加的字段，一样可以被解密，所以也不能存放敏感信息。

#### 2.3 Signature 签名信息

signature 是签证信息，该签证信息是通过header和payload，加上secret，通过算法加密生成。

    * header (base64后的)
    * payload (base64后的)
    * secret

    公式 signature = 加密算法(header + "." + payload, 密钥);


### 3.注意点

#### 3.1 它是如何做身份验证的？

首先，JWT 的 Token 相当是明文，是可以解密的，任何存在 payload 的东西，都没有秘密可言，所以隐私数据不能签发 token。

而服务端，拿到 token 后解密，即可知道用户信息，例如本例中的uuid

有了 uuid，那么你就知道这个用户是谁，是否有权限进行下一步的操作。

### 3.2 Token 的过期时间怎么确定？

payload 中有个标准字段 exp，明确表示了这个 token 的过期时间.

服务端可以拿这个时间与服务器时间作对比，过期则拒绝访问。

### 3.3 如何防止 Token 被串改？

此时 signature字段就是关键了，能被解密出明文的，只有header和payload

假如黑客/中间人串改了payload，那么服务器可以通过signature去验证是否被篡改过。

在服务端在执行一次 signature = 加密算法(header + "." + payload, 密钥);, 然后对比 signature 是否一致，如果一致则说明没有被篡改。

### 3.4 所以为什么说服务器的密钥不能被泄漏？

如果泄漏，将存在以下风险:

客户端可以自行签发 token
黑客/中间人可以肆意篡改 token
安全性相关

### 3.5 如何加强 JWT 的安全性？

根据我的使用，总结以下几点：

* 缩短 token 有效时间
* 使用安全系数高的加密算法
* token 不要放在 Cookie 中，有 CSRF 风险
* 使用 HTTPS 加密协议
* 对标准字段 iss、sub、aud、nbf、exp 进行校验
* 使用成熟的开源库，不要手贱造轮子
* 特殊场景下可以把用户的 UA、IP 放进 payload 进行校验(不推荐)

