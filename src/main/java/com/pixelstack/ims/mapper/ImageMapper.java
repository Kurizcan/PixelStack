package com.pixelstack.ims.mapper;

import com.pixelstack.ims.domain.Image;
import com.pixelstack.ims.mapper.SqlProvider.ImageSqlProvider;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.UpdateProvider;

public interface ImageMapper {

    @UpdateProvider(type = ImageSqlProvider.class, method = "addImage")
    @Options(useGeneratedKeys = true, keyProperty = "iid", keyColumn = "iid")
    public int addImage(Image image);

}
