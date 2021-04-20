package com.flx.netty.chat.common.utils.jdbc;


import com.flx.netty.chat.common.utils.jdbc.base.SqlBaseUtils;

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

    public static void createDataSource(String url,String username,String password){
        SqlUtils.url = url;
        SqlUtils.username = username;
        SqlUtils.password = password;
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

    public static Map<String,Object> queryMap(Connection con, String sql, List<Object> params)throws Exception{
        return SqlBaseUtils.queryMap(getConnection(),sql,params);
    }

    public static List<Map<String,Object>> queryMaps(Connection con,String sql,List<Object> params)throws Exception{
        return SqlBaseUtils.queryMaps(con,sql,params);
    }

    public static <T> T queryOne(String sql, List<Object> params, Class<T> c)throws Exception{
        return SqlBaseUtils.queryOne(getConnection(),sql,params,c);
    }

    public static <T> List<T> queryList(String sql,List<Object> params,Class<T> c)throws Exception{
        return SqlBaseUtils.queryList(getConnection(),sql,params,c);
    }

    public static int update(String sql, List<Object> params)throws Exception {
        return SqlBaseUtils.update(getConnection(),sql,params);
    }

    public static boolean execute(String sql, List<Object> params)throws Exception {
        return SqlBaseUtils.execute(getConnection(),sql,params);
    }

    public static int[] executeBatch(List<String> sqlList)throws Exception{
        return SqlBaseUtils.executeBatch(getConnection(),sqlList);
    }

    public static int[] executeBatch(String sql,List<List<Object>> params)throws Exception{
        return SqlBaseUtils.executeBatch(getConnection(),sql,params);
    }

    public static boolean executeWithTransaction(Connection con,List<SqlBaseUtils.Command> commands){
        return SqlBaseUtils.executeWithTransaction(con,commands);
    }

    public static void load(String location){
        SqlBaseUtils.SimpleDataSource dataSource = SqlBaseUtils.load(location);
        url = dataSource.getUrl();
        username = dataSource.getUsername();
        password = dataSource.getPassword();
    }

}
