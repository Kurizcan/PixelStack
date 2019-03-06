package com.pixelstack.ims.service;


import com.alibaba.fastjson.JSONObject;
import com.pixelstack.ims.common.Auth.Authentication;
import com.pixelstack.ims.domain.User;
import com.pixelstack.ims.mapper.AdminMapper;
import com.pixelstack.ims.mapper.CommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service
public class AdminService extends UserService {

    @Autowired
    AdminMapper adminMapper;

    @Autowired
    Authentication authentication;

    @Autowired
    CommentMapper commentMapper;

    /**
     * 获取用户信息列表
     * @return
     */
    public List<User> getUserList() {
        //return adminMapper.getUserList(); 分页 Page<User>
        return adminMapper.getAllUser();
    }

    /**
     * 获取管理员用户信息列表
     * @return
     */
    public List<User> getAdminList() {
        //return adminMapper.getAdminList();
        return adminMapper.getAllAdmin();
    }

    /**
     * 修改用户状态：normal/frozen/terminate
     * @param jsonObject
     * @return
     */

    @Transactional
    public boolean updateState(JSONObject jsonObject) {
       /*
        if (adminMapper.updateStateById(jsonObject) == 0)
            return false;
        else
            return true;
        */
       try {
           adminMapper.updateStateById(jsonObject);        // 更新状态
           List<String> uids = new ArrayList();
           Map<String,Object> map = (Map<String,Object>)jsonObject;
           Iterator<Map.Entry<String, Object>> entries = map.entrySet().iterator();
           while (entries.hasNext()) {
               Map.Entry<String, Object> entry = entries.next();
               if (!entry.getValue().equals("normal"))
                   uids.add(entry.getKey());               // 将要进行去除 token 的操作
               System.out.println(entry.getKey());
           }

           if (!uids.isEmpty()) {
               //System.out.println(uids.size());
               //判断 token 是否存在
               List<Map<String, Object>> errorUsers = userMapper.getUsersByuids(uids);
               System.out.println(errorUsers.size());

               Iterator errorList = errorUsers.iterator();

               while (errorList.hasNext()) {
                   Map<String, Object> errorUser = (Map<String, Object>) errorList.next();
                   //System.out.println(errorUser.get("uid") + ": " + errorUser.get("password"));
                   User user = new User();
                   user.setUid(Integer.parseInt(errorUser.get("uid").toString()));
                   user.setPassword(errorUser.get("password").toString());
                   System.out.println(user.getUid() + ":" + user.getPassword());
                   authentication.deleteToken(user);
               }

           }


       } catch (Exception e) {
           throw new RuntimeException("更新状态出错");
       }
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

    /**
     * 对被举报的用户评论进行审查
     * @param cid
     * @param reportRight
     * @return
     */
    public boolean dealWithReport(int cid, boolean reportRight) {
        int status;
        if (reportRight)
            status = commentMapper.deleteComment(cid);
        else
            status = commentMapper.updateReport(reportRight, cid);
        if (status == 0)
            return false;
        else
            return true;
    }
}