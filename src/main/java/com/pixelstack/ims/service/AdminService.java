package com.pixelstack.ims.service;


import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.pixelstack.ims.common.Auth.Authentication;
import com.pixelstack.ims.domain.User;
import com.pixelstack.ims.mapper.AdminMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService extends UserService {

    @Autowired
    AdminMapper adminMapper;

    @Autowired
    Authentication authentication;

    /**
     * 获取用户信息列表
     * @return
     */
    public Page<User> getUserList() {
        return adminMapper.getUserList();
    }

    /**
     * 获取管理员用户信息列表
     * @return
     */
    public Page<User> getAdminList() {
        return adminMapper.getAdminList();
    }

    /**
     * 修改用户状态：normal/frozen/terminate
     * @param jsonObject
     * @return
     */

    public boolean updateState(JSONObject jsonObject) {
        if (adminMapper.updateStateById(jsonObject) == 0)
            return false;
        else
            return true;
    }

    /**
     * 只有 root 权限的管理员可以创建管理员
     * @param user
     * @param authority
     * @return
     * @throws InterruptedException
     */

    public int createCount(User user, String authority) throws InterruptedException {
        if (authority.equals("root"))
            return this.register(user, "admin");
        else
            return 0;
    }
}