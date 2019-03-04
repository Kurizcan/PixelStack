package com.pixelstack.ims.mapper;

import com.alibaba.fastjson.JSONObject;
import com.pixelstack.ims.domain.Tag;
import com.pixelstack.ims.domain.User;
import com.pixelstack.ims.mapper.SqlProvider.ImageSqlProvider;
import com.pixelstack.ims.mapper.SqlProvider.TagSqlProvider;
import netscape.javascript.JSObject;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface TagMapper {

    @Select("select * from tb_tag where tagname = #{tagname}")
    public Tag selectTagByName(String tagname);

    @Insert("insert into tb_tag(tid,tagname) values (#{tid},#{tagname})")
    public int addTag(Tag tag);

    @Delete("delete from tb_tag where tid=#{tid}")
    public int deleteTagById(int tid);

    @InsertProvider(type = TagSqlProvider.class, method = "InsertTags")
    @Options(useGeneratedKeys = true, keyProperty = "tid", keyColumn = "tid")
    public int addTags(@Param("list") List<Tag> Tags);

    @InsertProvider(type = TagSqlProvider.class, method = "InsertTag_Relate")
    public int addTagsRelate(@Param("iid") int iid, @Param("tid") int tid);



}
