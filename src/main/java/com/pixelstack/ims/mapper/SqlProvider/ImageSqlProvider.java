package com.pixelstack.ims.mapper.SqlProvider;

import org.apache.ibatis.jdbc.SQL;

public class ImageSqlProvider {


    public String addImage() {
        return new SQL()
                .INSERT_INTO("tb_image_info")
                .VALUES("title, author, upload, url",
                        "#{title}, #{author}, #{upload}, #{url}")
                .toString();
    }

}
