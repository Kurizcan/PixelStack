package com.pixelstack.ims.mapper.SqlProvider;
import com.pixelstack.ims.domain.Tag;
import org.apache.ibatis.jdbc.SQL;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

public class ImageSqlProvider {


    public String addImage() {
        return new SQL()
                .INSERT_INTO("tb_image_info")
                .VALUES("title, author, upload, url",
                        "#{title}, #{author}, #{upload}, #{url}")
                .toString();
    }

    public String deleteImage() {
        return new SQL()
                .DELETE_FROM("tb_image_info")
                .WHERE("iid = #{iid}")
                .toString();
    }

    public String getImageStarCount() {
        return new SQL()
                .SELECT("count(*)")
                .FROM("tb_star_relate")
                .WHERE("iid = #{iid}")
                .toString();
    }

    public String getImageThumbCount() {
        return new SQL()
                .SELECT("count(*)")
                .FROM("tb_thumb_relate")
                .WHERE("iid = #{iid}")
                .toString();
    }

    public String selectMyStars(Map map) {
        List<Integer> iids = (List<Integer>) map.get("list");
        StringBuilder sb = new StringBuilder();
        sb.append("select iid, count, url from tb_image_info where iid in (");
        MessageFormat mf = new MessageFormat(
                "#'{'list[{0}]} "
        );
        for (int i = 0; i < iids.size(); i++) {
            sb.append(mf.format(new Object[]{i}));
            if (i < iids.size() - 1) {
                sb.append(",");
            }
        }
        sb.append(") ORDER BY upload DESC");
        //System.out.println(sb.toString());
        return sb.toString();
    }

    public String updateTitle() {
        return new SQL()
                .UPDATE("tb_image_info")
                .SET("title = #{title}")
                .WHERE("iid = #{iid}")
                .toString();
    }

    public String selectByTitleOrAuthor(Map map) {
        int type = Integer.parseInt(map.get("type").toString());
        String search = map.get("search").toString();
        String sql = new SQL() {{
            SELECT("iid, url, count");
            FROM("tb_image_info");
            if (type == 1 && !search.equals(""))
                WHERE("author = #{search}");
            if (type == 2 && !search.equals(""))
                WHERE("title like CONCAT('%',CONCAT(#{search},'%'))");
        }}.toString();
        //System.out.println(sql);
        return sql;
    }


}
