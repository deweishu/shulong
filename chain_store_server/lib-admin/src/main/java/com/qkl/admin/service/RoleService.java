package com.qkl.admin.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.qkl.admin.assembler.RoleAssembler;
import com.qkl.admin.dto.RoleDto;
import com.qkl.admin.dto.RoleReq;
import com.qkl.admin.entity.Role;
import com.qkl.admin.entity.RolePermission;
import com.qkl.admin.jpa.RolePermissionRepository;
import com.qkl.admin.jpa.RoleRepository;
import com.qkl.common.bean.BaseStatus;
import com.qkl.common.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.*;

/**
 * Created by dengjihai on 2018/3/7.
 */
@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RolePermissionRepository rolePermissionRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public List<Role> getAvailable() {
        return roleRepository.findAvailable();
    }

    public Role get(String id) {
        return roleRepository.findById(id);
    }

    public Set<Role> get(List<String> ids){
        List<Role> list =  roleRepository.findByIds(ids);
        Set<Role> set = new HashSet<>();
        set.addAll(list);
        return set;
    }

    @Transactional
    public Role save(RoleDto roleDto, String[] permissionCodes) {
        Role role;
        if (StringUtils.isEmpty(roleDto.getId())) {
            role = RoleAssembler.convertToEntity(roleDto, null);
        } else {
            rolePermissionRepository.deleteByRoleId(roleDto.getId());
            role = roleRepository.findById(roleDto.getId());
            role = RoleAssembler.convertToEntity(roleDto, role);
        }

        if (permissionCodes != null && permissionCodes.length > 0) {
            role.setPermissions(Sets.newHashSet());
            for (String p : permissionCodes) {
                RolePermission rp = new RolePermission();
                rp.setRole(role);
                rp.setPermission(p);
                role.getPermissions().add(rp);
            }
        }
        return roleRepository.save(role);
    }

    public void delete(String id) {
        roleRepository.delete(id);
    }


    public Page<RoleDto> findPage(RoleReq roleReq) {
        PageRequest pageRequest = new PageRequest(roleReq.getPageNumber(), roleReq.getPageSize(), new Sort(Sort.Direction.DESC, "createTime"));
        Specification<Role> spec = (root, query, builder) -> {
            List<Predicate> predicates = Lists.newArrayList();
            if (StringUtil.isNotNil(roleReq.getQueryLike())) {
                Path<String> name = root.get("name");
                Predicate nameLike = builder.like(name, "%" + roleReq.getQueryLike() + "%", '/');
                predicates.add(builder.or(nameLike));
            }
            query.orderBy(builder.desc(root.get("createTime")));
            return builder.and(predicates.toArray(new Predicate[predicates.size()]));
        };

        Page<Role> adminPage= roleRepository.findAll(spec,pageRequest);
        Page<RoleDto> adminDtoPage = new PageImpl<>(RoleAssembler.convertToDtoList(adminPage.getContent()), pageRequest,adminPage.getTotalElements());
        return adminDtoPage;
    }

    @Transactional
    public String updateStatus(String id,Boolean enable){
        Role role = roleRepository.findOne(id);
        if(Objects.isNull(role)){
            return null;
        }
        role.setStatus(enable? BaseStatus.COMMON: BaseStatus.DISABLED);
        return roleRepository.save(role).getId();
    }


    @Transactional
    public void batchSave(List<Role> roles) {
        Assert.notNull(roles, "数据不能为空");
        int size = roles.size();
        for (int i = 0; i<size; i++) {
            Role role = roles.get(i);
            if (StringUtil.isBlank(role.getId())) {
                entityManager.persist(role);
            } else {
                entityManager.persist(entityManager.merge(role));
            }

            // 每100条数据执行一次，或者最后不足100条时执行
            if (i % 100 == 0 || i==(size-1)) {
                entityManager.flush();
                entityManager.clear();
            }
        }
    }
}
