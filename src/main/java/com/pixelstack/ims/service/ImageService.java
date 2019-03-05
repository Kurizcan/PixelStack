package com.pixelstack.ims.service;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pixelstack.ims.common.ImageHelper.ImgResizeUtil;
import com.pixelstack.ims.domain.Image;
import com.pixelstack.ims.mapper.ImageMapper;
import com.pixelstack.ims.mapper.StarMapper;
import com.pixelstack.ims.mapper.ThumbMapper;
import com.pixelstack.ims.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ImageService {

    private static String[] IMAGE_FILE_EXT = new String[] {"bmp", "jpg", "jpeg", "png", "gif"};

    //public  String IMAGE_DIR = "C:\\Users\\asus\\Desktop\\users\\";
    public  String IMAGE_DIR = "/home/wuyu/pixelstack_upload/";

    @Autowired
    ImageMapper imageMapper;

    @Autowired
    ImgResizeUtil imgResizeUtil;

    @Autowired
    ThumbMapper thumbMapper;

    @Autowired
    StarMapper starMapper;

    @Autowired
    UserMapper userMapper;

    public boolean isImageAllowed(String imageExt) {
        for (String ext : IMAGE_FILE_EXT) {
            if (ext.equals(imageExt)) {
                return true;
            }
        }
        return false;
    }

    @Transactional
    public Object upload(MultipartFile[] files, String username, String title) throws IOException {
        String originalDir, smallDir, bigDir;
        String originalUrl = null;
        ArrayList<Object> postList = new ArrayList<>();
        HashMap<String, Object> List = new HashMap<>();
        ArrayList<Object> errorList = new ArrayList<>();
        int i = 0;
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        try {
            for (MultipartFile file : files) {
                originalDir = storeImageToLocal(file, "original", username, ft.format(date));      // 进行规格处理后存储大图
                //originalUrl = originalDir + "\\" + file.getOriginalFilename();
                originalUrl = originalDir + "/" + file.getOriginalFilename();
                if (originalUrl == null) {
                    errorList.add(file.getOriginalFilename());
                }
                else {
                    // 制作缩略图
                    smallDir = originalDir.replace("original", "small");
                    imgResizeUtil.resize(200, 200, originalUrl, smallDir, file.getOriginalFilename());
                    // 存入图片
                    Image image = new Image();
                    image.setTitle(title);
                    image.setUrl(originalUrl);
                    image.setUpload(date);
                    image.setAuthor(username);
                    imageMapper.addImage(image);
                    postList.add(image);
                }
                List.put("postList", postList);
                List.put("errorList", errorList);
            }
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return List;
    }

    @Transactional
    public String storeImageToLocal(MultipartFile file, String Type, String username, String date) throws IOException {
        // TODO 将文件写入到指定目录（更好的做法：将文件写入到云存储/或者指定目录通过 Nginx 进行 gzip 压缩和反向代理）
        String imageExt = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1).toLowerCase();
        try {
            if (this.isImageAllowed(imageExt)) {
                //String dirPath = this.IMAGE_DIR + username + "\\" + date + "\\" + Type;
                String dirPath = this.IMAGE_DIR + username + "/" + date + "/" + Type;
                File dir = new File(dirPath);
                if (!dir.exists())
                    dir.mkdirs();
                //String path = dirPath + "\\" + file.getOriginalFilename();
                String path = dirPath + "/" + file.getOriginalFilename();
                file.transferTo(new File(path));
                return dirPath;         // 返回的是文件夹的路径
            }
            else {
                return null;
            }
        } catch (IOException e) {
                throw new RuntimeException("写入文件失败");
        }
    }

    public boolean deleteImageByiid(int iid) {
        if (imageMapper.deleteImage(iid) == 0)
            return false;
        else
            return true;
    }

    public boolean addTitle(int pids, String titles) {
        if (imageMapper.addTiltle(titles, pids) == 0)
            return false;
        else
            return true;
    }

    public Object getImageDetailByiid(int iid, int uid) {
        HashMap<String, Object> details = new HashMap<>();
        Image image = imageMapper.getImageByiid(iid);
        boolean isStar = false;
        boolean isThumb = false;
        boolean isFollow = false;
        if (image == null)
            return null;
        else {
            details = this.makeupDeatils(image, iid);
            if (starMapper.checkStarByUid(iid, uid) > 0)
                isStar = true;
            if (thumbMapper.checkThumbByUid(iid, uid) > 0)
                isThumb = true;
            Integer fid = imageMapper.getUidbyImage(iid);
            if (fid != null && userMapper.checkFollowByFid(uid, fid) > 0)
                isFollow = true;
            details.put("isFollow", isFollow);
            details.put("isStar", isStar);
            details.put("isThumb", isThumb);
        }
        return details;
    }

    private HashMap<String, Object> makeupDeatils(Image image, int iid) {
        int star = 0;
        int thumb = 0;
        List tags = new ArrayList();
        HashMap<String, Object> details = new HashMap<>();
        if (image == null)
            return null;
        star = imageMapper.getImageStarCount(iid);
        thumb = imageMapper.getImageThumbCount(iid);
        tags = imageMapper.getImageAllTagsByiid(iid);
        details.put("title", image.getTitle());
        details.put("author", image.getAuthor());
        details.put("upload", this.getNormalDate(image.getUpload()));
        details.put("url", image.getUrl());
        details.put("count", image.getCount());
        details.put("tags", tags);
        details.put("star", star);
        details.put("thumb", thumb);
        return details;
    }

    public PageInfo<Map<String,Object>> getImageList(int pageNo, int pageSize) {
        PageHelper.startPage(pageNo,pageSize);
        PageInfo<Map<String,Object>> pageInfo = new PageInfo<>(imageMapper.getImageList());  // 根据用户 id 查询
        List<Map<String,Object>> list = pageInfo.getList();
        Iterator iterator = list.iterator();
        while (iterator.hasNext()) {
            Map<String,Object> imageMap = (Map<String,Object>) iterator.next();
            this.makeUp(imageMap);
        }
        return pageInfo;
    }

    private String getNormalDate(Date date) {
        return date.toString().replace(date.toString().substring(11, 24), "");
    }

    private void makeUp(Map<String,Object> imageMap) {
        int star = starMapper.getStarByiid((Integer) imageMap.get("iid"));
        int thumb = thumbMapper.getThumbByiid((Integer) imageMap.get("iid"));
        String smallUrl = imageMap.get("url").toString().replace("original", "small");
        imageMap.put("url", smallUrl);
        imageMap.put("star", star);
        imageMap.put("thumb", thumb);
    }

    public List<Map<String,Object>> getImageListByUid(int uid) {
        List<Map<String,Object>> mapList = (List<Map<String,Object>>) imageMapper.getImageListByUid(uid);
        Iterator iterator = mapList.iterator();
        while (iterator.hasNext()) {
            Map<String,Object> imageMap = (Map<String,Object>) iterator.next();
            this.makeUp(imageMap);
        }
        return mapList;
    }

    public List<Map<String, Object>> getMyStars(int uid) {
        List<Integer> stats = starMapper.getStars(uid);
        if (stats.size() == 0)
            return null;
        System.out.println(stats.toString());
        List<Map<String, Object>> imgs = imageMapper.selectMyStars(stats);
        Iterator iterator = imgs.iterator();
        while (iterator.hasNext()) {
            Map<String, Object> img = (Map<String, Object>) iterator.next();
            this.makeUp(img);
        }
        return imgs;
    }

    public List<Map<String, Object>> getListByTagName(String tagName) {
        List<Map<String, Object>> myTagImages = imageMapper.getListByTagName(tagName);
        if (myTagImages.size() == 0)
            return null;
        Iterator iterator = myTagImages.iterator();
        while (iterator.hasNext()) {
            Map<String, Object> img = (Map<String, Object>) iterator.next();
            this.makeUp(img);
        }
        return myTagImages;
    }
}
