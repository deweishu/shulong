
package com.qkl.admin.service;
import com.qkl.admin.assembler.CustomerAssembler;
import com.qkl.admin.assembler.WalletAssembler;
import com.qkl.admin.dto.AdminDto;
import com.qkl.admin.dto.CustomerDto;
import com.qkl.admin.dto.WalletDto;
import com.qkl.admin.entity.Admin;
import com.qkl.admin.entity.Wallet;
import com.qkl.admin.jpa.AdminRepository;
import com.qkl.admin.jpa.RoleRepository;
import com.qkl.admin.jpa.WalletRepository;
import com.qkl.common.constant.CodeConstant;
import com.qkl.common.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * 此类是管理员操作钱包
 */
@Service("walletsService")
public class WalletService {

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private RoleRepository RoleRepository;

    @Autowired
    private AdminService adminService;

    @Autowired
    private AdminRepository adminRepository;

    /**
     * 后台管理-钱包保存
     * @param walletDto
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public String save(WalletDto walletDto) {

        Wallet wallet;

        if (StringUtil.isNotNil(walletDto.getId())) {
            wallet = walletRepository.findOne(walletDto.getId());
            Assert.notNull(wallet, "不存该数据");
           wallet = WalletAssembler.convertToEntity(walletDto, wallet);
        } else {
            Wallet walletRepositoryByPhone = walletRepository.findByPhone(walletDto.getPhone());
            Assert.isTrue(walletRepositoryByPhone == null, "该手机号已经存在");
            Admin admin = adminRepository.findByMobile(walletDto.getPhone());
            Assert.isTrue(admin == null, "该手机号已经存在");
            AdminDto adminDto = new AdminDto();
            String[] strings = new String[1];
            strings[0] = CodeConstant.PARTNER_ROLE_ID;
            adminDto.setMobile(walletDto.getPhone());
            adminDto.setRealname(walletDto.getName());
            adminDto.setUsername(walletDto.getPhone());
            adminDto.setPassword(CodeConstant.PARTNER_DEFAULT_PASS);
            adminService.save(adminDto, strings);
           wallet = WalletAssembler.convertToEntity(walletDto, null);
        }
        return walletRepository.save(wallet).getId();
    }

    /**
     * 后台管理-钱包查找(id)
     * @param id
     * @return
     */
    public WalletDto findOne(String id) {
        Assert.isTrue(StringUtil.isNotBlank(id), "ID不能为空");
        return WalletAssembler.convertToDto(walletRepository.findOne(id));
    }

    /**
     * 后台管理-钱包查找(手机号)
     * @param phone
     * @return
     */
    public WalletDto findByPhone(String phone) {
        Assert.isTrue(StringUtil.isNotBlank(phone), "手机号不能为空");
        return WalletAssembler.convertToDto(walletRepository.findByPhone(phone));
    }


}

