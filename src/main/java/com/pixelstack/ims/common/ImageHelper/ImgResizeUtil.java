package com.pixelstack.ims.common.ImageHelper;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import org.springframework.stereotype.Component;

@Component
public class ImgResizeUtil {

    private Image img;
    private int width;
    private int height;

    /**
     * 构造函数
     */
    public void getImage(String fileName) throws IOException {
        File file = new File(fileName);// 读入已保存的文件
        img = ImageIO.read(file); // 构造Image对象
        width = img.getWidth(null); // 得到源图宽
        height = img.getHeight(null); // 得到源图长
    }

    /**
     * 按照宽度还是高度进行压缩
     *
     * @param w
     *            int 最大宽度
     * @param h
     *            int 最大高度
     */
    public void resizeFix(int w, int h, String filename, String filePath, String name) throws IOException {
        if (width > w || height > h) {
            if (width / height > w / h) {
                resizeByWidth(w, filename, filePath, name);
            } else {
                resizeByHeight(h, filename, filePath, name);
            }
        }
    }

    /**
     * 以宽度为基准，等比例放缩图片
     *
     * @param w
     *            int 新宽度
     */
    public void resizeByWidth(int w, String filename, String filePath, String name) throws IOException {
        if (width > w) {// 获取到的图片的宽大于指定缩放大小的宽才进行缩放
            int h = (int) (height * w / width);
            resize(w, h, filename, filePath, name);
        }
    }

    /**
     * 以高度为基准，等比例缩放图片
     *
     * @param h
     *            int 新高度
     */
    public void resizeByHeight(int h, String filename, String filePath, String name) throws IOException {
        if (height > h) {// 获取到的图片的高大于指定缩放大小的高才进行缩放
            int w = (int) (width * h / height);
            resize(w, h, filename, filePath, name);
        }
    }

    /**
     * 按照固定大小等比例缩放
     * @param w         新的宽度
     * @param h         新的高度
     * @param filename  源照片路径
     * @param destDir   新照片存储文件夹
     * @param name      修改文件的名字
     * @throws IOException
     */
    public void resize(int w, int h, String filename, String destDir, String name) throws IOException {
        // SCALE_SMOOTH 的缩略算法 生成缩略图片的平滑度的 优先级比速度高 生成的图片质量比较好 但速度慢
        this.getImage(filename);
        BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        image.getGraphics().drawImage(img, 0, 0, w, h, null); // 绘制缩小后的图
        // 生成目录
        File destPath = new File(destDir);
        if (!destPath.exists())
            destPath.mkdirs();
        // 新地址
        //File newImg = new File(destDir + "\\" + name);
        File newImg = new File(destDir + "/" + name);
        FileOutputStream out = new FileOutputStream(newImg); // 输出到文件流
        // 可以正常实现bmp、png、gif 转 jpg
        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
        encoder.encode(image); // JPEG 编码
        out.close();
    }

}
