package com.pinyougou.user.service;

import com.pinyougou.pojo.TbSeller;
import com.pinyougou.sellergoods.service.SellerService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class UserDetailServiceImpl implements UserDetailsService{

    private SellerService sellerService;

    public void setSellerService(SellerService sellerService) {
        this.sellerService = sellerService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("经过了认证服务类。。。。");
        List<GrantedAuthority> authorizes = new ArrayList<>();
        //赋予权限相关数据
        authorizes.add(new SimpleGrantedAuthority("ROLE_SELLER"));
        //基于商品id查询商家信息
        TbSeller seller = sellerService.findOne(username);

        if(seller!=null){
            //只有审核通过的商家才能登陆成功
            if("1".equals(seller.getStatus())){
                return new User(username,seller.getPassword(),authorizes);
            }else {
                return null;
            }
        }else {
            return null;
        }

    }
}
