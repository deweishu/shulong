package com.qkl.common.web.session;

import com.qkl.common.bean.UUIDEntity;
import com.qkl.common.cache.HashCache;
import com.qkl.common.helper.CookieHelper;
import com.qkl.common.util.StringUtil;
import com.qkl.common.util.WebUtil;
import com.qkl.common.web.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 通过redis cache实现的全局session，同时实现了spring mvc拦截器，用于拦截用户请求，以支持全局session
 */
public abstract class SingleSession<U extends UUIDEntity> extends HashCache implements HandlerInterceptor {

    public static final int TIMEOUT = 12 * RedisTime.HOURE; // 默认session存储时间为12小时
    public static String COOKIE_SESSION_ID = "SessionID";
    /**
     * 当前线程所对应的sessionId
     */
    private final ThreadLocal<String> threadSessionId = new ThreadLocal<>();
    /**
     * 当前线程所对应的用户信息
     */
    private final ThreadLocal<U> threadUser = new ThreadLocal<>();
    /**
     * 当前线程所对应的用户ID
     */
    private final ThreadLocal<String> threadId = new ThreadLocal<>();


    @Autowired
    UserSessionCache userSessionCache;
    @Autowired
    private SingleSession<?> singleSession;

    private String USER = "user";

    private String AVAILABLE = "available";

    public SingleSession() {
    }

    @Override
    protected Integer getTimeout() {
        return TIMEOUT;
    }

    @Override
    public String getPrefix() {
        return "session";
    }

    /**
     * @return 当前线程sessionId
     */
    public String getSessionId() {
        return threadSessionId.get();
    }

    /**
     * @return 当前线程用户
     */
    public U getUser() {
        return threadUser.get();
    }

    /**
     * 设置当前线程对应session的用户
     *
     * @param user
     */
    public void setUser(U user) {
        put(getSessionId(), USER, user);
        String tmp = userSessionCache.setUserSession(user.getId(), getSessionId());
        if(StringUtil.isNotNil(tmp)){
            setToUnavaliable(tmp);
        }
        threadUser.set(user);
    }

    public String getUserId() {
        return threadId.get();
    }

    public void setUserId(String id) {
        threadId.set(id);
    }

    public <T> void put(String key, T object) {
        put(getSessionId(), key, object);
    }

    /**
     * @param key
     * @return session中的字符型信息
     */
    public String get(String key) {
        return get(getSessionId(), key);
    }

    /**
     * @param key
     * @param clazz
     * @param <T>
     * @return session中的对象型信息
     */
    public <T> T get(String key, Class<T> clazz) {
        return get(getSessionId(), key, clazz);
    }

    /**
     * @param sessionId
     * @return session是否有效
     */
    private boolean isAvailable(String sessionId) {
        String tmp = get(sessionId, AVAILABLE);
        if (tmp == null) {
            return true;
        }
        return Boolean.valueOf(tmp);
    }

    /**
     * @return 当前session是否有效
     */
    private boolean isAvailable() {
        return isAvailable(getSessionId());
    }

    /**
     * 将sessionId对应session设为不可用
     *
     * @param sessionId
     */
    public void setToUnavaliable(String sessionId) {
        put(sessionId, AVAILABLE, Boolean.FALSE.toString());
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String sessionId = CookieHelper.getCookieValue(request, COOKIE_SESSION_ID);
        if (sessionId == null) {
            sessionId = UUID.randomUUID().toString();
            CookieHelper.addNewCookie(response, COOKIE_SESSION_ID, sessionId);
        }

        threadSessionId.set(sessionId);
        U user = get(USER, userClass());
        threadUser.set(user);

        // 用户在其他地方登录导致session不可用，则清空session并跳转至提示页面
        if (!singleSession.isAvailable()) {
            remove(getSessionId());
            // 若是前往登录，无需拦截，否则强制退出
            if (request.getRequestURI().startsWith("/"+ RequestContext.getModuleProperties().loginUri)
                    && WebUtil.GET.equalsIgnoreCase(request.getMethod())) {
                return true;
            }
            WebUtil.redirect(response,WebUtil.getDomainBasePath() + RequestContext.getModuleProperties().forceExitUri);
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {// 设置session超时
        alterSessionExpire();
        // 清理ThreadLocal
        cleanThreadSession();
    }

    /**
     * 变更session过期时间为当前时间后半小时
     */
    private void alterSessionExpire() {
        if (getSessionId() == null) {
            return;
        }
        getHashOperations(getSessionId()).expire(getTimeout(), TimeUnit.SECONDS);
    }

    /**
     * 清理当前线程session信息
     */
    public void cleanThreadSession() {
        threadSessionId.set(null);
        threadUser.set(null);
    }

    /**
     * @return 用户类型
     */
    public abstract Class<U> userClass();

    @Override
    public void invalid(String key) {
        remove(key);
    }
}
