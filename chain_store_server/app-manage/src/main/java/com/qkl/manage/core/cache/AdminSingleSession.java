package com.qkl.manage.core.cache;

import com.qkl.admin.entity.Admin;
import com.qkl.common.web.session.SingleSession;
import org.springframework.stereotype.Component;

@Component
public class AdminSingleSession extends SingleSession<Admin> {

    @Override
    public Class<Admin> userClass() {
        return Admin.class;
    }

}
