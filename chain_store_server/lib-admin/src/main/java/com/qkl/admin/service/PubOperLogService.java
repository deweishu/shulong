package com.qkl.admin.service;

import com.google.common.collect.Lists;
import com.qkl.common.util.StringUtil;
import com.qkl.admin.assembler.PubOperLogAssembler;
import com.qkl.admin.dto.PubOperLogReq;
import com.qkl.admin.dto.PubOperLogDto;
import com.qkl.admin.entity.PubOperLog;
import com.qkl.admin.jpa.PubOperLogRepository;
import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import java.util.List;

/**
 * Created by Benson on 2018/3/13.
 */
@Service
public class PubOperLogService {

    @Autowired
    private PubOperLogRepository pubOperLogRepository;


    public PubOperLog findOne(String id) {
        return pubOperLogRepository.findOne(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public PubOperLog save(PubOperLogDto operLogDto) {
        Assert.notNull(operLogDto, "");

        PubOperLog pubOperLog;
        if (StringUtil.isNotNil(operLogDto.getId())) {  // edit
            pubOperLog = pubOperLogRepository.findOne(operLogDto.getId());
            pubOperLog = PubOperLogAssembler.convertToEntity(operLogDto, pubOperLog);
        } else {
            pubOperLog = PubOperLogAssembler.convertToEntity(operLogDto, null);
        }

        return pubOperLogRepository.save(pubOperLog);
    }


    public Page<PubOperLogDto> findPage(PubOperLogReq operLogReq) {
        PageRequest pageRequest = new PageRequest(operLogReq.getPageNumber(), operLogReq.getPageSize(), new Sort(Sort.Direction.DESC, "createTime"));
        Specification<PubOperLog> spec = (root, query, builder) -> {
            List<Predicate> predicates = Lists.newArrayList();
            // search by parameters
            if (StringUtils.isNotEmpty(operLogReq.getQueryLike())) {
                Path<String> name = root.get("uri");
                Predicate nickLike = builder.like(name, "%" + operLogReq.getQueryLike() + "%", '/');
                predicates.add(builder.or(nickLike));
            }

            Path<String> operateName = root.get("operateName");
            Predicate operateLike = builder.notEqual(operateName, "admin");
            predicates.add(operateLike);

            query.orderBy(builder.desc(root.get("createTime")));
            return builder.and(predicates.toArray(new Predicate[predicates.size()]));
        };

        Page<PubOperLog> operLogPage = pubOperLogRepository.findAll(spec,pageRequest);
        Page<PubOperLogDto> dtoPage = new PageImpl<>(PubOperLogAssembler.convertToDtoList(operLogPage.getContent()), pageRequest,operLogPage.getTotalElements());
        return dtoPage;
    }
}
