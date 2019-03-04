package com.pixelstack.ims.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.pixelstack.ims.common.Auth.UserLoginToken;
import com.pixelstack.ims.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(value="/image")     // 通过这里配置使下面的映射都在 /user 下
public class ImageController {

    JSONObject result = new JSONObject();

    @Autowired
    ImageService imageService;

    @ResponseBody
    @GetMapping(value = {"/getImageDetails"})
    public Object getImageDetails(int iid) {
        HashMap details = (HashMap) imageService.getImageDetailByiid(iid);
        result.clear();
        if (details == null) {
            result.put("status", 500);
            result.put("message", "图片不存在");
        }
        else {
            result.put("title", details.get("title"));
            result.put("author", details.get("author"));
            result.put("upload", details.get("upload"));
            result.put("url", details.get("url"));
            result.put("count", details.get("count"));
            result.put("tags", details.get("tags"));
            result.put("star", details.get("star"));
            result.put("thumb", details.get("thumb"));
        }
        return result;
    }

    @ResponseBody
    @GetMapping(value = {"/getImageList"})
    public Object getImageList(@RequestParam(defaultValue = "1") int pageNo, @RequestParam(defaultValue = "60") int pageSize) {
        PageInfo pageInfo = imageService.getImageList(pageNo, pageSize);
        result.clear();
        result.put("imageList", pageInfo.getList());
        result.put("total", pageInfo.getTotal());
        result.put("curPage", pageInfo.getPageNum());
        result.put("prePage", pageInfo.getPrePage());
        result.put("nextPage", pageInfo.getNextPage());
        result.put("lastPage", pageInfo.getNavigateLastPage());
        return result;
    }

    @UserLoginToken
    @ResponseBody
    @GetMapping(value = {"/getImageListByUid"})
    public Object getImageListByUid(int uid) {
        List<Map<String,Object>> mapList =(List<Map<String,Object>>) imageService.getImageListByUid(uid);
        result.clear();
        result.put("imageList", mapList);
        return result;
    }
}
