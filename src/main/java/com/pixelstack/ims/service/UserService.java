package com.pixelstack.ims.service;

import com.pixelstack.ims.common.Auth.Authentication;
import com.pixelstack.ims.common.exception.InternalErrorException;
import com.pixelstack.ims.domain.User;
import com.pixelstack.ims.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    Authentication authentication;

    /**
     * 用户注册
     * @param user
     * @return
     * @throws InternalErrorException
     */
    public int register(User user, String authority){
        user.setAuthority(authority);
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

    /**
     * 修改用户信息， 可以修改用户姓名、密码、邮箱信息
     * @param user
     * @return
     * 1 更新成功
     * 2 更改了密码
     * 0 更新失败
     */
    public int modify(User user) {
        User old = userMapper.selectUserById(user.getUid());
        int status = userMapper.updateUserById(user);
        // 一旦用户修改了密码，需要重新设置它的 token
        if (!old.getPassword().equals(user.getPassword())) {
            authentication.deleteToken(old);
            status = 2;         // 用户密码被修改
        }
        return status;
    }

    public HashMap getUserInfo(int uid) {
        HashMap<String, Object> hashMap = new HashMap();
        User user = userMapper.selectUserById(uid);
        if (user == null)
            return null;
        else {
            int followCount = userMapper.getFollowCount(uid);
            int starCount = userMapper.getStarCount(uid);
            int likeCount = userMapper.getThumbCount(uid);
            int fansCount = userMapper.getFansCount(uid);
            hashMap.put("username", user.getUsername());
            hashMap.put("email", user.getEmail());
            hashMap.put("introduction", user.getIntroduction());
            hashMap.put("authority", user.getAuthority());
            hashMap.put("fans", fansCount);
            hashMap.put("follow",followCount);
            hashMap.put("star", starCount);
            hashMap.put("like", likeCount);

            return hashMap;
        }
    }


}
