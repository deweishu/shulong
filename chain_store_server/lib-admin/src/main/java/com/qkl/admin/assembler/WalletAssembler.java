package com.qkl.admin.assembler;
import com.qkl.admin.dto.WalletDto;
import com.qkl.admin.entity.Wallet;
import java.util.ArrayList;
import java.util.List;

public class WalletAssembler {
    public static Wallet convertToEntity(WalletDto walletDto, Wallet wallet){

        wallet.setId(walletDto.getId());
        wallet.setName(walletDto.getName());
        wallet.setPhone(walletDto.getPhone());
        wallet.setFee(walletDto.getFee());
        wallet.setRule(walletDto.getRule());
        wallet.setImage(walletDto.getImage());
        wallet.setAddress(walletDto.getAddress());
        wallet.setCreateTime(walletDto.getCreateTime());
        wallet.setUpdateTime(walletDto.getUpdateTime());
        return wallet;
    }


    public static WalletDto convertToDto(Wallet wallet){
        if(wallet==null){
            return null;
        }
        WalletDto walletDto = new WalletDto();
        walletDto.setId(wallet.getId());
        walletDto.setName(wallet.getName());
        walletDto.setPhone(wallet.getPhone());
        walletDto.setImage(wallet.getImage());
        walletDto.setAddress(wallet.getAddress());
        walletDto.setCreateTime(wallet.getCreateTime());
        walletDto.setUpdateTime(wallet.getUpdateTime());
        return walletDto;
    }


    public static List<WalletDto> convertToDtoList(List<Wallet> walletList){
        List<WalletDto> walletDtoList= new ArrayList<>();
        walletList.forEach(wallet -> walletDtoList.add(convertToDto(wallet)));
        return walletDtoList;
    }
 }
