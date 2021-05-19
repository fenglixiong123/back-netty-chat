package com.flx.netty.chat.admin.common.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author: Fenglixiong
 * @Date: 2021/5/12 19:44
 * @Description:
 */
@Data
@ConfigurationProperties(prefix = "spring.datasource")
public class DruidProperties {

    /**
     * 数据库地址
     */
    private String url = "jdbc:mysql://127.0.0.1:3306/web_auth?serverTimezone=UTC&useUnicode=true&characterEncoding=utf8&useSSL=false";

    /**
     * 用户名
     */
    private String username = "root";

    /**
     * 密码
     */
    private String password = "root123";

    /**
     * 数据库驱动包
     */
    private String driverClassName = "com.mysql.cj.jdbc.Driver";

    /**
     * 初始化连接数量
     */
    private int initialSize = 5;

    /**
     * 最小连接池数量
     */
    private int minIdle = 5;

    /**
     * 最大连接池数量
     */
    private int maxActive = 20;

    /**
     * 获取连接时最大等待时间，单位毫秒。设置-1时表示无限等待,设置为1分钟
     */
    private int maxWait = 60000;

    /**
     * 检查空闲连接的频率，单位毫秒, 非正整数时表示不进行检查
     */
    private int timeBetweenEvictionRunsMillis = 60000;

    /**
     * 池中某个连接的空闲时长达到 N 毫秒后, 连接池在下次检查空闲连接时，将回收该连接,要小于防火墙超时设置
     */
    private int minEvictableIdleTimeMillis = 120000;

    private int maxEvictableIdleTimeMillis = 480000;

    /**
     * 用来检测连接是否有效的sql，要求是一个查询语句。
     * 如果validationQuery为null，testOnBorrow、testOnReturn、testWhileIdle都不会其作用。
     */
    private String validationQuery = "select 1";

    /**
     * 当程序请求连接，池在分配连接时，是否先检查该连接是否有效。(高效)
     * 申请连接的时候检测，如果空闲时间大于timeBetweenEvictionRunsMillis，执行validationQuery检测连接是否有效。
     */
    private boolean testWhileIdle = true;

    /**
     * 程序申请连接时,进行连接有效性检查（低效，影响性能）
     */
    private boolean testOnBorrow = false;

    /**
     * 程序返还连接时,进行连接有效性检查（低效，影响性能）
     */
    private boolean testOnReturn = false;

    /**
     * 保持连接池活动状态
     */
    private boolean keepAlive = true;

    /**
     * 要求程序从池中get到连接后, N 秒后必须close,否则druid 会强制回收该连接,
     * 不管该连接中是活动还是空闲, 以防止进程不会进行close而霸占连接。
     */
    private boolean removeAbandoned = false;

    /**
     * 设置druid 强制回收连接的时限，当程序从池中get到连接开始算起，超过此值后，druid将强制回收该连接，单位秒。设置为30分钟
     */
    private int removeAbandonedTimeout = 1800;

    /**
     * 当druid强制回收连接后，是否将stack trace记录到日志中
     */
    private boolean logAbandoned = true;

    /**
     * 缓存通过以下两个方法发起的SQL:
     * public PreparedStatement prepareStatement(String sql);
     * public PreparedStatement prepareStatement(String sql,int resultSetType, int resultSetConcurrency);
     */
    private boolean poolPreparedStatements = true;

    /**
     * 每个连接最多缓存多少个SQL
     */
    private int maxPoolPreparedStatementPerConnectionSize = 20;

    private int maxOpenPreparedStatements = 100;

    /**
     * 连接属性。比如设置一些连接池统计方面的配置。
     * druid.stat.mergeSql=true;druid.stat.slowSqlMillis=5000
     * 比如设置一些数据库连接属性
     */
    private String connectionProperties = "druid.stat.mergeSql=true;druid.stat.slowSqlMillis=2000";

    /**
     * 监控统计: filter:stat
     * 日志监控: filter:log4j
     * 防御SQL注入: filter:wall
     */
    private String filters = "stat,wall,log4j";

}
