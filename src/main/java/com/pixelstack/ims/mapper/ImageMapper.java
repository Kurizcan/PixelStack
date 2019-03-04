package com.pixelstack.ims.mapper;

import com.github.pagehelper.Page;
import com.pixelstack.ims.domain.Comment;
import com.pixelstack.ims.domain.Image;
import com.pixelstack.ims.domain.Tag;
import com.pixelstack.ims.domain.User;
import com.pixelstack.ims.mapper.SqlProvider.ImageSqlProvider;
import com.pixelstack.ims.mapper.SqlProvider.UserSqlProvider;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface ImageMapper {

    @UpdateProvider(type = ImageSqlProvider.class, method = "addImage")
    @Options(useGeneratedKeys = true, keyProperty = "iid", keyColumn = "iid")
    public int addImage(Image image);

    @DeleteProvider(type = ImageSqlProvider.class, method = "deleteImage")
    public int deleteImage(@Param("iid") int iid);

    //@UpdateProvider(type = ImageSqlProvider.class, method = "addTiltle")
    //public int addTiltle(@Param("pids") ArrayList pids, @Param("title") String title);

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

    @Select("select iid, url, count from tb_image_info")
    @ResultType(List.class)
    Page<Map<String,Object>> getImageList();

    @Select("select iid, url, count from tb_image_info i, tb_user_info u where uid = #{uid} and u.username = i.author")
    @ResultType(List.class)
    List<Map<String,Object>> getImageListByUid(int uid);

}
