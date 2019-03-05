package com.pixelstack.ims.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.pixelstack.ims.common.Auth.UserLoginToken;
import com.pixelstack.ims.service.GeneralService;
import com.pixelstack.ims.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@RequestMapping(value="/image")     // 通过这里配置使下面的映射都在 /user 下
public class ImageController {

    JSONObject result = new JSONObject();

    @Autowired
    ImageService imageService;

    @Autowired
    GeneralService generalService;

    @ResponseBody
    @GetMapping(value = {"/getImageDetails"})
    public Object getImageDetails(int iid, @RequestParam(defaultValue = "0") int uid) {
        HashMap details = (HashMap) imageService.getImageDetailByiid(iid, uid);
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
            result.put("isStar", details.get("isStar"));
            result.put("isThumb", details.get("isThumb"));
            result.put("isFollow", details.get("isFollow"));
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


    @UserLoginToken
    @GetMapping(value = {"/isStar"})
    public Object isStar(int iid, int uid, boolean isStar) {
        result.clear();
        if (generalService.IsStar(iid, uid, isStar)) {
            result.put("status", 200);
            result.put("isStar", isStar);
        }
        else {
            result.put("status", 500);
            result.put("message", "error");
        }
        return result;
    }

    @UserLoginToken
    @GetMapping(value = {"/isThumb"})
    public Object isThumb(int iid, int uid, boolean isThumb) {
        result.clear();
        if (generalService.IsThumb(iid, uid, isThumb)) {
            result.put("status", 200);
            result.put("isThumb", isThumb);
        }
        else {
            result.put("status", 500);
            result.put("message", "error");
        }
        return result;
    }

    @UserLoginToken
    @GetMapping(value = {"/myStars"})
    public Object myStars(int uid) {
        List<Map<String, Object>> myStars = null;
        myStars = (List<Map<String, Object>>) imageService.getMyStars(uid);
        result.clear();
        result.put("status", 200);
        result.put("starList", myStars);
        return result;
    }

    @UserLoginToken
    @GetMapping(value = {"/getListByTagName"})
    public Object getListByTagName(String tagName) {
        List<Map<String, Object>> myTagsImg = null;
        myTagsImg = (List<Map<String, Object>>) imageService.getListByTagName(tagName);
        result.clear();
        result.put("status", 200);
        result.put("ImageList", myTagsImg);
        return result;
    }
}
