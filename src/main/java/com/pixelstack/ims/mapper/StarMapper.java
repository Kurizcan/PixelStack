package com.pixelstack.ims.mapper;

import com.pixelstack.ims.domain.Tag;
import org.apache.ibatis.annotations.Select;

public interface StarMapper {

    @Select("select count(*) from tb_star_relate where iid = #{iid}")
    public int getStarByiid(int iid);

}
