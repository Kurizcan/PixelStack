package com.pixelstack.ims.domain;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;

@Setter
public class User {
    private int uid;            // 使用 mybatis，此处如果是 Integer 类型初始化为 null，如果为 int，初始化为 0
    private String username;
    private String password;
    private String authority;
    private String email;
    private String status;
    private String introduction;

    public interface UserSimpleView {};

    public interface UserDetailView extends UserSimpleView {};

    @JsonView(UserSimpleView.class)
    public String getUsername() {
        return username;
    }

    @JsonView(UserSimpleView.class)
    public int getUid() {
        return uid;
    }

    @JsonView(UserSimpleView.class)
    public String getAuthority() {
        return authority;
    }

    @JsonView(UserSimpleView.class)
    public String getEmail() {
        return email;
    }

    @JsonView(UserSimpleView.class)
    public String getStatus() {
        return status;
    }

    @JsonView(UserDetailView.class)
    public String getIntroduction() {
        return introduction;
    }

    @JsonView(UserDetailView.class)
    public String getPassword() {
        return password;
    }
}
