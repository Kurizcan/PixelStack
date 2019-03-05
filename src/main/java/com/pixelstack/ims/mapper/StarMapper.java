package com.pixelstack.ims.mapper;

import org.apache.ibatis.annotations.*;

import java.util.List;

public interface StarMapper {

    @Select("select count(*) from tb_star_relate where iid = #{iid}")
    public int getStarByiid(int iid);

    @Insert("insert into tb_star_relate(uid, iid) values (#{uid}, #{iid})")
    public int addStarByUid(@Param("iid") int iid, @Param("uid") int uid);

    @Delete("delete from tb_star_relate where uid = #{uid} and iid = #{iid}")
    public int deleteStarByUid(@Param("iid") int iid, @Param("uid") int uid);

    @Select("select count(*) from tb_star_relate where iid = #{iid} and uid = #{uid}")
    public int checkStarByUid(@Param("iid") int iid, @Param("uid") int uid);

    @Select("select iid from tb_star_relate where uid = #{uid}")
    @ResultType(List.class)
    public List<Integer> getStars(int uid);

}
