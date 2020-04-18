package com.dwshu.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Entity;
import java.io.Serializable;

/**
 * @author dwshu
 * @create 2019/11/14
 *
 *  EsUser用于创建ES索引库
 */

@Document(indexName = "esdemo",type = "user", shards = 5, replicas = 0)
public class EsUser implements Serializable {


    @Id
    public  int id;

    @Field(type=FieldType.Text, analyzer = "ik_max_word")
    public  String username;

    @Field(type= FieldType.Keyword)
    public  String password;

    @Field(type=FieldType.Text, analyzer = "ik_max_word")
    public  String telephone;

    @Field(type= FieldType.Integer)
    public Integer state;


    public EsUser(User user) {
        id=user.getId();
        username=user.getUsername();
        password=user.getPassword();
        telephone=user.getTelephone();
        state=user.getState();
    }
}
