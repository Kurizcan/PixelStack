package com.pixelstack.ims.mapper;

import com.pixelstack.ims.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface UserMapper {

    @Insert("insert into tb_User_Info(username,password,authority,email,status) " +
            "values (#{username},#{password},#{authority},#{email},#{status})")
    public int addUser(User user);

    @Select("select * from tb_User_Info where username = #{username} and password = #{password}")
    public User checkUser(User user);

    @Select("select * from tb_User_Info where uid = #{uid}")
    public User selectUserById(@Param("uid") int uid);

}
