package com.qkl.user.cache;

import com.qkl.common.cache.AbstractCache;
import org.springframework.stereotype.Component;

/**
 * @author dengjihai
 * @create 2018-10-18
 **/
@Component
public class CommentLoveNumCache extends AbstractCache<Boolean> {


    public void save(String commentId,String userId){
        put(commentId+","+userId,true);
    }

    /**
     * 查询出该用户，和该评论，是否已经点赞过。
     * @param commentId
     * @param userId
     * @return
     */
    public Boolean validateAdd(String commentId,String userId){
        Boolean str=get(commentId+","+userId);
        return str==null||str==false?true:false;
    }

    @Override
    protected String getPrefix() {
        return "chain_store_comment_love_num";
    }

    @Override
    protected Integer getTimeout() {
        return RedisTime.FOREVER;
    }

    @Override
    public void invalid(String key) {
        remove(key);
    }
}
