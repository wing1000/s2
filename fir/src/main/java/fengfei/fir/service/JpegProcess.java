package fengfei.fir.service;

import java.io.IOException;

public interface JpegProcess {

    /**
     * 对图片进行缩放
     * 
     * @param srcImgFileName
     * @throws IOException
     */
    void zoomOut(String srcImgFileName, String destImgFileName, int... maxWidthOrHeight)
        throws Exception;

    /**
     * 以正方形剪切图片中间部分，并缩小为maxWidthOrHeight
     * 
     * @param srcImgFileName
     * @param maxWidthOrHeight
     * @throws IOException
     */
    void cropAndResize(String srcImgFileName, String destImgFileName, int maxWidthOrHeight)
        throws Exception;

    void cropAndResize(String srcImgFileName, String destImgFileName, int maxWidth, int maxHeight)
        throws Exception;

    public void adaptCropAndResize(
        String srcImgFileName,
        String destImgFileName,
        int maxWidth,
        int maxHeight) throws Exception;
}