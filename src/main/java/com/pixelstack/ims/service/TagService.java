package com.pixelstack.ims.service;

import com.pixelstack.ims.domain.Tag;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface TagService {

    @Select("select * from tb_Tag where tid = #{tid}")
    public Tag selectTagById(int tid);

    @Insert("insert into tb_Tag(tid,tagname) values (#{tid},#{tagname})")
    public int addTag(Tag tag);

    @Delete("delete from tb_Tag where tid=#{tid}")
    public int deleteTagById(int tid);
}
