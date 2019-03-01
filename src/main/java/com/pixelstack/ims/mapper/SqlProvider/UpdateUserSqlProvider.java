package com.pixelstack.ims.mapper.SqlProvider;

import org.apache.ibatis.jdbc.SQL;

public class UpdateUserSqlProvider {

    public String updateUserById() {
        return new SQL().UPDATE("tb_user_info")
                .VALUES("username", "#{username}")
                .VALUES("password", "{#password}")
                .VALUES("email", "{#email}")
                .WHERE("uid=#{uid}")
                .toString();
    }
}
