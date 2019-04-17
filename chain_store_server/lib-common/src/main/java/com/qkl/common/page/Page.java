package com.qkl.common.page;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.qkl.common.util.JsonUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 分页参数及查询结果封装.
 *
 * 注意: 所有序号从1开始.
 * 注意: JPA的使用请调用getJpaCurrent方法
 */
public class Page<T> implements Serializable{

    public static final Integer PAGE_SIZE=10;

    public static final Integer INDEX_PAGE_SIZE=3;


    // -- 分页参数 --//
    private boolean autoCount = true;// 是否计算总记录条数
    private int current = 1; // 当前页
    private int pageSize = -1; // 每页显示条数
    private Map<String,Object> parameters = new HashMap<>(); //请求参数(可选择性使用)

    // -- 返回结果 --//
    private List<T> result = new ArrayList<T>();
    private long totalCount = -1; // 总记录条数
    private long totalPage = -1; // 总页数
    private Map<String,Object> values = new HashMap<>(); //返回结果值(可选择性使用)


    public Page() {
    }

    public Page(int pageSize) {
        this.pageSize = pageSize;
    }

    public Page(int pageSize, int current) {
        this.pageSize = pageSize;
        this.current = current;
    }

    /**
     * 获得当前页的页号,序号从1开始,默认为1.
     */
    public int getCurrent() {
        return current;
    }

    /**
     * JPA使用时获得当前页的页号,序号从0开始
     */
    public int getJpaCurrent(){
        return current - 1;
    }

    /**
     * 设置当前页的页号,序号从1开始,低于1时自动调整为1.
     */
    public void setCurrent(int current) {
        this.current = current;
        if(current < 1) {
            this.current = 1;
        }
    }

    /**
     * 设置每页的记录数量.
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageSize() {
        return pageSize;
    }

    /**
     * 设置查询对象时是否自动先执行count查询获取总记录数.
     */
    public void setAutoCount(boolean autoCount) {
        this.autoCount = autoCount;
    }

    /**
     * 判断是否需要自动查询总记录条数
     */
    public boolean isAutoCount() {
        return autoCount;
    }

    /**
     * 返回Page对象自身的setPageNo函数,可用于连续设置。
     */
    public Page<T> pageCurrent(int current) {
        setCurrent(current);
        return this;
    }


    /**
     * 返回Page对象自身的setPageSize函数,可用于连续设置。
     */
    public Page<T> pageSize(int thePageSize) {
        setPageSize(thePageSize);
        return this;
    }

    /**
     * 返回Page对象自身的setAutoCount函数,可用于连续设置。
     */
    public Page<T> autoCount(boolean theAutoCount) {
        setAutoCount(theAutoCount);
        return this;
    }


    // -- 访问查询结果函数 --//
    /**
     * 获得页内的记录列表.
     */
    public List<T> getResult() {
        return result;
    }

    /**
     * 获取页内的记录json值
     * @return
     */
    @JsonIgnore
    public String getResultJson(){
        return JsonUtil.beanToJson(result);
    }


    /**
     * 设置页内的记录列表.
     */
    public void setResult(List<T> result) {
        this.result = result;
    }

    /**
     * 获得总记录数, 默认值为-1.
     */
    public long getTotalCount() {
        return totalCount;
    }

    /**
     * 设置总记录数.
     */
    public void setTotalCount(final long totalCount) {
        this.totalCount = totalCount;
    }

    /**
     * 根据pageSize与totalCount计算总页数, 默认值为-1.
     */
    public long getTotalPages() {
        if (totalCount < 0) {
            return -1;
        }
        return (totalCount + pageSize - 1) / pageSize;
    }

    /**
     * 是否还有下一页.
     */
    public boolean isHasNext() {
        return (current + 1 <= getTotalPages());
    }

    /**
     * 是否还有上一页.
     */
    public boolean isHasPre() {
        return (current - 1 >= 1);
    }

    /**
     * 取得下页的页号, 序号从1开始. 当前页为尾页时仍返回尾页序号.
     */
    public int getNextPage() {
        if (isHasNext()) {
            return current + 1;
        } else {
            return current;
        }
    }

    /**
     * 取得上页的页号, 序号从1开始. 当前页为首页时返回首页序号.
     */
    public int getPrePage() {
        if (isHasPre()) {
            return current - 1;
        } else {
            return current;
        }
    }

    public Map<String, Object> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, Object> parameters) {
        this.parameters = parameters;
    }

    public Map<String, Object> getValues() {
        return values;
    }

    public void setValues(Map<String, Object> values) {
        this.values = values;
    }

    public void addParameter(String key, Object parameter){
        this.parameters.put(key, parameter);
    }

    public void addValue(String key,Object value){
        this.values.put(key, value);
    }

    public Page<T> parameter(String key,Object parameter){
        addParameter(key, parameter);
        return this;
    }

    public Page<T> value(String key,Object value){
        addValue(key, value);
        return this;
    }

    public static <T> Page<T> convertPage(org.springframework.data.domain.Page<T> page){
        Page<T> resultPage = new Page<>();

        resultPage.setResult(page.getContent());
        resultPage.setCurrent(page.getNumber());
        resultPage.setPageSize(page.getSize());
        resultPage.setTotalCount(page.getTotalElements());

        return resultPage;
    }


    public static <E,T> Page<T> convertPage(
            org.springframework.data.domain.Page<E> page,
            Convertor<E,T> convertor){
        Page<T> resultPage = new Page<>();
        page.getContent().forEach(e -> {
            resultPage.getResult().add(convertor.convert(e));
        });
        resultPage.setCurrent(page.getNumber());
        resultPage.setPageSize(page.getSize());
        resultPage.setTotalCount(page.getTotalElements());
        return resultPage;
    }

    @Override
    public String toString() {
        return "Page{" +
                "autoCount=" + autoCount +
                ", current=" + current +
                ", pageSize=" + pageSize +
                ", result=" + result +
                ", totalCount=" + totalCount +
                ", totalPage=" + totalPage +
                '}';
    }

@FunctionalInterface
public static interface Convertor<E,T>{
    public T convert(E from);
}


}
