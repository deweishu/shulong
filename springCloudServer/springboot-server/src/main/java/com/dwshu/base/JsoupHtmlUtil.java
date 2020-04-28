package com.dwshu.base;

import com.dwshu.dto.GoodsDto;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author dwshu
 * @create 2020/4/20
 * @describe 用于接收请求，解析请求对应的页面
 */

public class JsoupHtmlUtil {

    public static List<GoodsDto> parsingReques(String keyword) {
        List<GoodsDto> list = new ArrayList<>();
        //京东请求
        String jd = "https://search.jd.com/Search?keyword=" + keyword;
        //天猫请求
        String tmall = "https://list.tmall.com/search_product.htm?q=" + keyword;
        //淘宝请求
        String taobao = "https://s.taobao.com/search?q=" + keyword;


        //京东
        try {
            //解决通信证书过期的问题
            SslUtils.trustEveryone();

            //解析京东搜索请求
            Document doucment = Jsoup.parse(new URL(jd),2000);
            //获取内容数据
            Elements elements = doucment.getElementById("J_goodsList").getElementsByTag("li");
            for (Element element : elements) {
                GoodsDto goodsDto = new GoodsDto();
                goodsDto.setImg(element.getElementsByTag("img").eq(0).attr("source-data-lazy-img"));
                goodsDto.setPrice(element.getElementsByClass("p-price").eq(0).text());
                goodsDto.setName(element.getElementsByClass("p-name").eq(0).text());
                goodsDto.setTitle(element.getElementsByClass("p-name").eq(0).select("i").text());
                goodsDto.setComment(element.getElementsByClass("p-commit").select("a").text());
                goodsDto.setShopName(element.getElementsByClass("p-shop").select("span").select("a").text());
                list.add(goodsDto);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        //天猫
        try {
            //解决通信证书过期的问题
            SslUtils.trustEveryone();
            //解析天猫搜索请求
            Document document = Jsoup.parse(new URL(tmall), 2000);
            Elements elements = document.getElementById("J_ItemList").getElementsByClass("product");
            for (Element element : elements) {
                GoodsDto goodsDto = new GoodsDto();
                goodsDto.setImg(element.getElementsByTag("img").attr("src"));
                goodsDto.setPrice(element.getElementsByClass("productPrice").text());
                goodsDto.setName("");
                goodsDto.setTitle(element.getElementsByClass("productTitle").eq(0).text());
                goodsDto.setComment(element.getElementsByClass("productStatus").select("span").eq(1).select("a").text());
                goodsDto.setShopName(element.getElementsByClass("productShop").eq(0).text());
                list.add(goodsDto);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }



        //淘宝  ------->以后再写



        return list;
    }

}
