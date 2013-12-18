package fengfei.fir.service.impl;

import fengfei.fir.service.JpegProcess;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.event.IIOWriteProgressListener;
import javax.imageio.stream.FileImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

public class DefaultJpegProcess implements JpegProcess {

    static Logger logger = LoggerFactory.getLogger(DefaultJpegProcess.class);
    public int quality = 100;
    public int imageType = BufferedImage.TYPE_INT_RGB;

    public void setQuality(int quality) {
        this.quality = quality;
    }

    /*
     * (non-Javadoc)
     * 
     * @see fengfei.spruce.utils.JpegProcess#zoomOut(java.lang.String, int)
     */
    @Override
    public void zoomOut(String srcImgFileName, String destImgFileName, int... maxWidthOrHeight)
            throws Exception {

        // 读入文件
        File _file = new File(srcImgFileName);
        // 构造Image对象

        BufferedImage src = javax.imageio.ImageIO.read(_file);
        int width = src.getWidth();
        int height = src.getHeight();

        for (int mwoh : maxWidthOrHeight) {
            if (width <= mwoh && height <= mwoh) {
                FileUtils.copyFile(new File(srcImgFileName), new File(destImgFileName));
            } else {
                float percentage = 1;
                if (width >= height) {
                    percentage = Float.valueOf(mwoh) / Float.valueOf(width);
                } else {
                    percentage = Float.valueOf(mwoh) / Float.valueOf(height);
                }

                int newWidth = Float.valueOf(width * percentage).intValue();
                int newHeight = Float.valueOf(height * percentage).intValue();

                logger
                        .debug(String
                                .format(
                                        "zoomImage %s, maxWidthOrHeight =%d src_width=%d src_height=%d new_width=%d new_height=%d rate=%f\n",
                                        srcImgFileName,
                                        mwoh,
                                        width,
                                        height,
                                        newWidth,
                                        newHeight,
                                        percentage));
                // 边长缩小
                BufferedImage destImg = new BufferedImage(
                        newWidth,
                        newHeight,
                        imageType);
                // 绘制缩小后的图
                destImg.getGraphics().drawImage(src, 0, 0, newWidth, newHeight, null);
                File f = new File(destImgFileName);
//                ImageIO.write(destImg, "JPEG", f);
                writeJPEG(f, destImg, quality, null);
                destImg = null;
            }
            src = null;

            // FileOutputStream out = new FileOutputStream(newFileName);
            // JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
            // encoder.encode(tag);
            // out.close();
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see fengfei.spruce.utils.JpegProcess#cutAndZoomOut(java.lang.String, int)
     */
    @Override
    public void cropAndResize(String srcImgFileName, String destImgFileName, int maxWidthOrHeight)
            throws Exception {

        // 读入文件
        File _file = new File(srcImgFileName);
        // 构造Image对象

        Image img;
        ImageFilter cropFilter;

        // 读取源图像
        BufferedImage src = ImageIO.read(_file);
        int destWidth = src.getWidth();
        int destHeight = src.getHeight();

        int reference = destHeight;

        if (destWidth >= destHeight) {
            reference = destHeight;
        } else {
            reference = destWidth;
        }

        int x = (destWidth - reference) / 2;
        int y = (destHeight - reference) / 2;
        int w = reference;
        int h = w;

        logger
                .debug(String
                        .format(
                                "cropAndResize %s square_dimension=%d reference=%d src_width=%d src_height=%d new_width=%d new_height=%d  x=%d, y=%d\n",
                                srcImgFileName,
                                maxWidthOrHeight,
                                reference,
                                destWidth,
                                destHeight,
                                w,
                                h,
                                x,
                                y));

        cropFilter = new CropImageFilter(x, y, w, h);
        img = Toolkit.getDefaultToolkit().createImage(
                new FilteredImageSource(src.getSource(), cropFilter));

        BufferedImage destImg = new BufferedImage(
                maxWidthOrHeight,
                maxWidthOrHeight,
                imageType);
//            BufferedImage.TYPE_INT_RGB);
        Graphics g = destImg.getGraphics();

        g.drawImage(img, 0, 0, maxWidthOrHeight, maxWidthOrHeight, null);
        g.dispose();

        File f = new File(destImgFileName);
//        ImageIO.write(destImg, "JPEG", f);
        writeJPEG(f, destImg, quality, null);
        src = null;
        destImg = null;
    }

    public void cropAndResize(
            String srcImgFileName,
            String destImgFileName,
            int maxWidth,
            int maxHeight) throws Exception {

        // 读入文件
        File _file = new File(srcImgFileName);
        // 构造Image对象

        // 读取源图像
        BufferedImage src = ImageIO.read(_file);
        int width = src.getWidth();
        int height = src.getHeight();

        float percentage = 1;
        float percentage1 = Float.valueOf(maxWidth) / Float.valueOf(width);
        float percentage2 = Float.valueOf(maxHeight) / Float.valueOf(height);
        percentage = Math.max(percentage1, percentage2);

        int w = Double.valueOf(StrictMath.round(width * percentage)).intValue();
        int h = Double.valueOf(StrictMath.round(height * percentage)).intValue();
        //

        int x = (w - maxWidth) / 2;
        int y = (h - maxHeight) / 2;

        logger
                .debug(String
                        .format(
                                "cropAndResize %s out_width=%d out_height=%d src_width=%d src_height=%d new_width=%d new_height=%d  x=%d, y=%d\n",
                                srcImgFileName,
                                maxWidth,
                                maxHeight,
                                width,
                                height,
                                w,
                                h,
                                x,
                                y));

        Image image = src.getScaledInstance(w, h, Image.SCALE_DEFAULT);// 获取缩放后的图片大小
        ImageFilter cropFilter = new CropImageFilter(x, y, maxWidth, maxHeight);
        Image img = Toolkit.getDefaultToolkit().createImage(
                new FilteredImageSource(image.getSource(), cropFilter));

        BufferedImage destImg = new BufferedImage(maxWidth, maxHeight, imageType);

        Graphics g = destImg.getGraphics();

        g.drawImage(img, 0, 0, null); // 绘制截取后的图
        g.dispose();

        File f = new File(destImgFileName);
//        ImageIO.write(destImg, "JPEG", f);
        writeJPEG(f, destImg, quality, null);
        g = null;
        src = null;
        destImg = null;
    }

    public void adaptCropAndResize(
            String srcImgFileName,
            String destImgFileName,
            int maxWidth,
            int maxHeight) throws Exception {

        // 读入文件
        File _file = new File(srcImgFileName);
        // 构造Image对象

        BufferedImage src = javax.imageio.ImageIO.read(_file);
        int width = src.getWidth();
        int height = src.getHeight();

        int wdiffer = width - maxWidth;
        int hdiffer = height - maxHeight;
        float percentage = 1;
        float percentage1 = Float.valueOf(maxWidth) / Float.valueOf(width);
        float percentage2 = Float.valueOf(maxHeight) / Float.valueOf(height);
        percentage = Math.min(percentage1, percentage2);
        // if (wdiffer < 0 && hdiffer >= 0) {
        // percentage = Float.valueOf(maxHeight) / Float.valueOf(height);
        // } else if (wdiffer >= 0 && hdiffer < 0) {
        // percentage = Float.valueOf(maxWidth) / Float.valueOf(width);
        // } else if (wdiffer >= 0 && hdiffer >= 0) {
        // if (maxWidth < maxHeight) {
        // percentage = Float.valueOf(maxWidth) / Float.valueOf(width);
        // } else if (maxWidth == maxHeight) {
        // if (width > height) {
        // percentage = Float.valueOf(maxWidth) / Float.valueOf(width);
        // } else {
        // percentage = Float.valueOf(maxHeight) / Float.valueOf(height);
        // }
        // } else {
        // percentage = Float.valueOf(maxHeight) / Float.valueOf(height);
        // }
        //
        // } else if (wdiffer < 0 && hdiffer < 0) {
        // if (wdiffer >= hdiffer) {
        // percentage = Float.valueOf(maxWidth) / Float.valueOf(width);
        // } else {
        // percentage = Float.valueOf(maxHeight) / Float.valueOf(height);
        // }
        // }

        int newWidth = Double.valueOf(StrictMath.round(width * percentage)).intValue();
        int newHeight = Double.valueOf(StrictMath.round(height * percentage)).intValue();

        //

        int y = Math.abs(newHeight - maxHeight) / 2;
        int x = Math.abs(newWidth - maxWidth) / 2;
        // if ((newWidth > newHeight && maxWidth < maxHeight)
        // || (newWidth < newHeight && maxWidth > maxHeight)) {
        // y = Math.abs(newHeight - maxHeight) / 2;
        // x = Math.abs(newWidth - maxWidth) / 2;
        // }

        logger
                .debug(String
                        .format(
                                "adaptCropAndResize %s out_width=%d out_height=%d src_width=%d src_height=%d new_width=%d new_height=%d  x=%d, y=%d\n",
                                srcImgFileName,
                                maxWidth,
                                maxHeight,
                                width,
                                height,
                                newWidth,
                                newHeight,
                                x,
                                y));
        // CropImageFilter cropFilter = new CropImageFilter(x, y, maxWidth,
        // maxHeight);
        // Image img = Toolkit.getDefaultToolkit().createImage(
        // new FilteredImageSource(src.getSource(), cropFilter));
        // 边长缩小
        BufferedImage destImg = new BufferedImage(maxWidth, maxHeight, imageType);
        // 绘制缩小后的图
        destImg.getGraphics().drawImage(src, x, y, newWidth, newHeight, Color.BLACK, null);
        File f = new File(destImgFileName);

//        ImageIO.write(destImg, "JPEG", f);
        writeJPEG(f, destImg, quality, null);

        // FileOutputStream out = new FileOutputStream(newFileName);
        // JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
        // encoder.encode(tag);
        // out.close();
        src = null;
        destImg = null;
    }

    /**
     * 保存图像到 JPEG 文件
     *
     * @param file     保存的目标文件
     * @param image    保存的源图像
     * @param quality  保存的 JPEG 图像质量
     * @param listener 保存进度监听器
     */
    protected static void writeJPEG(File file, BufferedImage image, int quality, IIOWriteProgressListener listener) throws
            FileNotFoundException, IOException {
        Iterator it = ImageIO.getImageWritersBySuffix("jpg");
        if (it.hasNext()) {
            FileImageOutputStream fileImageOutputStream = new FileImageOutputStream(file);
            ImageWriter iw = (ImageWriter) it.next();
            ImageWriteParam iwp = iw.getDefaultWriteParam();
            iwp.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            iwp.setCompressionQuality((float) quality / 100f);
            iw.setOutput(fileImageOutputStream);
            if (listener != null) {

                iw.addIIOWriteProgressListener(listener);
            }
            iw.write(null, new IIOImage(image, null, null), iwp);
            iw.dispose();
            fileImageOutputStream.flush();
            fileImageOutputStream.close();
        }
    }

    public static void main(String[] args) throws Exception {

        DefaultJpegProcess iZoom = new DefaultJpegProcess();
        File files = new File("C:\\Users\\wtt\\Desktop\\3");
        File[] fs = files.listFiles();
        for (File f : fs) {
            if (f.isFile() && "jpg".equalsIgnoreCase(FilenameUtils.getExtension(f.getName()))) {
                // iZoom.zoomOut(jpegFiles, 800, 100);
                int maxWidth = 300;
                int maxHeight = 200;
                String srcImgFileName = f.getAbsolutePath();
                String newFileName = FilenameUtils.getFullPath(srcImgFileName) + "xx/"
                        + FilenameUtils.getBaseName(srcImgFileName) + "_" + maxWidth + "x"
                        + maxHeight + "." + FilenameUtils.getExtension(srcImgFileName);
                String destImgFileName = FilenameUtils.getFullPath(srcImgFileName) + "xx/"
                        + FilenameUtils.getBaseName(srcImgFileName) + "_" + maxHeight + "x"
                        + maxHeight + "." + FilenameUtils.getExtension(srcImgFileName);
                iZoom.zoomOut(srcImgFileName, destImgFileName, maxHeight);
                iZoom.cropAndResize(srcImgFileName, newFileName, maxWidth, maxHeight);

            }

        }
        long start = System.currentTimeMillis();

        System.out.println(System.currentTimeMillis() - start);

    }

}