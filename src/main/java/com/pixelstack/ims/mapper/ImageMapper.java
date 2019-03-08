package com.pixelstack.ims.mapper;

import com.github.pagehelper.Page;
import com.pixelstack.ims.domain.Image;
import com.pixelstack.ims.mapper.SqlProvider.ImageSqlProvider;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

public interface ImageMapper {

    @UpdateProvider(type = ImageSqlProvider.class, method = "addImage")
    @Options(useGeneratedKeys = true, keyProperty = "iid", keyColumn = "iid")
    public int addImage(Image image);

    @DeleteProvider(type = ImageSqlProvider.class, method = "deleteImage")
    public int deleteImage(@Param("iid") int iid);

    @Update("update tb_image_info set title = #{title} where iid = #{iid}")
    public int addTiltle(@Param("title") String title, @Param("iid") int iid);

    @Select("select * from tb_image_info where iid = #{iid}")
    public Image getImageByiid(@Param("iid") int iid);

    @SelectProvider(type = ImageSqlProvider.class, method = "getImageStarCount")
    public int getImageStarCount(@Param("iid") int iid);

    @SelectProvider(type = ImageSqlProvider.class, method = "getImageThumbCount")
    public int getImageThumbCount(@Param("iid") int iid);

    @Select("SELECT tagname FROM tb_tag WHERE tid IN (SELECT tid from tb_tag_relate WHERE iid = #{iid})")
    public List<String> getImageAllTagsByiid(@Param("iid") int iid);

    @Select("select iid, url, count from tb_image_info ORDER BY upload DESC")
    @ResultType(List.class)
    Page<Map<String,Object>> getImageList();

    @Select("select iid, url, count from tb_image_info i, tb_user_info u where uid = #{uid} and u.username = i.author ORDER BY upload DESC")
    @ResultType(List.class)
    List<Map<String,Object>> getImageListByUid(int uid);

    @Select("SELECT uid FROM tb_user_info u, tb_image_info i WHERE u.username = i.author AND iid = #{iid} ")
    @ResultType(Integer.class)
    public Integer getUidbyImage(int iid);

    @SelectProvider(type = ImageSqlProvider.class, method = "selectMyStars")
    @ResultType(List.class)
    public List<Map<String, Object>> selectMyStars(@Param("list") List<Integer> list);

    @Select("SELECT tb_tag_relate.iid, url, count from tb_tag, tb_tag_relate, tb_image_info " +
            "WHERE tb_tag.tid = tb_tag_relate.tid AND " +
            "tagname = #{tagname} AND tb_image_info.iid = tb_tag_relate.iid ORDER BY upload DESC")
    @ResultType(List.class)
    public List<Map<String, Object>> getListByTagName(String tagname);

    @Update("update tb_image_info set count = count + 1 where iid = #{iid}")
    public int updateCount(int iid);

    @UpdateProvider(type = ImageSqlProvider.class, method = "updateTitle")
    public int updateTitle(@Param("iid") int iid, @Param("title") String title);

    @SelectProvider(type = ImageSqlProvider.class, method = "selectByTitleOrAuthor")
    @ResultType(List.class)
    public List<Map<String, Object>> getListByTitleOrAuthor(@Param("type") String type, @Param("search") String search);

}
