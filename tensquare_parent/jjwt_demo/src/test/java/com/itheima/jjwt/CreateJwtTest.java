package com.itheima.jjwt;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.Test;

import java.util.Date;


/**
 *  签发token
 *  1. 头部（Header）  {"typ":"JWT","alg":"HS256"}
 *  2. 载荷
 *      默认字段, 还可以自定义
 *       iss (issuer)：签发人
         exp (expiration time)：过期时间
         sub (subject)：主题
         aud (audience)：受众
         nbf (Not Before)：生效时间
         iat (Issued At)：签发时间
         jti (JWT ID)：编号
   3. 签证（signature）
        HMACSHA256(base64UrlEncode(头部) + "." +base64UrlEncode(载荷),秘钥)
 *
 */
public class CreateJwtTest {

    @Test
    //基本签发(没有设置过期时间, 没有自定义载荷)
    public void fun01(){

        //1. 创建JwtBuilder
        //eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxMjMiLCJzdWIiOiLlsI_pu5EiLCJpYXQiOjE1NDQwODQ0ODN9.O4mp3lp7NxqiSMM_VWh-Yw6vEZD1XP-cH1mKLnwX228
        JwtBuilder jwtBuilder = Jwts.builder().
                setId("123"). //设置id
                setSubject("小黑"). //设置主题
                setIssuedAt(new Date()). //设置签发时间
                signWith(SignatureAlgorithm.HS256, "itcast");//设置加密算法和秘钥

        //2. 签发token
        String token = jwtBuilder.compact();
        System.out.println("token="+token);

    }

    @Test
    //设置过期时间(1分钟过期)
    //eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI0NTYiLCJzdWIiOiLlsI_nuqIiLCJpYXQiOjE1NDQwODYwNTEsImV4cCI6MTU0NDA4NjExMH0.IMSV8bOX5tPPqQtEAGYTqVUY6r22_RNmpylbhm_iI4k
    public void fun02(){
        long now = System.currentTimeMillis();
        long exp = now+1000*60;

        //1. 创建JwtBuilder
        JwtBuilder jwtBuilder = Jwts.builder().
                setId("456"). //设置id
                setSubject("小红"). //设置主题
                setIssuedAt(new Date()). //设置签发时间
                setExpiration(new Date(exp)).//过期时间
                signWith(SignatureAlgorithm.HS256, "itcast");//设置加密算法和秘钥
        //2. 签发token
        String token = jwtBuilder.compact();
        System.out.println("token="+token);
    }


    @Test
    //自定义载荷(自定义有效的字段)
    //eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI0NTYiLCJzdWIiOiLlsI_nuqIiLCJpYXQiOjE1NDQwODYzMjIsImV4cCI6MTU0NDA4NjQ0Miwicm9sZSI6ImFkbWluIiwibG9nbyI6ImxvZ28ucG5nIn0.7cIeNcfsjhvrFASpmvre1R1U5TwhAE07tlz_Sl05AO0
    public void fun03(){
        long now = System.currentTimeMillis();
        long exp = now+1000*60*2;

        //1. 创建JwtBuilder
        JwtBuilder jwtBuilder = Jwts.builder().
                setId("456"). //设置id
                setSubject("小红"). //设置主题
                setIssuedAt(new Date()). //设置签发时间
                setExpiration(new Date(exp)).//过期时间
                claim("role","admin").//自定义载荷(角色)
                claim("logo","logo.png").//自定义载荷(图像)
                signWith(SignatureAlgorithm.HS256, "itcast");//设置加密算法和秘钥
        //2. 签发token
        String token = jwtBuilder.compact();
        System.out.println("token="+token);
    }

}
