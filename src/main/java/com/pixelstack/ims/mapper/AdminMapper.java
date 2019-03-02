package com.pixelstack.ims.mapper;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.pixelstack.ims.domain.User;
import com.pixelstack.ims.mapper.SqlProvider.UserSqlProvider;
import org.apache.ibatis.annotations.*;

public interface AdminMapper extends  UserMapper{

    @SelectProvider(type = UserSqlProvider.class, method = "getUsers")
    Page<User> getUserList();

    @SelectProvider(type = UserSqlProvider.class, method = "getAdmins")
    Page<User> getAdminList();

    @UpdateProvider(type = UserSqlProvider.class, method = "updateAllStatus")
    //@Update("update tb_user_info set status=#{status} where uid = #{uid}")
    public int updateStateById(JSONObject jsonObject);

}
