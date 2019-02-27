package com.pixelstack.ims.controller;

import com.pixelstack.ims.common.Result_Error;
import com.pixelstack.ims.common.Result_Success;
import com.pixelstack.ims.common.exception.NotFoundException;
import com.pixelstack.ims.domain.User;

import com.pixelstack.ims.mapper.UserMapper;
import com.pixelstack.ims.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/user")     // 通过这里配置使下面的映射都在 /user 下
public class UserController {

    public final static int ERROR = 0;

    @Autowired
    UserService userActivity;

    @Autowired
    UserMapper userMapper;

    @PostMapping(value={"/register"})
    public Object userRegister(User user) {
        int status = userActivity.register(user);
        if (status == ERROR) {
            // 注册出现错误，抛出错误
            return null;
        }
        else {
            return new Result_Success("SUSSESS EFFECT " + status + " row");
        }
    }

    @PostMapping(value={"/login"})
    public Object checkUser(User user) throws NotFoundException{
        Boolean status = userActivity.login(user.getUsername(), user.getPassword());
        if (!status) {
            // 插入出现错误，抛出错误
            throw new NotFoundException("username or password error", Result_Error.ErrorCode.USER_NOT_FOUND.getCode());
        }
        else {
            // 保存进 session 中维持状态
            return new Result_Success("Login Success");
        }
    }

}
