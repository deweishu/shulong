package com.qkl.common.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.qkl.common.util.BeanUtil;
import com.qkl.common.util.JsonUtil;
import com.qkl.common.util.StringUtil;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.usertype.UserType;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

/**
 * 转json存储的属性
 * Created by dengjihai on 2018/3/28.
 */
public class JsonableProperty implements Serializable,UserType {

    protected int[] TYPES = {Types.VARCHAR};

    @Override
    public int[] sqlTypes() {
        return TYPES;
    }

    @Override
    public Class returnedClass() {
        return getClass();
    }

    @Override
    public boolean equals(Object x, Object y) throws HibernateException {
        if (x == null && y == null) {
            return true;
        }
        if (x == null) {
            return false;
        }
        return x.equals(y);
    }

    @Override
    public int hashCode(Object x) throws HibernateException {
        Assert.isTrue(x != null, "");
        return x.hashCode();
    }

    @Override
    public Object nullSafeGet(ResultSet rs, String[] names, SessionImplementor session, Object owner) throws HibernateException, SQLException {
        String json = rs.getString(names[0]);
        if(StringUtil.isNil(json)) {
            try {
                return getClass().newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return JsonUtil.jsonToBean(json,getClass());
    }

    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index, SessionImplementor session) throws HibernateException, SQLException {
        if(value == null) {
            StandardBasicTypes.STRING.nullSafeSet(st,null,index,session);
        } else {
            StandardBasicTypes.STRING.nullSafeSet(st,JsonUtil.beanToJson(value),index,session);
        }
    }

    @Override
    public Object deepCopy(Object value) throws HibernateException {
        return BeanUtil.deepCopy(value);
    }

    @Override
    @JsonIgnore
    public boolean isMutable() {
        return false;
    }

    @Override
    public Serializable disassemble(Object value) throws HibernateException {
        return (Serializable) value;
    }

    @Override
    public Object assemble(Serializable cached, Object owner) throws HibernateException {
        return cached;
    }

    @Override
    public Object replace(Object original, Object target, Object owner) throws HibernateException {
        return original;
    }

}
