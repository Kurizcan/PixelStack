package com.pixelstack.ims.mapper.SqlProvider;

import com.alibaba.fastjson.JSONObject;
import com.pixelstack.ims.domain.User;
import org.apache.ibatis.jdbc.SQL;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class UserSqlProvider {

    public String updateStateById() {
        return new SQL()
                .UPDATE("tb_user_info")
                .SET("status = {#status}")
                .WHERE("uid = {#uid}")
                .toString();
    }

    public String updateUserById(User user) {
        return new SQL() {{
            UPDATE("tb_user_info");
            if (user.getPassword() != null && !user.getPassword().equals("")) {
                SET("password = #{password}");
            }
            if (user.getUsername() != null) {
                SET("username = #{username}");
            }
            if (user.getEmail() != null) {
                SET("email = #{email}");
            }
            if (user.getIntroduction() != null) {
                SET("introduction = #{introduction}");
            }
            WHERE("uid = #{uid}");
        }}.toString();

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

        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date date = new Date();

        String totalSql = "";

        StringBuffer sql = new StringBuffer();

        Map<String,Object> map = (Map<String,Object>)jsonObject;

        Iterator<Map.Entry<String, Object>> entries = map.entrySet().iterator();

        while (entries.hasNext()) {

            Map.Entry<String, Object> entry = entries.next();

           // String sql = "update tb_user_info set status = '";
            sql.append("update tb_user_info set status = '");
            sql.append(entry.getValue());
            sql.append("', admindate = '");
            sql.append(sf.format(date));
            sql.append("' where uid = ");
            sql.append(entry.getKey());
            sql.append(";");
        }
        totalSql = sql.toString();
        System.out.println(totalSql);
        return totalSql;
    }

    public String selectAllUsers(Map map) {
        List<String> uids = (List<String>) map.get("list");
        StringBuilder sb = new StringBuilder();
        sb.append("select uid, password from tb_user_info where uid in (");
        MessageFormat mf = new MessageFormat(
                "#'{'list[{0}]}"
        );
        for (int i = 0; i < uids.size(); i++) {
            sb.append(mf.format(new Object[]{i}));
            if (i < uids.size() - 1) {
                sb.append(", ");
            }
        }
        sb.append(")");
        System.out.println(sb.toString());
        return sb.toString();
    }
}
