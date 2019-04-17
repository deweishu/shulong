package com.qkl.admin.assembler;


import com.qkl.admin.dto.CustomerDto;
import com.qkl.admin.entity.Customer;
import java.util.ArrayList;
import java.util.List;



/**
*
generate by dengjihai
*/
public class CustomerAssembler {

	public static Customer convertToEntity(CustomerDto customerDto, Customer customer){
		if(customer==null){
			customer = new Customer();
			customer.setStatus(true);//账号默认是正常的
		}else{
			customer.setStatus(customerDto.getStatus());
		}
		customer.setId(customerDto.getId());
		customer.setName(customerDto.getName());
		customer.setPhone(customerDto.getPhone());
		return customer;
	}


	public static CustomerDto convertToDto(Customer customer){
		if(customer==null){
			return null;
		}
		CustomerDto customerDto = new CustomerDto();
		customerDto.setId(customer.getId());
		customerDto.setName(customer.getName());
		customerDto.setPhone(customer.getPhone());
		customerDto.setStatus(customer.getStatus());
		customerDto.setCreateTime(customer.getCreateTime());
		customerDto.setUpdateTime(customer.getUpdateTime());
		return customerDto;
	}


	public static List<CustomerDto> convertToDtoList(List<Customer> customerList){
		List<CustomerDto> customerDtoList= new ArrayList<>();
		customerList.forEach(customer -> customerDtoList.add(convertToDto(customer)));
		return customerDtoList;
	}
}
