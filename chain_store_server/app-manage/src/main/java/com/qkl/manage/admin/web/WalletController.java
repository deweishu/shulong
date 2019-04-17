package com.qkl.manage.admin.web;
import com.qkl.admin.annotaion.SystemLogAnnotation;
import com.qkl.admin.dto.WalletDto;
import com.qkl.admin.service.WalletService;
import com.qkl.common.permission.RequestPermission;
import com.qkl.common.util.WebUtil;
import com.qkl.common.web.JsonResponse;
import com.qkl.manage.admin.bean.AdminPermission;
import com.qkl.manage.common.base.BaseController;
import com.qkl.manage.menu.bean.BoardMenu;
import com.qkl.menu.MenuMapping;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Api(description = "钱包相关接口")
@Controller
@RequestMapping("/wallet")
public class WalletController extends BaseController {


    @Autowired
    private WalletService walletService;

    @ApiOperation(value = "钱包管理")
    @GetMapping("/index")
    @RequestPermission(value = AdminPermission.WALLET_LIST)
    @MenuMapping(value ="钱包管理",menu = BoardMenu.WALLET, weight = 9)
    public  String index(){
        return "list1";
    }




    /**
     * 进入钱包新增页面
     *
     */
    @ApiOperation(value="钱包添加", notes="钱包添加", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @GetMapping("/add")
    public String toSave(Model model) {
        return "wallet/edit";
    }


    /**
     * 进入钱包修改页面
     *
     */
    @ApiOperation(value = "钱包修改")
    @GetMapping("/edit/{id}")
    public String toEdit(Model model,@PathVariable String id) {
        WalletDto walletDto=walletService.findOne(id);
        model.addAttribute("wallet",walletDto);
        return "wallet/edit";
    }

    /**
     * 添加数据方法
     */
    @ApiOperation(value = "添加数据")
    @SystemLogAnnotation(desc = "更新/添加了合作商信息")
    @ResponseBody
    @PostMapping("/save")
    public JsonResponse save(WalletDto walletDto) {
        walletService.save(walletDto);
        return WebUtil.successJsonResponse("保存成功");
    }
 }
