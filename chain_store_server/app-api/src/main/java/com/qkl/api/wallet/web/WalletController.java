package com.qkl.api.wallet.web;

import com.qkl.admin.service.ConfigService;
import com.qkl.admin.service.HotSearchService;
import com.qkl.api.base.web.BaseController;
import com.qkl.api.user.cache.TokenSessionCache;
import com.qkl.common.util.WebUtil;
import com.qkl.common.web.JsonResponse;
import com.qkl.common.web.ResponseHeader;
import com.qkl.common.web.StandardResponseHeader;
import com.qkl.user.service.WalletService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * 用户钱包相关接口
 * @author wjj
 * @create 2018-12-31
 */
@Api(description = "钱包相关接口")
@EnableAutoConfiguration
@RestController
@RequestMapping("/wallet")
public class WalletController extends BaseController {

    @Autowired
    WalletService walletService;

    @Autowired
    TokenSessionCache tokenSessionCache;

    @Autowired
    ConfigService configService;

    @ApiOperation(value="查询钱包明细接口", notes="查询钱包明细接口", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @GetMapping("/findByIndex/{index}")
    public JsonResponse wallet(@PathVariable(name = "index") String index){
        String userId=tokenSessionCache.getUserId();
        return WebUtil.successJsonResponse("查询钱包金额成功", walletService.findWallet(userId, index));
    }


//    @ApiOperation(value="查询钱包明细接口", notes="查询钱包明细接口", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    @GetMapping("/findByIndex/{index}")
//    public JsonResponse wallet(@PathVariable(name = "index") String index){
//        String userId=tokenSessionCache.getUserId();
//        System.out.println("**********\nuserId======"+userId+"\n************");
//        return WebUtil.successJsonResponse("查询钱包金额成功");
//    }
//    private ResponseBody getWallet(String userId, String index){
//        System.out.println("getWallet执行了");
//        return (ResponseBody) JsonResponse.newInstance(StandardResponseHeader.SUCCESS, "成功userId="+userId, "数据index="+index);
//    }




//    @ApiOperation(value="查询钱包明细接口", notes="查询钱包明细接口", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    @GetMapping("/findByIndex")
//    public JsonResponse wallet(){
//        String userId="用户idddddd";
//        System.out.println("**********\nuserId======"+userId+"\n************");
//        return WebUtil.successJsonResponse("查询钱包金额成功", this.getWallet(userId, "23333"));
//    }
//    private ResponseBody getWallet(String userId, String index){
//        System.out.println("getWallet执行了");
//        return (ResponseBody) JsonResponse.newInstance(StandardResponseHeader.SUCCESS, "成功userId="+userId, "index="+index);
//    }
//    @Autowired
//    HotSearchService hotSearchService;
//
//    @ApiOperation(value="获取热搜关键词接口", notes="获取热搜关键词接口", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    @GetMapping("/findByIndex/{indexs}")
//    public JsonResponse getSearchKey(@PathVariable String indexs) {
//        System.out.println(indexs);
//        String userId=tokenSessionCache.getUserId();
//        return WebUtil.successJsonResponse("获取热搜关键词成功");
//    }







//    @GetMapping("/update/{money}")
//    public JsonResponse add(@RequestBody Double money){
//        //根据用户id和钱包种类更新钱包金额
//        return null;
//    }
}
