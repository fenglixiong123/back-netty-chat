package com.flx.netty.chat.generator.service;

import com.flx.netty.chat.common.utils.CollectionUtils;
import com.flx.netty.chat.common.utils.jdbc.SqlUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: Fenglixiong
 * @Date: 2021/4/27 14:04
 * @Description:
 */
@Slf4j
@Service
public class TableService {

    /**
     * 获取所有的表
     * @param ip 数据库IP地址
     * @param port 数据库端口
     * @param database 数据库名字
     * @param username 用户名
     * @param password 密码
     * @return 所有的表
     * @throws Exception
     */
    public List<Object> getTables(String ip,int port,String database,String username,String password) throws Exception {
        String sql = "select * from information_schema.tables where table_schema='"+database+"'";
        List<Map<String, Object>> mapList = SqlUtils.buildExecutor(ip, port, database, username, password).queryMapList(sql);
        if(CollectionUtils.isNotEmpty(mapList)){
            List<Object> tables = mapList.stream().map(e -> e.get("TABLENAME")).collect(Collectors.toList());
            tables.remove("flyway_history");
            return tables;
        }
        return new ArrayList<>();
    }

}
