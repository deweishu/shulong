package com.tensquare.friend.dao;

import com.tensquare.friend.pojo.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface FriendDao extends JpaRepository<Friend,String>,JpaSpecificationExecutor<Friend> {

    @Query("select count(f) from Friend f where f.userid=?1 and f.friendid=?2")
    int findCount(String userid,String friendid);

    @Modifying
    @Query("update Friend f set f.islike =?3 where f.userid=?1 and f.friendid=?2")
    void updateLike(String userId, String friendid, String isLike);

    @Modifying
    @Query("delete  from  Friend f where f.userid=?1 and f.friendid=?2")
    void deleteFriend(String userId, String friendid);
}
