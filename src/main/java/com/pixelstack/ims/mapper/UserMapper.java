package com.pixelstack.ims.mapper;

import com.pixelstack.ims.domain.User;
import com.pixelstack.ims.mapper.SqlProvider.UpdateUserSqlProvider;
import org.apache.ibatis.annotations.*;

public interface UserMapper {

    @Insert("insert into tb_user_info(username,password,authority,email,status) " +
            "values (#{username},#{password},#{authority},#{email},#{status})")
    public int addUser(User user);

    @Select("select * from tb_user_info where username = #{username} and password = #{password}")
    public User checkUser(User user);

    @Select("select * from tb_user_info where uid = #{uid}")
    public User selectUserById(@Param("uid") int uid);

    @UpdateProvider(type = UpdateUserSqlProvider.class, method = "updateUserById")
    public int updateUserById(User user);


}
