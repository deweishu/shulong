package com.dwshu;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.dwshu.base.ProducerRabbit;
import com.dwshu.pojo.EsUser;
import com.dwshu.pojo.User;
import com.dwshu.service.UserServer;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.data.elasticsearch.core.ElasticsearchEntityMapper;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringbootServerApplication.class)
public class SpringbootServerTests {

    @Autowired
    public ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Autowired
    private UserServer userServer;



    //----------------------------------------elasticsearch 测试---------------------------------------------//

    /**
     * 测试创建索引库，添加字段映射
     */
    @Test
    public void createIndex() {
        //创建索引库名称
        elasticsearchRestTemplate.createIndex(EsUser.class);
        //添加字段映射
        elasticsearchRestTemplate.putMapping(EsUser.class);
    }


    /**
     * 往索引库里添加数据
     */
    @Test
    public void insertInfo(){

    }



    /**
     * 往索引库里添加数据
     */
    @Test
    public void saveInfo(){
        //id暂时手动插入以后该uuid,密码同样以后加密
        User user = new User(1, "dwshu", "123456", "13113613861", 0);
        User user1 = new User(2, "shulong", "123456", "13113613861", 0);
        User user2 = new User(3, "shudewei", "123456", "13113613861", 0);
        /*Map<String,User> map=new HashMap<>();
        map.put("user",user);
        map.put("user1",user1);
        map.put("user2",user2);
        IndexQuery query = new IndexQuery();
        Set<Map.Entry<String, User>> entries = map.entrySet();
        for (Map.Entry<String, User> entry : entries) {
            User u = entry.getValue();
            query.setObject(new EsUser(u));
            elasticsearchRestTemplate.index(query);
        }*/
        IndexQuery query = new IndexQuery();
        List<User> list=new LinkedList<>();
        list.add(user);
        list.add(user1);
        list.add(user2);

        for (User u : list) {
            query.setObject(new EsUser(u));
            elasticsearchRestTemplate.index(query);
        }


    }



    //----------------------------------------redis 测试---------------------------------------------//
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 获取redis数据库里的数据
     */
    @Test
    public void getRedisData() {
        Map<String, Object> map = new HashMap<>();
        List<User> userList = userServer.findAllByOrderById();

        try {
            if (userList != null && !userList.isEmpty()) {
                for (User user : userList) {
                    Object userInfo = redisTemplate.opsForValue().get(String.valueOf(user.getId()));

                    //会存在map里value值为null的元素，取不到
                    //map=JSONObject.parseObject(JSONObject.toJSONString(userInfo),Map.class);

                    map = JSONObject.parseObject(JSONObject.toJSONString(userInfo, SerializerFeature.WriteMapNullValue), Map.class);

                    log.info("用户信息:id=" + map.get("id") + " ,username=" + map.get("username") +
                            " ,password=" + map.get("password") + " ,telephone=" + map.get("telephone") +
                            " ,state=" + map.get("state"));
                }
            }

        } catch (Exception e) {
            log.info("程序出错了,错误信息：" + e.getMessage());
        }


    }



    //----------------------------------------rabbitMQ 测试---------------------------------------------//

    @Autowired
    ProducerRabbit producerRabbit;

    /**
     *  测试direct直接模式
     */
    @Test
    public void sendDirectMsg(){
        producerRabbit.sendDirectMsg("myQueue",String.valueOf(System.currentTimeMillis()));
    }

    /**
     * 测试topic主题模式
     */
    @Test
    public void senTopicMsg(){
        producerRabbit.sendExchangeMsg("topic-exchange","myQueue", "这是我的第一个主题模式消息！");
    }

    /**
     * 测试fanout分裂模式
     */
    @Test
    public void senFanoutMsg(){
        producerRabbit.sendExchangeMsg("fanout-exchange","myQueue.test", "这是我的第一个分裂模式消息！");
    }

    /**
     * 测试header头模式
     */
    @Test
    public void senHeaderMsg(){
        Map<String,Object> map=new HashMap<>();
        map.put("first","第一");
        producerRabbit.sendHeaderMsg("headers-exchange","这是我的第一个头模式消息！",map);
    }




    //----------------------------------------rabbitMQ 测试---------------------------------------------//




}