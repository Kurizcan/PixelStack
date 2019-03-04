package com.pixelstack.ims.service;

import com.pixelstack.ims.domain.Tag;
import com.pixelstack.ims.mapper.TagMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class TagService {

    @Autowired
    TagMapper tagMapper;


    public boolean addTagsRelate(int iid, int tid) {
        if (tagMapper.addTagsRelate(iid, tid) == 0)
            return false;
        else
            return true;
    }

    public Tag selectTagByName(String name) {
        return tagMapper.selectTagByName(name);
    }


}
