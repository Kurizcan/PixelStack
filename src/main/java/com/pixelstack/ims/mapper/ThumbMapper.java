package com.pixelstack.ims.mapper;

import org.apache.ibatis.annotations.Select;

public interface ThumbMapper {

    @Select("select count(*) from tb_thumb_relate where iid = #{iid}")
    public int getThumByiid(int iid);
}
