package com.pixelstack.ims.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Image {
    private int iid;
    private String title;
    private String author;
    private Date upload;
    private String url;
    private int count;
}
