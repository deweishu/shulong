package com.qkl.admin.service;


import com.qkl.admin.dto.AdminDto;
import com.qkl.admin.dto.CustomerDto;
import com.qkl.admin.entity.Admin;
import com.qkl.admin.jpa.AdminRepository;
import com.qkl.admin.jpa.CustomerRepository;
import com.qkl.admin.dto.CustomerReq;
import com.qkl.admin.assembler.CustomerAssembler;
import com.qkl.admin.entity.Customer;
import com.qkl.common.constant.CodeConstant;
import com.qkl.common.util.EncryptionUtil;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import java.util.*;
import com.google.common.collect.Lists;
import com.qkl.common.util.StringUtil;
import javax.persistence.criteria.*;



/**
*
generate by dengjihai
*/
@Service
public class CustomerService {

	@Autowired
	private CustomerRepository customerRepository;


	@Autowired
	AdminService adminService;

	@Autowired
	AdminRepository adminRepository;


	/**
	 * 添加合作商---请求方法
	 * @param customerDto
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public String save(CustomerDto customerDto){
		Customer  customer;

		/*if(StringUtil.isNotBlank(customerDto.getId())){
			customer=customerRepository.findOne(customerDto.getId());
			Assert.notNull(	customer ,message:"不存在该数据");
			customer = CustomerAssembler.convertToEntity(customerDto,customer);
		}else{
			Customer customerRepositoryByPhone = customerRepository.findByPhone(customerDto.getPhone());
			Assert.isTrue(expression : customerRepositoryByPhone==null. message:"该手机号已经存在");
		   Admin admin=	adminRepository.findByMobile(customerDto.getPhone());
			Assert.isTrue(expressionn:admin == null, message:"该手机号已经存在");
			AdminDto adminDto = new AdminDto();
			String [] strings = new String[1];



		}*/




		if (StringUtil.isNotNil(customerDto.getId())) {
			customer=customerRepository.findOne(customerDto.getId());
			Assert.notNull(customer, "不存该数据");
			customer = CustomerAssembler.convertToEntity(customerDto, customer);
		}else{
			Customer customerRepositoryByPhone=customerRepository.findByPhone(customerDto.getPhone());
			Assert.isTrue(customerRepositoryByPhone==null,"该手机号已经存在");
			Admin admin =adminRepository.findByMobile(customerDto.getPhone());
			Assert.isTrue(admin==null,"该手机号已经存在");
			AdminDto adminDto  = new AdminDto();
			String []strings = new String[1];
			strings[0]= CodeConstant.PARTNER_ROLE_ID;
			adminDto.setMobile(customerDto.getPhone());
			adminDto.setRealname(customerDto.getName());
			adminDto.setUsername(customerDto.getPhone());
			adminDto.setStatus(10);
			adminDto.setPassword(CodeConstant.PARTNER_DEFAULT_PASS);
			adminService.save(adminDto,strings);

			customer =CustomerAssembler.convertToEntity(customerDto,null);
		}
		return customerRepository.save(customer).getId();
	}


	public CustomerDto findOne(String id) {
		Assert.isTrue(StringUtil.isNotBlank(id), "ID不能为空");
		return CustomerAssembler.convertToDto(customerRepository.findOne(id));
	}

	public CustomerDto findByPhone(String phone) {
		Assert.isTrue(StringUtil.isNotBlank(phone), "手机号不能为空");
		return CustomerAssembler.convertToDto(customerRepository.findByPhone(phone));
	}



	/**
	 * 重置合作商密码
	 * @param id
	 * @return
	 */
	public String restPwd(String id){
		Customer customer=customerRepository.findOne(id);
		Assert.notNull(customer,"不存在该合作商");
		Admin admin=adminRepository.findByMobile(customer.getPhone());
		Assert.notNull(admin,"系统账号不存在");
		admin.setPassword(EncryptionUtil.encryptPassword(CodeConstant.PARTNER_DEFAULT_PASS));
		String adminId=adminRepository.save(admin).getId();
		return adminId;
	}


	public Page<CustomerDto> findPage(CustomerReq customerReq) {
		PageRequest pageRequest = new PageRequest(customerReq.getPageNumber(), customerReq.getPageSize(), new Sort(Sort.Direction.DESC, "createTime"));
		Specification<Customer> spec = (root, query, builder) -> {
			List<Predicate> predicates = Lists.newArrayList();
			if(StringUtil.isNotNil(customerReq.getPhone())){
				Path<String> phonePath = root.get("phone");
				predicates.add(builder.equal(phonePath,  customerReq.getPhone()));
			}
			if(StringUtil.isNotNil(customerReq.getName())){
				Path<String> phonePath = root.get("name");
				predicates.add(builder.equal(phonePath,  customerReq.getName()));
			}
			query.orderBy(builder.desc(root.get("createTime")));
			return builder.and(predicates.toArray(new Predicate[predicates.size()]));
		};
		Page<Customer> customerPage = customerRepository.findAll(spec,pageRequest);
		Page<CustomerDto> customerDtoPage = new PageImpl<>(CustomerAssembler.convertToDtoList(customerPage.getContent()), pageRequest,customerPage.getTotalElements());
		return customerDtoPage;
	}
}
