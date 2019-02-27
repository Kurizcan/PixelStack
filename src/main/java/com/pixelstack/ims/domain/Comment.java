package com.pixelstack.ims.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class Comment {
    private int cid;
    private int uid;
    private String content;
    private Date cdate;
}
