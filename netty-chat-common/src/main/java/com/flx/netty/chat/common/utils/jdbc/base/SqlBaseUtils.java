package com.flx.netty.chat.common.utils.jdbc.base;

import com.flx.springboot.scaffold.common.utils.ObjectUtils;
import com.flx.springboot.scaffold.common.utils.code.CodeUtils;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.*;

/**
 * @Author: Fenglixiong
 * @Date: 2021/3/16 12:18
 * @Description: SQL基础操作原
 */
public class SqlBaseUtils {

    public static String KEY_URL = "db.url";//数据库的路径
    public static String KEY_USERNAME = "db.username";//数据库的登录名
    public static String KEY_PASSWORD = "db.password";//数据据库的登录密码
    public static String KEY_DRIVER_CLASS = "db.driverClass";//加载驱动时的路径

    private static String DEFAULT_LOCATION = "application.properties";//数据库连接文件地址

    public static String DEFAULT_DRIVER_CLASS = "com.mysql.cj.jdbc.Driver";//默认driverClass

    public static void setLocation(String location){
        DEFAULT_LOCATION = location;
    }

    public static String getLocation(){
        return DEFAULT_LOCATION;
    }

    /**
     * 获取数据库连接
     */
    public static Connection getConnection() throws Exception{
        return getConnection(DEFAULT_LOCATION);
    }

    /**
     * 获取数据库连接
     */
    public static Connection getConnection(String location) throws Exception {
        SimpleDataSource info = load(location);
        return DriverManager.getConnection(info.getUrl(),info.getUsername(),info.getPassword());
    }

    /**
     * 获取数据库连接
     */
    public static Connection getConnection(String url,String username,String password) throws Exception {
        return getConnection(url,username,password,DEFAULT_DRIVER_CLASS);
    }

    /**
     * 获取数据库连接
     */
    public static Connection getConnection(String url,String username,String password,String driverClass) throws Exception {
        Class.forName(driverClass);
        return DriverManager.getConnection(url,username,password);
    }

    /**
     * 查询单个map结果
     */
    public static Map<String,Object> queryMap(Connection con,String sql,List<Object> params)throws Exception{
        List<Map<String,Object>> resultMap = queryMaps(con,sql,params);
        return resultMap==null ? null : resultMap.get(0);
    }

    /**
     * 查询map结果
     */
    public static List<Map<String,Object>> queryMaps(Connection con,String sql,List<Object> params)throws Exception{
        Objects.requireNonNull(con);
        Objects.requireNonNull(sql);
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Map<String,Object>> resultList = new ArrayList<>();
        try{
            ps = con.prepareStatement(sql);
            if(params!=null) {
                for (int i = 0; i < params.size(); i++) {
                    ps.setObject(i + 1, params.get(i));
                }
            }
            rs = ps.executeQuery();
            ResultSetMetaData rsm = rs.getMetaData();
            int column = rsm.getColumnCount();
            while (rs.next()){
                Map<String,Object> row = new HashMap<>();
                for (int i=1;i<=column;i++){
                    String name = CodeUtils.underToCamel(rsm.getColumnName(i));
                    Object value = rs.getObject(i);
                    row.put(name,value);
                }
                resultList.add(row);
            }
            return resultList;
        }finally {
            close(con,ps,rs);
        }
    }

    /**
     * 通用查询接口
     */
    public static <T> T queryOne(Connection con,String sql,List<Object> params,Class<T> c)throws Exception{
        List<T> result = queryList(con, sql, params, c);
        return result==null ? null : result.get(0);
    }

    /**
     * 通用查询接口
     */
    public static <T> List<T> queryList(Connection con,String sql,List<Object> params,Class<T> c)throws Exception{
        Objects.requireNonNull(con);
        Objects.requireNonNull(sql);
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<T> resultList = new ArrayList<>();
        try{
            ps = con.prepareStatement(sql);
            if(params!=null) {
                for (int i = 0; i < params.size(); i++) {
                    ps.setObject(i + 1, params.get(i));
                }
            }
            rs = ps.executeQuery();
            ResultSetMetaData rsm = rs.getMetaData();
            int column = rsm.getColumnCount();
            while (rs.next()){
                T o = c.newInstance();
                for (int i=1;i<=column;i++){
                    String name = CodeUtils.underToCamel(rsm.getColumnName(i));
                    Object value = rs.getObject(i);
                    ObjectUtils.setFieldByNameIgnoreException(o,name,value);
                }
                resultList.add(o);
            }
            return resultList;
        }finally {
            close(con,ps,rs);
        }
    }

    /**
     * 通用更新操作
     */
    public static int update(Connection con,String sql, List<Object> params)throws Exception {
        Objects.requireNonNull(con);
        Objects.requireNonNull(sql);
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(sql);
            if(params!=null) {
                for (int i = 0; i < params.size(); i++) {
                    ps.setObject(i + 1, params.get(i));
                }
            }
            return ps.executeUpdate();
        }finally {
            close(con,ps);
        }
    }

    /**
     * 通用操作
     */
    public static boolean execute(Connection con,String sql, List<Object> params)throws Exception {
        Objects.requireNonNull(con);
        Objects.requireNonNull(sql);
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(sql);
            if(params!=null) {
                for (int i = 0; i < params.size(); i++) {
                    ps.setObject(i + 1, params.get(i));
                }
            }
            return ps.execute();
        }finally {
            close(con,ps);
        }
    }

    /**
     * 1.Statement它更适合执行不同sql的批处理，它没有提供预处理功能，性能比较低。
     * 2.PreparedStatement它适合执行相同的批处理，它提供了预处理功能，属性比较高。
     * @param sqlList insert into user values ();
     * @throws Exception
     */
    public static int[] executeBatch(Connection con,List<String> sqlList)throws Exception{
        Objects.requireNonNull(con);
        Objects.requireNonNull(sqlList);
        Statement st = null;
        try {
            st = con.createStatement();
            for (String sql : sqlList){
                st.addBatch(sql);
            }
            int[] ret = st.executeBatch();
            st.clearBatch();
            return ret;
        }finally {
            close(con,st);
        }
    }

    /**
     * 批量执行新增或者更新操作，对相同数据操作
     * @param sql
     * @param params
     * @return
     * @throws Exception
     */
    public static int[] executeBatch(Connection con,String sql,List<List<Object>> params)throws Exception{
        Objects.requireNonNull(con);
        Objects.requireNonNull(sql);
        if(params==null){
            params = new ArrayList<>();
        }
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(sql);
            for (int i = 0; i < params.size(); i++) {
                List<Object> rows = params.get(i);
                for (int j = 0; j < rows.size(); j++) {
                    ps.setObject(j+1,rows.get(j));
                }
                ps.addBatch();
                if(i%1000==0){
                    ps.executeBatch();
                    ps.clearBatch();
                }
            }
            int[] ret = ps.executeBatch();
            ps.clearBatch();
            return ret;
        }finally {
            close(con,ps);
        }
    }

    /**
     * 带事务的批量更新
     * @param con
     * @param commands
     */
    public static boolean executeWithTransaction(Connection con,List<Command> commands){
        Objects.requireNonNull(con);
        Objects.requireNonNull(commands);
        List<PreparedStatement> pss = new ArrayList<>();
        try {
            con.setAutoCommit(false);
            for (Command command : commands) {
                PreparedStatement ps = con.prepareStatement(command.getSql());
                List<Object> params = command.getParams();
                if(params!=null){
                    for (int i = 0; i < params.size(); i++) {
                        ps.setObject(i+1,params.get(i));
                    }
                }
                pss.add(ps);
                if(ps.executeUpdate()<0){
                    throw new RuntimeException("Bad execute and will rollback !");
                }
            }
            return true;
        }catch (Exception e){
            try {
                con.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return false;
        }finally {
            try {
                con.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            pss.forEach(SqlBaseUtils::close);
        }
    }

    public static void close(Connection con){
        close(con,null,null,null);
    }

    public static void close(Statement st){
        close(null,null,st,null);
    }

    public static void close(PreparedStatement ps){
        close(null,ps,null,null);
    }

    public static void close(Connection con, Statement st){
        close(con,null,st,null);
    }

    public static void close(Connection con, PreparedStatement ps){
        close(con,ps,null,null);
    }

    public static void close(Connection con, PreparedStatement ps, ResultSet rs){
        close(con,ps,null,rs);
    }

    /**
     * 释放资源
     * @param con 连接源
     * @param ps 预编译
     * @Param rs 结果集
     */
    @SuppressWarnings("all")
    public static void close(Connection con, PreparedStatement ps, Statement st, ResultSet rs){
        if(con!=null){
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(ps!=null){
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(st!=null){
            try {
                st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(rs!=null){
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static SimpleDataSource load(){
        return load(DEFAULT_LOCATION);
    }

    public static SimpleDataSource load(String location){
        //1.加载配置文件
        Objects.requireNonNull(location);
        InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(location);
        Properties pr = new Properties();
        try {
            pr.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //2.读取配置信息
        String username = pr.getProperty(KEY_USERNAME);
        String password = pr.getProperty(KEY_PASSWORD);
        String url = pr.getProperty(KEY_URL);
        String driverClass = pr.getProperty(KEY_DRIVER_CLASS);
        if(driverClass == null){
            driverClass = DEFAULT_DRIVER_CLASS;
        }
        //3.加载驱动
        try {
            Class.forName(driverClass);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return SimpleDataSource.of(url,username,password,driverClass);
    }

    @Data
    @AllArgsConstructor
    public static class SimpleDataSource{

        private String url;
        private String username;
        private String password;
        private String driverClass;

        public static SimpleDataSource of(String url,String username,String password,String driverClass){
            return new SimpleDataSource(url,username,password,driverClass);
        }
    }

    public static Command builder(){
        return Command.build();
    }

    public static Command builder(String sql,List<Object> params){
        return Command.build().sql(sql).params(params);
    }

    @Data
    public static class Command{
        private String sql;
        private List<Object> params;

        public static Command build(){
            return new Command();
        }
        public Command sql(String sql){
            this.sql = sql;
            return this;
        }
        public Command params(List<Object> params){
            this.params = params;
            return this;
        }
        public Command params(Object ...params){
            this.params = Arrays.asList(params);
            return this;
        }
    }

}
