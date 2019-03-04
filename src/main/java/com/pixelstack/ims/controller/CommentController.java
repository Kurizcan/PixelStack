package com.pixelstack.ims.controller;


import com.alibaba.fastjson.JSONObject;
import com.pixelstack.ims.common.Auth.UserLoginToken;
import com.pixelstack.ims.domain.Comment;
import com.pixelstack.ims.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value="/Comment")     // 通过这里配置使下面的映射都在 /user 下
public class CommentController {

    @Autowired
    CommentService commentService;

    JSONObject result = new JSONObject();

    @ResponseBody
    @GetMapping(value = {"/getCommentsByiid"})
    public Object getCommentsByiid(int iid) {
        List<Map<String,String>> commentList = (List<Map<String,String>>)commentService.getCommentByiid(iid);
        result.clear();
        result.put("status", 200);
        result.put("comments", commentList);
        return result;
    }

    @UserLoginToken
    @ResponseBody
    @PostMapping(value = {"/add"})
    public Object add(int iid, int uid, String content) {
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setCdate(new Date());
        comment.setUid(uid);
        result.clear();
        if (commentService.addComment(iid, uid, comment)) {
            result.put("status", 200);
            result.put("message", "评论成功");
        }
        else {
            result.put("status", 500);
            result.put("message", "评论失败");
        }
        return result;
    }

}
