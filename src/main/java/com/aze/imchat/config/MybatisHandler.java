package com.aze.imchat.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;

@Component
public class MybatisHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        //属性名
        this.strictInsertFill(metaObject, "gmtCreate", Date.class, new Date());
        this.strictInsertFill(metaObject, "gmtModified", Date.class, new Date());


        //this.setFieldValByName("createUser", SecureUtil.getUserId(), metaObject);
        //不维护create_user可以不使用这行代码
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        //属性名
        this.setFieldValByName("gmtModified", new Date(), metaObject);
        //this.setFieldValByName("updateUser", SecureUtil.getUserId(), metaObject);
    }
}
