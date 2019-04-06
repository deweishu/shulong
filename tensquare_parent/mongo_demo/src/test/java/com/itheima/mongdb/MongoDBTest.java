package com.itheima.mongdb;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.junit.Test;

import java.util.HashMap;

public class MongoDBTest {

    @Test
    //查询所有
    public void fun01(){
        //1.创建客户端对象(连接mongdb数据库)
        MongoClient client = new MongoClient("192.168.147.139");

        //2.获得数据库(打开数据库)
        MongoDatabase spitdb = client.getDatabase("spitdb");

        //3.获得集合
        MongoCollection<Document> spit = spitdb.getCollection("spit");

        //4.查询所有的文档
        FindIterable<Document> documents = spit.find();

        //5.遍历 一个document对象就是一个文档, 就是一条数据
        for (Document document : documents) {
            System.out.println("获得id=" + document.get("_id"));
            System.out.println("获得content=" + document.get("content"));
            System.out.println("获得userid=" + document.get("userid"));
            System.out.println("获得visits=" + document.get("visits"));
            System.out.println("获得thumbup=" + document.get("thumbup"));

            System.out.println("-----------------");
        }

        //6.释放资源
        client.close();

    }

    @Test
    //条件查询, 查询_id=1的
    public void fun02(){
        //1.创建客户端对象(连接mongdb数据库)
        MongoClient client = new MongoClient("192.168.147.139");

        //2.获得数据库(打开数据库)
        MongoDatabase spitdb = client.getDatabase("spitdb");

        //3.获得集合
        MongoCollection<Document> spit = spitdb.getCollection("spit");

        //4.查询所有的文档
        //封装条件
        BasicDBObject bson = new BasicDBObject("_id","1");
        FindIterable<Document> documents = spit.find(bson);

        //5.遍历 一个document对象就是一个文档, 就是一条数据
        for (Document document : documents) {
            System.out.println("获得id=" + document.get("_id"));
            System.out.println("获得content=" + document.get("content"));
            System.out.println("获得userid=" + document.get("userid"));
            System.out.println("获得visits=" + document.get("visits"));
            System.out.println("获得thumbup=" + document.get("thumbup"));

            System.out.println("-----------------");
        }

        //6.释放资源
        client.close();

    }

    @Test
    //条件查询, 查询visits>1000的
    public void fun03(){
        //1.创建客户端对象(连接mongdb数据库)
        MongoClient client = new MongoClient("192.168.147.139");

        //2.获得数据库(打开数据库)
        MongoDatabase spitdb = client.getDatabase("spitdb");

        //3.获得集合
        MongoCollection<Document> spit = spitdb.getCollection("spit");

        //4.查询所有的文档
        //封装条件  db.spit.find({visits:{$gt:NumberInt(1000)}});
        BasicDBObject bson = new BasicDBObject("visits",new BasicDBObject("$gt",1000));
        FindIterable<Document> documents = spit.find(bson);

        //5.遍历 一个document对象就是一个文档, 就是一条数据
        for (Document document : documents) {
            System.out.println("获得id=" + document.get("_id"));
            System.out.println("获得content=" + document.get("content"));
            System.out.println("获得userid=" + document.get("userid"));
            System.out.println("获得visits=" + document.get("visits"));
            System.out.println("获得thumbup=" + document.get("thumbup"));

            System.out.println("-----------------");
        }

        //6.释放资源
        client.close();

    }

    @Test
    //条件查询, 查询visits>1000的并且thumbup<13的
    public void fun04(){
        //1.创建客户端对象(连接mongdb数据库)
        MongoClient client = new MongoClient("192.168.147.139");

        //2.获得数据库(打开数据库)
        MongoDatabase spitdb = client.getDatabase("spitdb");

        //3.获得集合
        MongoCollection<Document> spit = spitdb.getCollection("spit");

        //4.查询所有的文档
        //封装条件    db.spit.find({$and:[{visits:{$gt:NumberInt(1000)}},{thumbup:{$lt:NumberInt(13)}}]});
        BasicDBObject bson = new BasicDBObject("$and",new BasicDBObject[]{
                new BasicDBObject("visits",new BasicDBObject("$gt",1000)),
                new BasicDBObject("thumbup",new BasicDBObject("$lt",13)),
        });

        FindIterable<Document> documents = spit.find(bson);

        //5.遍历 一个document对象就是一个文档, 就是一条数据
        for (Document document : documents) {
            System.out.println("获得id=" + document.get("_id"));
            System.out.println("获得content=" + document.get("content"));
            System.out.println("获得userid=" + document.get("userid"));
            System.out.println("获得visits=" + document.get("visits"));
            System.out.println("获得thumbup=" + document.get("thumbup"));

            System.out.println("-----------------");
        }

        //6.释放资源
        client.close();

    }

    @Test
    //插入
    public void fun05(){
        //1.创建客户端对象(连接mongdb数据库)
        MongoClient client = new MongoClient("192.168.147.139");

        //2.获得数据库(打开数据库)
        MongoDatabase spitdb = client.getDatabase("spitdb");

        //3.获得集合
        MongoCollection<Document> spit = spitdb.getCollection("spit");

        //4.插入文档
        HashMap hashMap = new HashMap();
        hashMap.put("_id","10");
        hashMap.put("content","好热啊");
        hashMap.put("userid","3");
        hashMap.put("nickname","ls");
        hashMap.put("visits",200);
        hashMap.put("thumbup",10);
        hashMap.put("comment",10);

        Document document = new Document(hashMap);
        spit.insertOne(document);





        //6.释放资源
        client.close();

    }

    @Test
    //删除 id为1的
    public void fun06(){
        //1.创建客户端对象(连接mongdb数据库)
        MongoClient client = new MongoClient("192.168.147.139");

        //2.获得数据库(打开数据库)
        MongoDatabase spitdb = client.getDatabase("spitdb");

        //3.获得集合
        MongoCollection<Document> spit = spitdb.getCollection("spit");

        //4.删除文档
        Bson bson =  new BasicDBObject("_id","1");
        spit.deleteOne(bson);

        //6.释放资源
        client.close();

    }

}
