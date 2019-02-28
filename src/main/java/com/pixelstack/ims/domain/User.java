package com.pixelstack.ims.domain;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class User {
    private int uid;            // 使用 mybatis，此处如果是 Integer 类型初始化为 null，如果为 int，初始化为 0
    private String username;
    private String password;
    private String authority;
    private String email;
    private String status;
}
