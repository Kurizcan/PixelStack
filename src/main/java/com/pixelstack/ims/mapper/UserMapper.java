package com.pixelstack.ims.mapper;

import com.pixelstack.ims.domain.User;
import com.pixelstack.ims.mapper.SqlProvider.CountSqlProvider;
import org.apache.ibatis.annotations.*;

public interface UserMapper {

    @Insert("insert into tb_user_info(username,password,authority,email,status) " +
            "values (#{username},#{password},#{authority},#{email},#{status})")
    public int addUser(User user);

    @Select("select * from tb_user_info where username = #{username} and password = #{password}")
    public User checkUser(User user);

    @Select("select * from tb_user_info where uid = #{uid}")
    public User selectUserById(@Param("uid") int uid);

    @Update("update tb_user_info set username = #{username}, password = #{password}, " +
            "email = #{email}, introduction = #{introduction} where uid = #{uid}")
    public int updateUserById(User user);

    @SelectProvider(type = CountSqlProvider.class, method = "getStarCount")
    public int getStarCount(@Param("uid") int uid);

    @SelectProvider(type = CountSqlProvider.class, method = "getThumbCount")
    public int getThumbCount(@Param("uid") int uid);

    @SelectProvider(type = CountSqlProvider.class, method = "getFansCount")
    public int getFansCount(@Param("fid") int fid);   // 粉丝数

    @SelectProvider(type = CountSqlProvider.class, method = "getFollowCount")
    public int getFollowCount(@Param("uid") int uid);


}
