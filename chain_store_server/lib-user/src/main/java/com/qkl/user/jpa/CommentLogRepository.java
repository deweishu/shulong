package com.qkl.user.jpa;

import com.qkl.user.entity.CommentLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;


public interface CommentLogRepository extends JpaRepository<CommentLog, String>, JpaSpecificationExecutor<CommentLog> {


    CommentLog findByUser_IdAndApk_Id(String userId,String apkId);

    /**
     * 通过apkid ，分数，查询出好评（4-5）和差评（1）
     * */
    Integer countByApk_IdAndCommentNumEquals(String apkId,Integer commentNum);


    @Query(value ="select count(c.id) from u_comment_log c where c.apk_id=?1 and c.comment_num>=2 and c.comment_num<4",nativeQuery = true)
    Integer countApkNum(String apkId);


    @Query(value = "select  sum(c.comment_num) from u_comment_log c where c.apk_id=?1 ",nativeQuery = true)
    Integer sumApkNum(String apkId);

}
