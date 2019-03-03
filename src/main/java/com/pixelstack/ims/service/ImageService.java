package com.pixelstack.ims.service;

import com.pixelstack.ims.common.ImageHelper.ImgResizeUtil;
import com.pixelstack.ims.domain.Image;
import com.pixelstack.ims.mapper.ImageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

@Service
public class ImageService {

    private static String[] IMAGE_FILE_EXT = new String[] {"bmp", "jpg", "jpeg", "png", "gif"};

    //public  String IMAGE_DIR = "C:\\Users\\asus\\Desktop\\users\\";
    public  String IMAGE_DIR = "/home/wuyu/pixelstack_upload/";

    @Autowired
    ImageMapper imageMapper;

    @Autowired
    ImgResizeUtil imgResizeUtil;

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

    public String formatDate(Date date, int index, int end) {
        StringBuffer date_buffer = new StringBuffer(date.toString());
        return date_buffer.delete(index, end).toString();// 11, 24
    }


}
