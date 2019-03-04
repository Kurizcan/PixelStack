package com.pixelstack.ims.mapper;

import com.pixelstack.ims.domain.Comment;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

public interface CommentMapper {

    @Select("SELECT username, content, cdate from tb_comment, tb_user_info WHERE cid IN (SELECT cid FROM tb_comment_relate WHERE iid = #{iid}) " +
            "AND tb_user_info.uid = tb_comment.uid")
    @ResultType(List.class)
    public List<Map<String,String>> getCommentByiid(@Param("iid") int iid);

    @Insert("insert into tb_comment(uid, content, cdate) values(#{uid}, #{content}, #{cdate})")
    @Options(useGeneratedKeys = true, keyProperty = "cid", keyColumn = "cid")
    public int addComment(Comment comment);

    @Insert("insert into tb_comment_relate(iid, cid) values (#{iid}, #{cid})")
    public int addCommentRelate(@Param("iid") int iid, @Param("cid") int cid);

}
