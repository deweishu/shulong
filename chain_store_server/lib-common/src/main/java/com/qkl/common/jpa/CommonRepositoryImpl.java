package com.qkl.common.jpa;

import com.qkl.common.bean.UUIDEntity;
import com.qkl.common.dto.ILoad;
import com.qkl.common.page.Page;
import com.qkl.common.util.CollectionUtil;
import com.qkl.common.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;


public class CommonRepositoryImpl {

    private static Logger log = LoggerFactory.getLogger(CommonRepositoryImpl.class);

    @Autowired
    private EntityManager entityManager;

    public int executeUpdate(String sql,Map<String,Object> params) {
        Query query = createNativeQuery(sql,params);
        return query.executeUpdate();
    }

    public <T extends UUIDEntity> T findOne(String id, Class<T> clazz) {
        if (StringUtil.isNotNil(id)) {
            String hql = "SELECT a FROM " + clazz.getName() + " a WHERE a.id = :id";
            Query q = entityManager.createQuery(hql);
            q.setParameter("id", id);
            return (T) q.getSingleResult();
        }else{
            return null;
        }
    }

    public <T extends UUIDEntity> List<T> findAll(Class<T> clazz) {
        String hql = "SELECT a FROM " + clazz.getName() + " a";
        Query q = entityManager.createQuery(hql);
        return q.getResultList();
    }

    public List<Object> callFunction(String sql, Map<String,Object> params){
        Query q = entityManager.createNativeQuery(sql);
        if(params != null && params.size() > 0){
            for (String key : params.keySet()) {
                Object o = params.get(key);
                q.setParameter(key, o);
            }
        }
        return q.getResultList();
    }


    private Query createNativeQuery(String sql, Map<String,Object> params){
        Assert.isTrue(sql != null ,"传入的sql不能为null");
        Query query = entityManager.createNativeQuery(sql);
        if(params != null && params.size() > 0) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                String paramName = entry.getKey();
                Object paramValue = entry.getValue();
                if (paramValue instanceof Collection)
                    query.setParameter(paramName, (Collection) paramValue);
                else if (paramValue instanceof Object[])
                    query.setParameter(paramName, (Object[]) paramValue);
                else
                    query.setParameter(paramName, paramValue);
            }
        }
        return query;
    }

    /** 根据结果集组装DTO*/
    private <X extends ILoad> List<X> loadList(List<Object[]> rows, Class<X> dtoClazz) {
        final List<X> result = new ArrayList<X>();
        ILoad dto = null;
        try {
            dto = dtoClazz.newInstance();
        } catch (Throwable e) {
            String errorMessage = MessageFormat.format("初始化Dto对象(class={0})时发生异常: {1}", dtoClazz.getName(), e.getMessage());
            log.error(errorMessage, e);
            throw new Error(errorMessage, e);
        }
        if(CollectionUtil.isNotNil(rows)) {
            for (final Object[] row : rows) {
                result.add((X) dto.load(row));
            }
        }
        return result;
    }

    /** 查询总记录条数 */
    private Long findCount(String sql, Map<String, Object> parameters) {
        sql = "SELECT COUNT(1) FROM (" + sql + ") cunpiao_temptable";
        Query query = createNativeQuery(sql,parameters);

        return Long.valueOf(query.getSingleResult().toString());
    }


    public <T extends ILoad> List<T> findNativeSQL(String sql, Map<String,Object> params, Class<T> dtoClazz){
        Query query = createNativeQuery(sql,params);
        List<Object[]> objects = query.getResultList();
        return loadList(objects,dtoClazz);
    }

    public <T extends ILoad> Page<T> findNativeSQL(Page page, String sql, Map<String,Object> params, Class<T> dtoClazz){
        if(page == null){ //不分页
           List<T> list = findNativeSQL(sql, params, dtoClazz);
           Page p = new Page();
           p.setResult(list);
           p.setTotalCount(list.size());
           return p;
        }else{ // 进行分页

            Map<String,Object> parameters = (params == null ? page.getParameters() : params);
            if(page.isAutoCount()){
                page.setTotalCount(findCount(sql,parameters));
            }
            int first = (page.getCurrent() - 1) * page.getPageSize(); // 记录开始位置
            int maxResut = page.getPageSize();

            Query query = createNativeQuery(sql,parameters);
            query.setFirstResult(first);
            query.setMaxResults(maxResut);
            List<Object[]> objects = query.getResultList();
            page.setResult(loadList(objects, dtoClazz));
            return page;
        }
    }

}
