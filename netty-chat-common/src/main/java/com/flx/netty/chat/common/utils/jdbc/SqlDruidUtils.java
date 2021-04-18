package com.flx.netty.chat.common.utils.jdbc;

import com.alibaba.druid.pool.DruidDataSource;
import com.flx.netty.chat.common.utils.jdbc.base.SqlBaseUtils;
import lombok.Setter;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import static com.flx.netty.chat.common.utils.jdbc.base.SqlBaseUtils.DEFAULT_DRIVER_CLASS;
import static com.flx.netty.chat.common.utils.jdbc.base.SqlBaseUtils.getLocation;

/**
 * @Author: Fenglixiong
 * @Date: 2021/3/16 12:21
 * @Description:
 */
public class SqlDruidUtils {

    @Setter
    private static DruidDataSource dataSource;//数据源

    static {
        load(getLocation());
    }

    public static void createDataSource(String url,String username,String password) throws SQLException {
        createDataSource(url,username,password,DEFAULT_DRIVER_CLASS);
    }

    public static void createDataSource(String url,String username,String password,String driverClass) throws SQLException {
        dataSource = new DruidDataSource();
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setDriverClassName(driverClass);
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
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
        SqlBaseUtils.SimpleDataSource info = SqlBaseUtils.load(location);
        dataSource = new DruidDataSource();
        dataSource.setUrl(info.getUrl());
        dataSource.setUsername(info.getUsername());
        dataSource.setPassword(info.getPassword());
        dataSource.setDriverClassName(info.getDriverClass());
    }

}
