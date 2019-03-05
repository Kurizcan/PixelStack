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

    /*
    public String addTiltle(ArrayList pids, String title) {
        String totalSql = "update tb_image_info set title = " + title + " where iid in (";
        Iterator<String> iterator = pids.iterator();
        while (iterator.hasNext()) {
            String sql = iterator.next();
            totalSql += sql + ",";
        }
        totalSql.replace(totalSql.charAt(totalSql.length() - 1), ')');
        System.out.println(totalSql);
        return totalSql;
    }
    */

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
        sb.append(")");
        //System.out.println(sb.toString());
        return sb.toString();
    }


}
