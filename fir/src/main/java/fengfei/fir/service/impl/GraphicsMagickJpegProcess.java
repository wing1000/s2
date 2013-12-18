package fengfei.fir.service.impl;

import fengfei.fir.service.JpegProcess;
import org.apache.commons.io.FilenameUtils;
import org.im4java.core.ConvertCmd;
import org.im4java.core.IM4JavaException;
import org.im4java.core.IMOperation;
import org.im4java.process.Pipe;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class GraphicsMagickJpegProcess implements JpegProcess {

    private int quality = 75;
    // create command
    // GraphicsMagickCmd cmd = new GraphicsMagickCmd("convert");.
    static ConvertCmd cmd = new ConvertCmd(true);

    public GraphicsMagickJpegProcess() {
        cmd.setAsyncMode(true);
    }

    public int getQuality() {
        return quality;
    }

    public void setQuality(int quality) {
        this.quality = quality;
    }

    @Override
    public void zoomOut(String srcImgFileName, String destImgFileName, int... maxWidthOrHeight)
        throws IOException,
        InterruptedException,
        IM4JavaException {

        // create the operation, add images and operators/options

        for (int mwoh : maxWidthOrHeight) {

            IMOperation op = new IMOperation();
            op.addImage(srcImgFileName);
            op.resize(mwoh);
            op.addImage(destImgFileName);

            cmd.run(op);
        }

    }

    public void zoomOut2(InputStream in, OutputStream out, int... maxWidthOrHeight)
        throws IOException,
        InterruptedException,
        IM4JavaException {
        Pipe pipeIn = new Pipe(in, null);
        Pipe pipeOut = new Pipe(null, out);

        // create the operation, add images and operators/options

        for (int mwoh : maxWidthOrHeight) {

            IMOperation op = new IMOperation();
            op.addImage("-");
            op.resize(mwoh);
            op.addImage("-");

            cmd.setInputProvider(pipeIn);
            cmd.setOutputConsumer(pipeOut);
            cmd.run(op);
            in.close();
            out.close();
        }

    }

    /**
     * <pre>
     * gm convert IMG_33382.jpg -thumbnail "300x300^" -gravity center -extent 300x300 output.jpg
     * gm convert IMG_33382.jpg -sample "300x300^" -gravity center -extent 300x300  -quality 50 output.jpg
     * </pre>
     */
    @Override
    public void cropAndResize(String srcImgFileName, String destImgFileName, int maxWidthOrHeight)
        throws Exception {
        cropAndResize(srcImgFileName, destImgFileName, maxWidthOrHeight, maxWidthOrHeight);
    }

    @Override
    public void cropAndResize(
        String srcImgFileName,
        String destImgFileName,
        int maxWidth,
        int maxHeight) throws Exception {

        String wh = maxWidth + "x" + maxHeight;
        IMOperation op = new IMOperation();
        op.addImage(srcImgFileName);
        op.addRawArgs("-sample", "\"" + wh + "^\"");
        op.addRawArgs("-gravity", "center");
        op.addRawArgs("-extent", wh);
        op.addRawArgs("-quality", String.valueOf(quality));
        // +profile icm, +profile ipt
        // op.addRawArgs("+profile", "icm");
        // op.addRawArgs("+profile", "iptc");
        op.addRawArgs("+profile", "!Exif,*");// 移出除exif之外的所有profile，以减小图片大小
        op.addImage(destImgFileName);
        cmd.run(op);

    }

    /**
     * <pre>
     * gm convert input.jpg -thumbnail '100x100' -background gray -gravity center -extent 100x100 output_4.jpg
     * </pre>
     * 
     * @param jpegFiles
     * @param maxWidth
     * @param maxHeight
     * @throws Exception
     */

    public void adaptCropAndResize(
        String srcImgFileName,
        String destImgFileName,
        int maxWidth,
        int maxHeight) throws Exception {

        String wh = maxWidth + "x" + maxHeight;
        IMOperation op = new IMOperation();
        op.addImage(srcImgFileName);

        op.addRawArgs("-sample", "\"" + wh + "\"");
        op.addRawArgs("-background", "black");
        op.addRawArgs("-gravity", "center");
        op.addRawArgs("-extent", wh);
        op.addRawArgs("-quality", String.valueOf(quality));
        // +profile icm, +profile ipt
        // op.addRawArgs("+profile", "icm");
        // op.addRawArgs("+profile", "iptc");
        op.addRawArgs("+profile", "!Exif,*");
        op.addImage(destImgFileName);
        cmd.run(op);

    }

    /**
     * <pre>
     * gm convert input.jpg -thumbnail '10000@' -background gray -gravity center -extent 100x100 output_5.jpg
     * </pre>
     * 
     * @param jpegFiles
     * @param maxWidth
     * @param maxHeight
     * @throws Exception
     */
    public void partialAdaptCropAndResize(
        String srcImgFileName,
        String destImgFileName,
        int maxWidth,
        int maxHeight) throws Exception {

        int wh = maxWidth * maxHeight;
        String swh = maxWidth + "x" + maxHeight;
        IMOperation op = new IMOperation();
        op.addImage(srcImgFileName);

        op.addRawArgs("-sample", "\"" + wh + "@\"");
        op.addRawArgs("-background", "black");
        op.addRawArgs("-gravity", "center");
        op.addRawArgs("-extent", swh);
        op.addRawArgs("-quality", String.valueOf(quality));
        // +profile icm, +profile ipt
        // op.addRawArgs("+profile", "icm");
        // op.addRawArgs("+profile", "iptc");
        op.addRawArgs("+profile", "!Exif,*");
        op.addImage(destImgFileName);
        cmd.run(op);

    }

    public static void main(String[] args) throws Exception {

        GraphicsMagickJpegProcess iZoom = new GraphicsMagickJpegProcess();
        File files = new File("C:\\Users\\tietang\\Desktop\\3");
        File[] fs = files.listFiles();

        for (File f : fs) {
            if (f.isFile() && "jpg".equalsIgnoreCase(FilenameUtils.getExtension(f.getName()))) {
                int width = 4000;
                int height = 1000;
                String srcImgFileName = f.getAbsolutePath();
                String newFileName = FilenameUtils.getFullPath(srcImgFileName) + "xx/"
                        + FilenameUtils.getBaseName(srcImgFileName) + "_" + width + "x" + height
                        + "." + FilenameUtils.getExtension(srcImgFileName);
                // iZoom.zoomOut(jpegFiles, 800, 100);
                iZoom.cropAndResize(f.getAbsolutePath(), newFileName, width, height);
                // iZoom.cutAndZoomOut2(jpegFiles, 400, 300);
                // iZoom.cutAndZoomOut3(jpegFiles, 500, 400);

            }

        }

    }

}
