package com.pixelstack.ims.mapper.SqlProvider;

import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.jdbc.SQL;
import java.util.Iterator;
import java.util.Map;

public class UserSqlProvider {

    public String updateStateById() {
        return new SQL()
                .UPDATE("tb_user_info")
                .SET("status = {#status}")
                .WHERE("uid = {#uid}")
                .toString();
    }

    public String updateUserById() {
        return new SQL()
                .UPDATE("tb_user_info")
                .SET("username = #{username}")
                .SET("password = #{password}")
                .SET("email = #{email}")
                .SET("introduction = #{introduction}")
                .WHERE("uid = #{uid}")
                .toString();
    }

    public String getUsers() {
        return new SQL()
                .SELECT("*")
                .FROM("tb_user_info")
                .WHERE("authority = 'user'")
                .toString();
    }

    public String getAdmins() {
        return new SQL()
                .SELECT("*")
                .FROM("tb_user_info")
                .WHERE("authority != 'user'")
                .toString();
    }

    public String updateAllStatus(JSONObject jsonObject) {

        String totalSql = "";

        Map<String,Object> map = (Map<String,Object>)jsonObject;

        Iterator<Map.Entry<String, Object>> entries = map.entrySet().iterator();

        while (entries.hasNext()) {

            Map.Entry<String, Object> entry = entries.next();

            String sql = "update tb_user_info set status = '" + entry.getValue() + "' where uid = " + entry.getKey() + ";";

            totalSql += sql;
        }

        return totalSql;
    }
}
