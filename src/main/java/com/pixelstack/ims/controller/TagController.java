package com.pixelstack.ims.controller;

import com.pixelstack.ims.common.Result_Error;
import com.pixelstack.ims.common.Result_Success;
import com.pixelstack.ims.common.exception.NotFoundException;
import com.pixelstack.ims.domain.Tag;
import com.pixelstack.ims.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="/tag")     // 通过这里配置使下面的映射都在 /tag 下
public class TagController {

    public final static int ERROR = 0;

    @Autowired
    TagService tagService;

    @GetMapping(value={"/selectTagById"})
    public Tag selectTagById(String id) throws NotFoundException {
        Tag tag = tagService.selectTagById(Integer.parseInt(id));
        if (tag == null) {
            throw new NotFoundException("address " + id + " is not exist!", Result_Error.ErrorCode.USER_NOT_FOUND.getCode());
        }
        return tag;
    }

    @PostMapping(value={"/addTag"})
    public Object addTag(Tag tag){
        int status = tagService.addTag(tag);
        if (status == ERROR) {
            // 插入出现错误，抛出错误
            return null;
        }
        else {
            return new Result_Success("SUSSESS EFFECT " + status + " row");
        }
    }

    @RequestMapping(value={"/deleteTag"}, method=RequestMethod.POST)
    public Object deleteTag(String id) throws NotFoundException {
        int status = tagService.deleteTagById(Integer.parseInt(id));
        if (status == ERROR) {
            // 删除出现错误
            throw new NotFoundException("User " + id + " is not found", Result_Error.ErrorCode.USER_NOT_FOUND.getCode());
        }
        else {

            return new Result_Success("SUSSESS EFFECT " + status + " row");
        }
    }
}
