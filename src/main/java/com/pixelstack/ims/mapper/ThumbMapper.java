package com.pixelstack.ims.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface ThumbMapper {

    @Select("select count(*) from tb_thumb_relate where iid = #{iid}")
    public int getThumbByiid(int iid);

    @Insert("insert into tb_thumb_relate(uid, iid) values (#{uid}, #{iid})")
    public int addThumbByUid(@Param("iid") int iid, @Param("uid") int uid);

    @Delete("delete from tb_thumb_relate where uid = #{uid} and iid = #{iid}")
    public int deleteThumbByUid(@Param("iid") int iid, @Param("uid") int uid);

    @Select("select count(*) from tb_thumb_relate where iid = #{iid} and uid = #{uid}")
    public int checkThumbByUid(@Param("iid") int iid, @Param("uid") int uid);
}
