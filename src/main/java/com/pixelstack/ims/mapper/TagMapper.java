package com.pixelstack.ims.mapper;

import com.pixelstack.ims.domain.Tag;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

public interface TagMapper {

    @Select("select * from tb_Tag where tid = #{tid}")
    public Tag selectTagById(int tid);

    @Insert("insert into tb_Tag(tid,tagname) values (#{tid},#{tagname})")
    public int addTag(Tag tag);

    @Delete("delete from tb_Tag where tid=#{tid}")
    public int deleteTagById(int tid);
}
