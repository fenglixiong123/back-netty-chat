package com.flx.netty.chat.plugin.plugins.mybatis.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.flx.netty.chat.common.constants.WebConstant;
import com.flx.netty.chat.common.enums.State;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Author: Fenglixiong
 * @Date: 2021/4/23 19:57
 * @Description: 自定义字段填充器
 */
@Component
public class SimpleMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {

        fillValue("state", State.effective, metaObject);
        fillValue("create_user", WebConstant.SYSTEM_USER, metaObject);
        fillValue("update_user", WebConstant.SYSTEM_USER, metaObject);

    }

    @Override
    public void updateFill(MetaObject metaObject) {

        fillValue("update_user", WebConstant.SYSTEM_USER, metaObject);
        fillValue("update_time", new Date(), metaObject);

    }

    /**
     * 当用户给了值就不用填充了
     * @param column 数据库字段
     * @param defValue 填充默认值
     * @param metaObject 其他信息
     */
    private void fillValue(String column,Object defValue,MetaObject metaObject){
        Object value = getFieldValByName(column, metaObject);
        if(value==null){
            this.setFieldValByName(column,defValue,metaObject);
        }
    }

}
