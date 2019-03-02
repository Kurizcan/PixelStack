package com.pixelstack.ims.controller;

import com.alibaba.fastjson.JSONObject;
import com.pixelstack.ims.common.Auth.Authentication;
import com.pixelstack.ims.common.Auth.UserLoginToken;
import com.pixelstack.ims.common.exception.Result_Error;
import com.pixelstack.ims.common.exception.InternalErrorException;
import com.pixelstack.ims.common.exception.NotFoundException;
import com.pixelstack.ims.domain.User;

import com.pixelstack.ims.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping(value="/user")     // 通过这里配置使下面的映射都在 /user 下
public class UserController {

    public final static int ERROR = 0;

    JSONObject result = new JSONObject();

    @Autowired
    UserService userService;

    @Autowired
    Authentication authentication;

    @ResponseBody
    @PostMapping(value = {"/register"})
    public Object userRegister(User user) throws InternalErrorException {
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
        result.clear();
        if (login_user == null) {
            result.put("status", "500");
            result.put("message", "用户不存在或者密码不正确");
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
        if (user.getUsername() == null || user.getPassword() == null || user.getEmail() == null) {
            result.put("status", "500");
            result.put("message", "信息修改不规范");
        }
        else {
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
        }
        return result;
    }

    @UserLoginToken
    @PostMapping(value = {"/getMessage"})
    public String getMessage() {
        return "你已验证通过";
    }
}
