package com.flx.netty.chat.plugin.plugins.mybatis.base;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.flx.netty.chat.plugin.plugins.mybatis.common.ColumnUtils;
import com.flx.netty.chat.plugin.plugins.mybatis.common.QueryBuilder;
import com.flx.netty.chat.plugin.plugins.mybatis.common.UpdateBuilder;
import com.flx.netty.chat.common.utils.ArrayUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.flx.netty.chat.plugin.plugins.mybatis.common.TableFieldAlias.getTableFiledName;

/**
 * 公共Manager类
 *
 * @author fenglixiong
 * @date 2018-08-09 19:59
 */
@Slf4j
@SuppressWarnings("all")
public abstract class BaseManager<T extends BaseDO, V extends BaseDao> {

    @Value("${com.flx.system.defaultUser:system}")
    private String defaultUser;
    
    @Value("${com.flx.query.maxPageSize:3000}")
    private int maxPageSize;

    @Autowired
    protected V dao;

    private Class<T> modelClass;

    public BaseManager() {
        ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
        modelClass = (Class<T>) pt.getActualTypeArguments()[0];
    }

    protected QueryBuilder<T> getQueryBuilder() {
        return new QueryBuilder<T>();
    }

    protected UpdateBuilder<T> getUpdateBuilder() {
        return new UpdateBuilder<>();
    }


    protected Long add(T model) throws Exception{
        model.setCreateTime(new Date());
        if (StringUtils.isEmpty(model.getCreateUser())) {
            model.setCreateUser(defaultUser);
        }
        if (StringUtils.isEmpty(model.getUpdateUser())) {
            model.setUpdateUser(defaultUser);
        }
        model.setUpdateTime(new Date());
        dao.insert(model);
        return model.getId();
    }

    protected Integer add(List<T> modelList) throws Exception {
        int result = 1;
        for (T model : modelList) {
            model.setCreateTime(new Date());
            model.setUpdateTime(new Date());
            if (StringUtils.isEmpty(model.getCreateUser())) {
                model.setCreateUser(defaultUser);
            }
            if (StringUtils.isEmpty(model.getUpdateUser())) {
                model.setUpdateUser(defaultUser);
            }
            try {
                dao.insert(model);
            } catch (Exception e) {
                log.error("Add model fail,message=" + e.getMessage());
                throw e;
            }
        }
        return result;
    }

    protected Integer delete(Long id) throws Exception {
        return dao.deleteById(id);
    }

    protected Integer delete(QueryWrapper queryWrapper) throws Exception {
        return dao.delete(queryWrapper);
    }

    protected Integer update(T model) throws Exception {
        model.setUpdateTime(new Date());
        if (StringUtils.isEmpty(model.getUpdateUser())) {
            model.setUpdateUser(defaultUser);
        }
        return dao.updateById(model);
    }

    protected Integer updateNull(T model) throws Exception {
        model.setUpdateTime(new Date());
        if (StringUtils.isEmpty(model.getUpdateUser())) {
            model.setUpdateUser(defaultUser);
        }
        UpdateWrapper<T> updateWrapper = getUpdateBuilder()
                .query("id", model.getId())
                .readObject(model).build(true);
        return dao.update(model, updateWrapper);
    }

    protected Integer update(T model, String codeName, String codeValue) throws Exception {
        model.setUpdateTime(new Date());
        if (StringUtils.isEmpty(model.getUpdateUser())) {
            model.setUpdateUser(defaultUser);
        }
        QueryWrapper queryWrapper = getQueryBuilder().query(codeName, codeValue).build(false);
        return dao.update(model, queryWrapper);
    }

    protected Integer updateNull(T model, String codeName, String codeValue) throws Exception {
        model.setUpdateTime(new Date());
        if (StringUtils.isEmpty(model.getUpdateUser())) {
            model.setUpdateUser(defaultUser);
        }
        UpdateWrapper queryWrapper = getUpdateBuilder()
                .query(codeName, codeValue)
                .readObject(model)
                .build(false);
        return dao.update(model, queryWrapper);
    }

    protected int update(T model, QueryWrapper queryWrapper) throws Exception {
        model.setUpdateTime(new Date());
        if (StringUtils.isEmpty(model.getUpdateUser())) {
            model.setUpdateUser(defaultUser);
        }
        return dao.update(model, queryWrapper);
    }

    protected int update(T model, UpdateWrapper updateWrapper) throws Exception {
        model.setUpdateTime(new Date());
        if (StringUtils.isEmpty(model.getUpdateUser())) {
            model.setUpdateUser(defaultUser);
        }
        return dao.update(model, updateWrapper);
    }

    public Object get(Long id) throws Exception {
        return dao.selectById(id);
    }

    public Object get(String keyCode,String keyValue) throws Exception {
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(getTableFiledName(keyCode),keyValue);
        return dao.selectOne(queryWrapper);
    }

    protected IPage<T> queryPage(Integer pageNum, Integer pageSize, Map<String, Object> query) throws Exception {
        Page<T> modelPage = new Page<>(pageNum, pageSize);
        QueryWrapper<T> queryWrapper = getQueryBuilder().query(query).build(true);
        queryWrapper.orderByAsc("id");
        return dao.selectPage(modelPage, queryWrapper);
    }

    protected IPage<T> queryPage(Integer pageNum, Integer pageSize, Object query) throws Exception {
        Page<T> modelPage = new Page<>(pageNum, pageSize);
        QueryWrapper<T> queryWrapper = getQueryBuilder().query(query).build(false);
        queryWrapper.orderByAsc("id");
        return dao.selectPage(modelPage, queryWrapper);
    }

    protected IPage<T> queryPageSome(Integer pageNum, Integer pageSize, Map<String, Object> query,String[] columns) throws Exception {
        Page<T> modelPage = new Page<>(pageNum, pageSize);
        QueryWrapper<T> queryWrapper = getQueryBuilder().query(query).build(true);
        if (ArrayUtils.isNotNull(columns)) {
            queryWrapper.select(columns);
        }
        queryWrapper.orderByAsc("id");
        return dao.selectPage(modelPage, queryWrapper);
    }

    protected IPage<T> queryPageSome(Integer pageNum, Integer pageSize, Object query,String[] columns) throws Exception {
        Page<T> modelPage = new Page<>(pageNum, pageSize);
        QueryWrapper<T> queryWrapper = getQueryBuilder().query(query).build(false);
        if (ArrayUtils.isNotNull(columns)) {
            queryWrapper.select(columns);
        }
        queryWrapper.orderByAsc("id");
        return dao.selectPage(modelPage, queryWrapper);
    }

    protected List<T> query(Object query) throws Exception {
        Page<T> modelPage = new Page<>(1, maxPageSize);
        QueryWrapper<T> queryWrapper = getQueryBuilder().query(query).build(false);
        queryWrapper.orderByAsc("id");
        queryWrapper.select(ColumnUtils.columnReject(modelClass));
        IPage<T> modelDOList = dao.selectPage(modelPage, queryWrapper);
        if (modelDOList == null || modelDOList.getRecords()==null) {
            return new ArrayList<>();
        }
        return modelDOList.getRecords();
    }

    protected List<T> query(Map<String, Object> query) throws Exception {
        Page<T> modelPage = new Page<>(1, maxPageSize);
        QueryWrapper<T> queryWrapper = getQueryBuilder().query(query).build(false);
        queryWrapper.orderByAsc("id");
        queryWrapper.select(ColumnUtils.columnReject(modelClass));
        IPage<T> modelDOList = dao.selectPage(modelPage, queryWrapper);
        if (modelDOList == null || modelDOList.getRecords()==null) {
            return new ArrayList<>();
        }
        return modelDOList.getRecords();
    }

    protected List<T> querySome(Object query, String[] columns) throws Exception {
        Page<T> modelPage = new Page<>(1, maxPageSize);
        QueryWrapper<T> queryWrapper = getQueryBuilder().query(query).build(false);
        queryWrapper.orderByAsc("id");
        if (ArrayUtils.isNotNull(columns)) {
            queryWrapper.select(columns);
        }
        IPage<T> modelDOList = dao.selectPage(modelPage, queryWrapper);
        if (modelDOList == null || modelDOList.getRecords()==null) {
            return new ArrayList<>();
        }
        return modelDOList.getRecords();
    }

    protected List<T> querySome(Map<String, Object> query, String[] columns) throws Exception {
        Page<T> modelPage = new Page<>(1, maxPageSize);
        QueryWrapper<T> queryWrapper = getQueryBuilder().query(query).build(false);
        queryWrapper.orderByAsc("id");
        if (ArrayUtils.isNotNull(columns)) {
            queryWrapper.select(columns);
        }
        IPage<T> modelDOList = dao.selectPage(modelPage, queryWrapper);
        if (modelDOList == null || modelDOList.getRecords()==null) {
            return new ArrayList<>();
        }
        return modelDOList.getRecords();
    }

    protected List<T> queryAll(Object query) throws Exception {
        Page<T> modelPage = new Page<>(1, maxPageSize);
        QueryWrapper<T> queryWrapper = getQueryBuilder().query(query).build(false);
        queryWrapper.orderByAsc("id");
        IPage<T> modelDOList = dao.selectPage(modelPage, queryWrapper);
        if (modelDOList == null || modelDOList.getRecords()==null) {
            return new ArrayList<>();
        }
        return modelDOList.getRecords();
    }

    protected List<T> queryAll(Map<String, Object> query) throws Exception {
        Page<T> modelPage = new Page<>(1, maxPageSize);
        QueryWrapper<T> queryWrapper = getQueryBuilder().query(query).build(false);
        queryWrapper.orderByAsc("id");
        IPage<T> modelDOList = dao.selectPage(modelPage, queryWrapper);
        if (modelDOList == null || modelDOList.getRecords()==null) {
            return new ArrayList<>();
        }
        return modelDOList.getRecords();
    }

    public boolean isExist(Long id) throws Exception {
        QueryWrapper<T> queryWrapper = getQueryBuilder().query("id", id).build(false);
        return dao.selectCount(queryWrapper)>0;
    }

    public boolean isExist(String keyCode,String keyValue) throws Exception {
        QueryWrapper<T> queryWrapper = getQueryBuilder().query(keyCode, keyValue).build(false);
        return dao.selectCount(queryWrapper)>0;
    }

}
