package com.pixelstack.ims.service;

import com.pixelstack.ims.domain.User;
import com.pixelstack.ims.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    /**
     * 用户注册
     */
    @Autowired
    UserMapper userMapper;

    public int register(User user) {
        user.setAuthority("user");
        user.setStatus("normal");
        return userMapper.addUser(user);
    }

    /**
     * 用户登录
     */
    public boolean login(String username, String password) {
        User user = userMapper.checkUser(username, password);
        if (user == null)
            return false;
        else
            return true;
    }

}
