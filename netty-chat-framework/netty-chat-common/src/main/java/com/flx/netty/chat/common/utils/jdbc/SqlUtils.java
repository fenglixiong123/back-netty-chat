package com.flx.netty.chat.common.utils.jdbc;


import com.flx.netty.chat.common.utils.jdbc.base.SqlBaseUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import static com.flx.netty.chat.common.utils.jdbc.base.SqlBaseUtils.getLocation;


/**
 * @Author: Fenglixiong
 * @Date: 2021/3/16 12:06
 * @Description:
 */
public class SqlUtils {

    private static String url;//数据库的路径
    private static String username;//数据库的登录名
    private static String password;//数据据库的登录密码

    static {
        load(getLocation());
    }

    @Data
    @AllArgsConstructor
    public static class Executor{

        private String url;//数据库的路径
        private String username;//数据库的登录名
        private String password;//数据据库的登录密码

        private Connection getConnection() throws Exception {
            return SqlUtils.getConnection(url,username,password);
        }

        public <T> T queryOne(String sql, Class<T> c) throws Exception{
            return SqlUtils.queryOne(getConnection(),sql,null,c);
        }

        public <T> T queryOne(String sql, List<Object> params, Class<T> c) throws Exception{
            return SqlUtils.queryOne(getConnection(),sql,params,c);
        }

        public <T> List<T> queryList(String sql,Class<T> c) throws Exception{
            return SqlUtils.queryList(getConnection(),sql,null,c);
        }

        public <T> List<T> queryList(String sql,List<Object> params,Class<T> c) throws Exception{
            return SqlUtils.queryList(getConnection(),sql,params,c);
        }

        public Map<String,Object> queryMap(String sql)throws Exception{
            return SqlUtils.queryMap(getConnection(),sql,null);
        }

        public Map<String,Object> queryMap(String sql, List<Object> params)throws Exception{
            return SqlUtils.queryMap(getConnection(),sql,params);
        }

        public List<Map<String,Object>> queryMapList(String sql)throws Exception{
            return SqlUtils.queryMapList(getConnection(),sql,null);
        }

        public List<Map<String,Object>> queryMapList(String sql,List<Object> params)throws Exception{
            return SqlUtils.queryMapList(getConnection(),sql,params);
        }

        public int update(String sql)throws Exception {
            return SqlUtils.update(getConnection(),sql,null);
        }

        public int update(String sql, List<Object> params)throws Exception {
            return SqlUtils.update(getConnection(),sql,params);
        }

        public boolean execute(String sql) throws Exception {
            return SqlUtils.execute(getConnection(),sql,null);
        }

        public boolean execute(String sql, List<Object> params) throws Exception {
            return SqlUtils.execute(getConnection(),sql,params);
        }

        public boolean executeTrans(List<SqlBaseUtils.Command> commands)throws Exception{
            return SqlUtils.executeWithTransaction(getConnection(),commands);
        }

        public int[] executeBatch(List<String> sqlList)throws Exception{
            return SqlUtils.executeBatch(getConnection(),sqlList);
        }

        public int[] executeBatch(String sql,List<List<Object>> params)throws Exception{
            return SqlUtils.executeBatch(getConnection(),sql,params);
        }

    }

    /**
     * 创建数据源
     * @param url 连接地址
     * @param username 用户名
     * @param password 密码
     * @return
     */
    public static Executor buildExecutor(String url,String username,String password){
        return new Executor(url,username,password);
    }

    /**
     * 创建数据源
     * @param ip IP地址
     * @param port 数据库端口
     * @param database 数据库名字
     * @param username 用户名
     * @param password 密码
     * @return 数据源
     */
    public static Executor buildExecutor(String ip,int port,String database,String username,String password){
        String url = "jdbc:mysql://" + ip + ":" + port + "/" + database + "?serverTimezone=UTC&useUnicode=true&characterEncoding=utf8&useSSL=false";
        return new Executor(url,username,password);
    }



    public static Connection getConnection() throws Exception {
        return SqlBaseUtils.getConnection(url,username,password);
    }

    public static Connection getConnection(String location) throws Exception {
        return SqlBaseUtils.getConnection(location);
    }

    public static Connection getConnection(String url,String username,String password) throws Exception {
        return SqlBaseUtils.getConnection(url,username,password);
    }

    public static Map<String,Object> queryMap(String sql, List<Object> params)throws Exception{
        return SqlBaseUtils.queryMap(getConnection(),sql,params);
    }

    public static Map<String,Object> queryMap(Connection connection, String sql, List<Object> params)throws Exception{
        return SqlBaseUtils.queryMap(connection,sql,params);
    }

    public static List<Map<String,Object>> queryMapList(String sql,List<Object> params)throws Exception{
        return SqlBaseUtils.queryMaps(getConnection(),sql,params);
    }

    public static List<Map<String,Object>> queryMapList(Connection connection,String sql,List<Object> params)throws Exception{
        return SqlBaseUtils.queryMaps(connection,sql,params);
    }

    public static <T> T queryOne(String sql, List<Object> params, Class<T> c)throws Exception{
        return SqlBaseUtils.queryOne(getConnection(),sql,params,c);
    }

    public static <T> T queryOne(Connection connection,String sql, List<Object> params, Class<T> c)throws Exception{
        return SqlBaseUtils.queryOne(connection,sql,params,c);
    }

    public static <T> List<T> queryList(String sql,List<Object> params,Class<T> c)throws Exception{
        return SqlBaseUtils.queryList(getConnection(),sql,params,c);
    }

    public static <T> List<T> queryList(Connection connection,String sql,List<Object> params,Class<T> c)throws Exception{
        return SqlBaseUtils.queryList(connection,sql,params,c);
    }

    public static int update(String sql, List<Object> params)throws Exception {
        return SqlBaseUtils.update(getConnection(),sql,params);
    }

    public static int update(Connection connection,String sql, List<Object> params)throws Exception {
        return SqlBaseUtils.update(connection,sql,params);
    }

    public static boolean execute(String sql, List<Object> params)throws Exception {
        return SqlBaseUtils.execute(getConnection(),sql,params);
    }

    public static boolean execute(Connection connection,String sql, List<Object> params)throws Exception {
        return SqlBaseUtils.execute(connection,sql,params);
    }

    public static int[] executeBatch(List<String> sqlList)throws Exception{
        return SqlBaseUtils.executeBatch(getConnection(),sqlList);
    }

    public static int[] executeBatch(Connection connection,List<String> sqlList)throws Exception{
        return SqlBaseUtils.executeBatch(connection,sqlList);
    }

    public static int[] executeBatch(String sql,List<List<Object>> params)throws Exception{
        return SqlBaseUtils.executeBatch(getConnection(),sql,params);
    }

    public static int[] executeBatch(Connection connection,String sql,List<List<Object>> params)throws Exception{
        return SqlBaseUtils.executeBatch(connection,sql,params);
    }

    public static boolean executeWithTransaction(List<SqlBaseUtils.Command> commands)throws Exception{
        return SqlBaseUtils.executeWithTransaction(getConnection(),commands);
    }

    public static boolean executeWithTransaction(Connection connection,List<SqlBaseUtils.Command> commands)throws Exception{
        return SqlBaseUtils.executeWithTransaction(connection,commands);
    }

    public static void load(String location){
        SqlBaseUtils.SimpleDataSource dataSource = SqlBaseUtils.load(location);
        url = dataSource.getUrl();
        username = dataSource.getUsername();
        password = dataSource.getPassword();
    }

}
