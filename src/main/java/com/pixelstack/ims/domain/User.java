package com.pixelstack.ims.domain;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class User {
    private int uid;
    private String username;
    private String password;
    private String authority;
    private String email;
    private String status;
}
