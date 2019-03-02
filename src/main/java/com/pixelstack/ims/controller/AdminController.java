package com.pixelstack.ims.controller;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonView;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pixelstack.ims.common.Auth.UserLoginToken;
import com.pixelstack.ims.domain.User;
import com.pixelstack.ims.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="/admin")
public class AdminController {

    JSONObject result = new JSONObject();

    @Autowired
    AdminService adminService;

    @JsonView(User.UserSimpleView.class)
    @UserLoginToken
    @GetMapping(value = {"/getUserList"})
    public Object getUserList(@RequestParam(defaultValue = "1") int pageNo, @RequestParam(defaultValue = "10") int pageSize, int type) {
        PageHelper.startPage(pageNo,pageSize);
        PageInfo<User> pageInfo = null;
        if (type == 0)
            pageInfo = new PageInfo<>(adminService.getUserList());   // 查询用户
        else if (type == 1)
            pageInfo = new PageInfo<>(adminService.getAdminList());  // 查询管理员

        result.clear();
        result.put("userList", pageInfo.getList());
        result.put("total", pageInfo.getTotal());
        result.put("curPage", pageInfo.getPageNum());
        result.put("prePage", pageInfo.getPrePage());
        result.put("nextPage", pageInfo.getNextPage());
        result.put("lastPage", pageInfo.getNavigateLastPage());
        return result;
    }

    @UserLoginToken
    @PostMapping(value = {"/manageCountStatus"})
    public Object manageCountStatus(@RequestBody JSONObject jsonObject) {
        result.clear();
        if (adminService.updateState(jsonObject)) {
            result.put("status", 200);
            result.put("message", "修改状态成功");
        }
        else {
            result.put("status", 500);
            result.put("message", "修改状态失败");
        }
        return result;
    }

    @UserLoginToken
    @PostMapping(value = {"/createCount"})  // 由前端传回权限值是不安全的
    public Object createCount(User user, String authority) throws  InterruptedException{
        int status = adminService.createCount(user, authority);
        result.clear();
        if (status == 0) {
            result.put("status", 500);
            result.put("message", "权限不足，创建失败");
        }
        else {
            result.put("status", 200);
            result.put("message", "创建成功");
        }
        return result;
    }

}