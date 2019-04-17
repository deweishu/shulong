package com.qkl.common.web.session;

import com.qkl.common.cache.StringCache;
import org.springframework.stereotype.Component;

/**
 * Created by dengjihai on 2018/3/7.
 */
@Component
public class UserSessionCache extends StringCache {

    public String setUserSession(String userId, String sessionId) {
        if (userId == null || sessionId == null) {
            return null;
        }

        String tmp = get(userId);
        if (sessionId.equals(tmp)) {
            return null;
        }
        put(userId, sessionId);
        return tmp;
    }

    @Override
    public String getPrefix() {
        return "user:session";
    }

    @Override
    protected Integer getTimeout() {
        return SingleSession.TIMEOUT;
    }

    @Override
    public void invalid(String key) {
        remove(key);
    }
}
