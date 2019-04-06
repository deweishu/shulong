package com.itheima.jjwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.Test;

/**
 * 解析token
 */
public class ParseJwtTest {

    @Test
    public void fun01(){
        String token  ="eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI0NTYiLCJzdWIiOiLlsI_nuqIiLCJpYXQiOjE1NDQwODYzMjIsImV4cCI6MTU0NDA4NjQ0Miwicm9sZSI6ImFkbWluIiwibG9nbyI6ImxvZ28ucG5nIn0.7cIeNcfsjhvrFASpmvre1R1U5TwhAE07tlz_Sl05AO0";
        //1.通过解析token获得的Claims对象(提供了一系列的get方法, 来获得你在签发token的时候设置的数据)
        Claims claims = Jwts.parser().setSigningKey("itcast").parseClaimsJws(token).getBody();
        //2. 获得
        System.out.println("id="+claims.getId());
        System.out.println("主题="+claims.getSubject());
        System.out.println("签发时间="+claims.getIssuedAt());
        System.out.println("角色="+claims.get("role"));
        System.out.println("logo="+claims.get("logo"));
    }


}
