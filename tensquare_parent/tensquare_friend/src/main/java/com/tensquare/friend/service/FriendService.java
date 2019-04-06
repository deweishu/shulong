package com.tensquare.friend.service;


import com.tensquare.friend.client.UserClient;
import com.tensquare.friend.dao.FriendDao;
import com.tensquare.friend.dao.NoFriendDao;
import com.tensquare.friend.pojo.Friend;
import com.tensquare.friend.pojo.NoFriend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FriendService {

    @Autowired
    private FriendDao friendDao;

    @Autowired
    private NoFriendDao noFriendDao;

    @Autowired
    private UserClient userClient;


    @Transactional
    public int addFriend(String userId, String friendid) {
        // 判断是否添加过了 (就是根据userId和friendid作为条件查询数据库, 能查询出来的数量>0, 添加过了)
        int count = friendDao.findCount(userId, friendid);
        if(count > 0){
            //返回0 代表之前添加过
            return 0;
        }

        //没有添加过, 就是向friend表添加一条数据
        Friend friend = new Friend();
        friend.setUserid(userId);
        friend.setFriendid(friendid);
        friend.setIslike("0");
        friendDao.save(friend);

        //判断对方之前是否添加过你, 如果添加过, islike全部置为1
        if(friendDao.findCount(friendid,userId)>0){
            //更新isLike
            friendDao.updateLike(userId,friendid,"1");
            friendDao.updateLike(friendid,userId,"1");
        }

        //调用tensquare_user微服务, 变更粉丝数和关注数 (自己的关注数+1,对方的粉丝数+1 )
        userClient.incFollow(userId,1);
        userClient.incFans(friendid,1);

        return 1;
    }

    /**
     * 添加非好友
     * @param userId
     * @param friendid
     */
    public void addNoFriend(String userId, String friendid) {
        //就是向noFriend里面添加一条数据
        NoFriend noFriend = new NoFriend();
        noFriend.setUserid(userId);
        noFriend.setFriendid(friendid);
        noFriendDao.save(noFriend);
    }

    /**
     * 删除好友
     * @param userId
     * @param friendid
     */
    @Transactional
    public void deleteFriend(String userId, String friendid) {
        //tb_friend表中当前记录删除;
        friendDao.deleteFriend(userId,friendid);

        //向不喜欢表中添加记录 ;
        addNoFriend(userId,friendid);

        //如果是双向喜欢,则另外一方的islike置为0,
        friendDao.updateLike(friendid,userId,"0");

        //调用tensquare-user微服务, 粉丝数和关注数变更  (自己的关注数-1,对方的粉丝数-1 )
        userClient.incFollow(userId,-1);
        userClient.incFans(friendid,-1);
    }
}
