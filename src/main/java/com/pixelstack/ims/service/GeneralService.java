package com.pixelstack.ims.service;

import com.pixelstack.ims.domain.Tag;
import com.pixelstack.ims.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 普通用户功能
 */

@Service
public class GeneralService extends UserService {

    @Autowired
    TagMapper tagMapper;

    @Autowired
    StarMapper starMapper;

    @Autowired
    ThumbMapper thumbMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    CommentMapper commentMapper;

    @Transactional
    public boolean addTags(List tags, List pids) {
        // 判断表中是否存在 tag 重复
        try {

            Iterator tagIterator = tags.iterator();
            List<Tag> tagList = new ArrayList<>();
            while (tagIterator.hasNext()) {
                String tagName = (String) tagIterator.next();
                Tag tag = tagMapper.selectTagByName(tagName);
                if (tag == null) {
                    tag = new Tag();
                    tag.setTagname(tagName);
                    if (tagMapper.addTagByName(tag) > 0)
                        tagList.add(tag);
                }
                else
                    tagList.add(tag);
            }

            for (int i = 0; i < tagList.size(); i++) {
                Iterator pidIterator = pids.iterator();
                while (pidIterator.hasNext()) {
                    int iid = (int) pidIterator.next();
                    tagMapper.addTagsRelate(iid, tagList.get(i).getTid());
                    System.out.println(tagList.get(i).getTid() + ": " + tagList.get(i).getTagname());
                }
            }
            return true;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("添加标签事务出错，已回滚");
        }
    }

    public boolean IsStar(int iid, int uid, boolean isStar) {
        try {
            int status;
            if (isStar)
                status = starMapper.addStarByUid(iid, uid);
            else
                status = starMapper.deleteStarByUid(iid, uid);
            if (status == 0)
                return false;
            else
                return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("收藏或取消收藏操作失败");
        }
    }

    public boolean IsThumb(int iid, int uid, boolean isThumb) {
        try {
            int status;
            if (isThumb)
                status = thumbMapper.addThumbByUid(iid, uid);
            else
                status = thumbMapper.deleteThumbByUid(iid, uid);
            if (status == 0)
                return false;
            else
                return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("点赞或取消点赞操作失败");
        }
    }

    public boolean followOther(int uid, int fid, boolean isFollow) {
        try {
            int status;
            if (isFollow)
                status = userMapper.follow(uid, fid);
            else
                status = userMapper.unfollow(uid, fid);
            if (status == 0)
                return false;
            else
                return true;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("follow 或 unfollow 操作失败");
        }

    }

    public List<Map<String, Object>> getFollowers(int uid) {
        List<Map<String, Object>> followList = userMapper.getFollowers(uid);
        if (followList == null)
            return null;
        else {
            makeUp(followList);
        }
        return followList;
    }

    public List<Map<String, Object>> getFans(int uid) {
        List<Map<String, Object>> fansList = userMapper.getFans(uid);
        if (fansList == null)
            return null;
        else {
            makeUp(fansList);
        }
        return fansList;
    }

    private void makeUp(List<Map<String, Object>> lists) {
        Iterator iterator = lists.iterator();
        while (iterator.hasNext()) {
            Map<String, Object> follower = (Map<String, Object>) iterator.next();
            if (follower.get("introduction") == null)
                follower.put("introduction", null);
        }
    }

    public boolean Isfollow(int uid, int fid) {
        if (userMapper.checkFollowByFid(uid, fid) == 0)
            return false;
        else
            return true;
    }


}
