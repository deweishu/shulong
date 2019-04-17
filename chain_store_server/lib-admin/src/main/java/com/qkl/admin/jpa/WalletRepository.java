package com.qkl.admin.jpa;
import com.qkl.admin.entity.Customer;
import com.qkl.admin.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;



public interface WalletRepository extends JpaRepository<Wallet, String>, JpaSpecificationExecutor<Wallet> {

    Wallet findByPhone(String phone);

}
