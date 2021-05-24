
# Feign使用okHttpClient作为客户端

## 添加maven依赖

    <dependency>
        <groupId>io.github.openfeign</groupId>
        <artifactId>feign-okhttp</artifactId>
    </dependency>
    
## 修改配置文件

    feign:
      httpclient:
        enabled: false
      okhttp:
        enabled: true
        
## 配置类

在配置类中将 OkHttpClient 注入 Spring 的容器中，这里我们指定了连接池的大小，最大保持连接数为 10 ，并且在 5 分钟不活动之后被清除。

    @Bean
    public OkHttpClient okHttpClient(){
        return new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .connectionPool(new ConnectionPool(10 , 5L, TimeUnit.MINUTES))
                .addInterceptor(new OkHttpLogInterceptor())
                .build();
    }   
    
## 添加日志拦截器

* 这里实现的接口是 okhttp3.Interceptor ，并不是 Spring Boot 中的 Interceptor。
* 这里仅简单打印了 okhttp 请求的路径，如果有业务校验权限等需求可以放在拦截器中实现。

    @Slf4j
    public class OkHttpLogInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            log.info("OkHttpUrl : " + chain.request().url());
            return chain.proceed(chain.request());
        }
    }    
    
             