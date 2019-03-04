package com.pixelstack.ims.controller;

import com.alibaba.fastjson.JSONObject;
import com.pixelstack.ims.common.Auth.Authentication;
import com.pixelstack.ims.common.Auth.UserLoginToken;
import com.pixelstack.ims.common.exception.Result_Error;
import com.pixelstack.ims.common.exception.InternalErrorException;
import com.pixelstack.ims.common.exception.NotFoundException;
import com.pixelstack.ims.domain.Tag;
import com.pixelstack.ims.domain.User;

import com.pixelstack.ims.service.GeneralService;
import com.pixelstack.ims.service.ImageService;
import com.pixelstack.ims.service.TagService;
import com.pixelstack.ims.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping(value="/user")     // 通过这里配置使下面的映射都在 /user 下
public class UserController {

    public final static int ERROR = 0;

    JSONObject result = new JSONObject();

    @Autowired
    UserService userService;

    @Autowired
    Authentication authentication;

    @Autowired
    ImageService imageService;

    @Autowired
    GeneralService generalService;

    @Autowired
    TagService tagService;

    @ResponseBody
    @PostMapping(value = {"/register"})
    public Object userRegister(User user) throws InternalErrorException {
        if (user.getPassword() == null || user.getUsername() == null) {
            result.put("status", "500");
            result.put("message", "用户名或者密码为空");
            return result;
        }
        int status = userService.register(user, "user");
        if (status == ERROR) {
            // 注册出现错误，抛出错误
            throw new InternalErrorException("username or password null", Result_Error.ErrorCode.INSERT_ERROR.getCode());
        }
        else {
            result.put("status", "200");
            result.put("message", "注册成功");
        }
        return result;
    }


    @PostMapping(value = {"/login"})
    public Object checkUser(User user) throws NotFoundException{
        User login_user = userService.login(user);
        if (login_user == null) {
            result.put("status", "500");
            result.put("message", "用户不存在或者密码不正确");
        }
        else if (login_user.getStatus().equals("frozen")) {
            result.put("status", "501");
            result.put("message", "账户被冻结");
        }
        else if (login_user.getStatus().equals("terminate")) {
            result.put("status", "502");
            result.put("message", "账户被封锁");
        }
        else {
            // 登陆成功，创建 token 并保存至 redis 中
            String token = authentication.createToken(login_user);
            if (token != null && authentication.storeToken(token, login_user.getUid())) {
                HashMap <String, Object> userInfo = new HashMap<String, Object>();
                userInfo.put("uid", login_user.getUid());
                userInfo.put("username", login_user.getUsername());
                userInfo.put("email", login_user.getEmail());
                userInfo.put("introduction", login_user.getIntroduction());
                userInfo.put("status", login_user.getStatus());
                userInfo.put("authority", login_user.getAuthority());
                userInfo.put("token", token);
                result.put("status", "200");
                result.put("userInfo", userInfo);
            }
            else {
                result.put("status", "500");
                result.put("message", "token 创建或设置失败，请稍后重试");
            }

        }
        return result;
    }


    @UserLoginToken
    @GetMapping(value = {"/getUserInfo"})
    public Object getUserInfo(int uid) {
        Object userInfo = userService.getUserInfo(uid);
        result.clear();
        if (userInfo == null) {
            result.put("status", "40401");
            result.put("message", "无此用户");
        }
        else {
            result.put("status", "200");
            result.put("userInfo", userInfo);
        }
        return result;
    }


    @UserLoginToken
    @PostMapping(value = {"/modify"})
    public Object modify(User user) {
        result.clear();
        int status = userService.modify(user);
        if (status == 0) {
            result.put("status", "500");
            result.put("message", "信息修改不规范");
        }
        else {
            if (status == 2) {
                result.put("status", "201");
                result.put("message", "密码已修改，请重新登陆");
            }
            else {
                result.put("status", "200");
                result.put("message", "修改成功");
            }
        }
        return result;
    }


    @UserLoginToken
    @PostMapping(value = {"/upload"})
    public Object upload(@RequestParam("file") MultipartFile[] files, int uid, String title) throws IOException {
        result.clear();
        if (files == null || files.length == 0) {
            result.put("status", "500");
            result.put("message", "前上传照片");
        }
        String username = (String) userService.getUserInfo(uid).get("username");
        if (username == null) {
            result.put("status", "500");
            result.put("message", "用户不存在");
        }
        else {
            HashMap imageInfo = (HashMap) imageService.upload(files, username, title);
            ArrayList postList = (ArrayList) imageInfo.get("postList");
            ArrayList errorList  = (ArrayList) imageInfo.get("errorList");
            if (errorList == null) {
                result.put("fail", 0);
            }
            else {
                result.put("errors", errorList);
                result.put("fail", errorList.size());

            }

            result.put("upload", postList);
            result.put("success", postList.size());
            result.put("status", "200");
        }
        return result;
    }

    @UserLoginToken
    @PostMapping(value = {"/deleteImage"})
    public Object deleteImage(int iid) {
        result.clear();
        if (imageService.deleteImageByiid(iid)) {
            result.put("status", 200);
            result.put("message", "删除成功");
        } else {
            result.put("status", 500);
            result.put("message", "删除失败");
        }
        return result;
    }

    @UserLoginToken
    @Transactional
    @PostMapping(value = {"/addTagsandTitle"})
    public Object addTagsandTitle(@RequestBody JSONObject jsonObject) throws IOException{
        ArrayList tags = (ArrayList) jsonObject.get("tags");
        ArrayList pids = (ArrayList) jsonObject.get("pids");
        List<Tag> tagsList = new ArrayList();
        List<Tag> newTagList = new ArrayList<>();
        int i, j;
        //newTagList = null;
        result.clear();
        for (i = 0; i < tags.size(); i++) {
            Tag tag = tagService.selectTagByName(tags.get(i).toString());
            System.out.println(10);
            if (tag == null) {
                tag = new Tag();
                tag.setTagname(tags.get(i).toString());
                newTagList.add(tag);
            }
            else {
                System.out.println(5);
                tagsList.add(tag);
            }
        }
        try {
            System.out.println(6);
            if (newTagList != null) {

                if (!generalService.addTags(newTagList)) {
                    result.put("status", 501);
                    result.put("message", "加入标签失败");
                }

                // 新标签插入成功
                for (i = 0; i < newTagList.size(); i++) {
                    tagsList.add(newTagList.get(i));
                    System.out.println(1);
                }

                System.out.println(2);
            }

            System.out.println(7);
            System.out.println(tagsList.size());

            for (i = 0; i < tagsList.size(); i++) {
                System.out.println(8);
                for (j = 0; j < pids.size(); j++) {
                    System.out.println(9);
                    if (!tagService.addTagsRelate((int)pids.get(j), tagsList.get(i).getTid())) {
                        result.put("status", 502);
                        result.put("message", "加入标签-相片关系失败");
                    }
                    System.out.println(3);
                }
            }

            result.put("status", 200);
            result.put("tagIsAdd", "Yes");

        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return result;
    }



}
