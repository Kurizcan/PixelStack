package com.pixelstack.ims.mapper.SqlProvider;

import org.apache.ibatis.jdbc.SQL;

public class CountSqlProvider {

    public String getFollowCount() {
        return new SQL()
                .SELECT("count(*)")
                .FROM("tb_follow_relate")
                .WHERE("uid = #{uid}")
                .toString();
    }

    public String getStarCount() {
        return new SQL()
                .SELECT("count(*)")
                .FROM("tb_star_relate")
                .WHERE("uid = #{uid}")
                .toString();
    }

    public String getThumbCount() {
        return new SQL()
                .SELECT("count(*)")
                .FROM("tb_thumb_relate")
                .WHERE("uid = #{uid}")
                .toString();
    }

    public String getFansCount() {
        return new SQL()
                .SELECT("count(*)")
                .FROM("tb_follow_relate")
                .WHERE("fid = #{fid}")
                .toString();
    }


}
