package com.pixelstack.ims.mapper.SqlProvider;

import com.pixelstack.ims.domain.Tag;
import org.apache.ibatis.jdbc.SQL;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

public class TagSqlProvider {

    public String InsertTags(Map map) {
        List<Tag> tags = (List<Tag>) map.get("list");
        StringBuilder sb = new StringBuilder();
        sb.append("insert into tb_tag(tagname) values");
        MessageFormat mf = new MessageFormat(
                "(#'{'list[{0}].tagname})"
        );
        for (int i = 0; i < tags.size(); i++) {
            sb.append(mf.format(new Object[]{i}));
            if (i < tags.size() - 1) {
                sb.append(",");
            }
        }
        return sb.toString();
    }

    public String InsertTag_Relate() {
        String sql =  new SQL()
                .INSERT_INTO("tb_tag_relate")
                .VALUES("iid, tid", "#{iid}, #{tid}")
                .toString();
        return sql;
    }

}
