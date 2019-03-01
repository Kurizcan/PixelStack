package com.pixelstack.ims.controller;

import com.alibaba.fastjson.JSONObject;
import com.pixelstack.ims.common.Auth.Authentication;
import com.pixelstack.ims.common.Auth.UserLoginToken;
import com.pixelstack.ims.common.exception.Result_Error;
import com.pixelstack.ims.common.exception.Result_Success;
import com.pixelstack.ims.common.exception.InternalErrorException;
import com.pixelstack.ims.common.exception.NotFoundException;
import com.pixelstack.ims.domain.User;

import com.pixelstack.ims.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping(value = {"/register"})
    public Object userRegister(User user) throws InternalErrorException {
        int status = userService.register(user);
        if (status == ERROR) {
            // 注册出现错误，抛出错误
            throw new InternalErrorException("username or password null", Result_Error.ErrorCode.INSERT_ERROR.getCode());
        }
        else {
            return new Result_Success(200, "SUSSESS EFFECT " + status + " row");
        }
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
                HashMap <String, Object> data = new HashMap<String, Object>();
                data.put("user", login_user);
                data.put("token", token);
                result.put("status", "200");
                result.put("data", data);
            }
            else {
                result.put("status", "500");
                result.put("message", "token 创建或设置失败，请稍后重试");
            }

        }
        return result;
    }

    /*
    @PostMapping(value = {"/vaild"})
    public Object vaildUser(String token) {
        User user = userService.vaild(token);
        HashMap <String, Object> data = new HashMap<String, Object>();
        result.clear();
        if (user == null) {
            result.put("status", "401");
            result.put("message", "登陆已过期，请重新登陆");
        }
        else {
            result.put("status", "200");
            data.put("uid", user.getUid());
            data.put("username", user.getUsername());
            data.put("Authority", user.getAuthority());
            result.put("data", data);
        }
        return result;
    }
    */

    @PostMapping(value = {"/getUserInfo"})
    public Object getUserInfo(String token) {
        return null;
    }


    @UserLoginToken
    @PostMapping(value = {"/getMessage"})
    public String getMessage() {
        return "你已验证通过";
    }


    @UserLoginToken
    @PostMapping(value = {"/modify"})
    public Object modify(User user) {
        result.clear();
        if (userService.modify(user) == 0) {
            result.put("status", "200");
            result.put("message", "修改成功");
        }
        else {
            result.put("status", "500");
            result.put("message", "修改失败");
        }
        return result;
    }
}
