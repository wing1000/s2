package fengfei.spruce.utils; /**
 */

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;

public class CreateImageUtils {

    static int width = 200;
    static int height = 200;
    static int rectWidth = 40;


    static Random random = new Random();

    public static boolean[][] random55() {
        boolean[][] r = new boolean[2][10];
        for (int i = 0; i < r[0].length; i++) {
            r[0][i] = random.nextBoolean();
        }
        for (int i = 0; i < r[1].length; i++) {
            r[1][i] = random.nextBoolean();
        }

        return r;
    }

    private static void fillLeftRightSymmetry(Graphics graphics, boolean isRect) {
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 5; j++) {
                if (random.nextBoolean()) {
                    if (isRect) {
                        graphics.fillRect(i * rectWidth, j * rectWidth, rectWidth, rectWidth);
                        graphics.fillRect(width - (i + 1) * rectWidth, j * rectWidth, rectWidth, rectWidth);
                    } else {
                        graphics.fillArc(i * rectWidth, j * rectWidth, rectWidth, rectWidth, 0, 360);
                        graphics.fillArc(width - (i + 1) * rectWidth, j * rectWidth, rectWidth, rectWidth, 0, 360);
                    }
                }
            }
        }
        for (int i = 0; i < 5; i++) {
            if (random.nextBoolean()) {
                graphics.fillRect(2 * rectWidth, i * rectWidth, rectWidth, rectWidth);
            }
        }
    }

    private static void fillUpDownSymmetry(Graphics graphics, boolean isRect) {
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 5; j++) {
                if (random.nextBoolean()) {
                    if (isRect) {
                        graphics.fillRect(i * rectWidth, j * rectWidth, rectWidth, rectWidth);
                        graphics.fillRect(i * rectWidth, height - (j + 1) * rectWidth, rectWidth, rectWidth);
                    } else {
                        graphics.fillArc(i * rectWidth, j * rectWidth, rectWidth, rectWidth, 0, 360);
                        graphics.fillArc(i * rectWidth, height - (j + 1) * rectWidth, rectWidth, rectWidth, 0, 360);
                    }
                }
            }
        }
        for (int i = 0; i < 5; i++) {
            if (random.nextBoolean()) {
                graphics.fillRect(i * rectWidth, 2 * rectWidth, rectWidth, rectWidth);
            }
        }
    }

    public static void createImage(long id) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = image.getGraphics();
        File file = new File("c:/xx/image" + id + ".jpg");
        try {
            graphics.setColor(Color.WHITE);
            graphics.fillRect(0, 0, width, height);
            graphics.setColor(new Color(47, 122, 114));
            boolean isRect = false;
            fillLeftRightSymmetry(graphics, isRect);
//            else fillUpDownSymmetry(graphics, isRect);

            //  ImageIO.write(image, "PNG", file);//生成图片方法一
            //ImageIO,可以生成不同格式的图片，比如JPG,PNG,GIF.....
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        //生成图片方法二开始,只知道生成jpg格式的图片,这个方法其他格式的还是不知道怎么弄。
        try {
            FileOutputStream fos = new FileOutputStream(file);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(bos);
            encoder.encode(image);
            bos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //生成图片方法二结束
        graphics.dispose();//释放资源
    }


    public static void main(String[] args) throws Exception {

        for (int i = 0; i < 40; i++) {

            createImage(i);
        }

    }
}
