package com.pixelstack.ims.service;

import com.pixelstack.ims.common.Auth.Authentication;
import com.pixelstack.ims.common.exception.InternalErrorException;
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

    @Autowired
    Authentication authentication;

    public int register(User user) throws InternalErrorException {
        user.setAuthority("user");
        user.setStatus("normal");
        return userMapper.addUser(user);
    }

    /**
     * 用户登录
     */
    public User login(User user) {
        return userMapper.checkUser(user);
    }

    /**
     * 验证用户
     * @param token
     * @return
     */
    public User vaild(String token) {
        int uid;
        User user = null;
        if (authentication.vaildToken(token)) {
            // 验证成功，返回用户信息
            uid = Integer.parseInt(authentication.getUidByToken(token));
            user = userMapper.selectUserById(uid);
        }
        return user;
    }

}
