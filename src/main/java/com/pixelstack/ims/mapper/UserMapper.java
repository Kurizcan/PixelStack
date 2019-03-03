package com.pixelstack.ims.mapper;

import com.pixelstack.ims.domain.User;
import com.pixelstack.ims.mapper.SqlProvider.CountSqlProvider;
import com.pixelstack.ims.mapper.SqlProvider.UserSqlProvider;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface UserMapper {

    @Insert("insert into tb_user_info(username,password,authority,email,status) " +
            "values (#{username},#{password},#{authority},#{email},#{status})")
    public int addUser(User user);

    @Select("select * from tb_user_info where username = #{username} and password = #{password}")
    public User checkUser(User user);

    @Select("select * from tb_user_info where uid = #{uid}")
    public User selectUserById(@Param("uid") int uid);

    @UpdateProvider(type = UserSqlProvider.class, method = "updateUserById")
    public int updateUserById(User user);

    @SelectProvider(type = CountSqlProvider.class, method = "getStarCount")
    public int getStarCount(@Param("uid") int uid);

    @SelectProvider(type = CountSqlProvider.class, method = "getThumbCount")
    public int getThumbCount(@Param("uid") int uid);

    @SelectProvider(type = CountSqlProvider.class, method = "getFansCount")
    public int getFansCount(@Param("fid") int fid);   // 粉丝数

    @SelectProvider(type = CountSqlProvider.class, method = "getFollowCount")
    public int getFollowCount(@Param("uid") int uid);

    @Select("select * from tb_user_info where authority = 'user'")
    public List<User> getAllUser();

}
