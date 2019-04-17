package com.qkl.common.collection;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;

/**
 * 自定义哈希数组
 * Created by dengjihai on 2018/3/28.
 */
public class ArrayHashMap<K, V> {

    private Map<K, V> map;

    private List<V> list;

    public ArrayHashMap() {
        map = Maps.newHashMap();
        list = Lists.newArrayList();
    }

    public static <K, V> ArrayHashMap<K, V> newInstance() {
        return new ArrayHashMap<>();
    }

    public void add(K key, V value) {
        map.put(key, value);
        list.add(value);
    }

    public List<V> getList() {
        return list;
    }

    public Map<K,V> getMap(){
        return map;
    }

    public V get(K key) {
        return map.get(key);
    }
}
