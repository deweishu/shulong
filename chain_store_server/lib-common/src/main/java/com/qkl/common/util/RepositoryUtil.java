package com.qkl.common.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

/**
 * Created by dengjihai on 2018/3/7.
 */
public class RepositoryUtil {

    public static final int PER_PAGE_RESULT = 20;

    public static Pageable getPageable(int page) {
        return new PageRequest(page, PER_PAGE_RESULT);
    }

}
