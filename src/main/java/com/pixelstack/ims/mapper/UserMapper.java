package com.pixelstack.ims.mapper;

import com.pixelstack.ims.domain.User;
import com.pixelstack.ims.mapper.SqlProvider.CountSqlProvider;
import com.pixelstack.ims.mapper.SqlProvider.UserSqlProvider;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

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

    @Insert("insert into tb_follow_relate(uid, fid) values (#{uid}, #{fid})")
    public int follow(@Param("uid") int uid, @Param("fid") int fid);

    @Delete("delete from tb_follow_relate where uid = #{uid} and fid = #{fid}")
    public int unfollow(@Param("uid") int uid, @Param("fid") int fid);

    @Select("select count(*) from tb_follow_relate where uid = #{uid} and fid = #{fid}")
    public int checkFollowByFid(@Param("uid") int uid, @Param("fid") int fid);

    @Select("select uid from tb_user_info where username = #{username}")
    @ResultType(Integer.class)
    public Integer getUidByUsername(String username);

    @Select("SELECT f.fid, username, introduction as introduction  FROM tb_user_info u, tb_follow_relate f " +
            "WHERE f.fid = u.uid AND f.uid = #{uid}")
    @ResultType(List.class)
    public List<Map<String, Object>> getFollowers(int uid);

    @Select("SELECT f.uid, username, introduction FROM tb_user_info u, tb_follow_relate f " +
            "WHERE f.uid = u.uid AND f.fid = #{uid} ")
    @ResultType(List.class)
    public List<Map<String, Object>> getFans(int uid);

}
