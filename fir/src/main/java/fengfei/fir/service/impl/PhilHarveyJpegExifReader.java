package fengfei.fir.service.impl;

import fengfei.fir.service.ExifException;
import fengfei.fir.service.JpegExifReader;
import org.im4java.core.ETOperation;
import org.im4java.core.ExiftoolCmd;
import org.im4java.core.IM4JavaException;
import org.im4java.process.ArrayListOutputConsumer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * http://owl.phy.queensu.ca/~phil/exiftool/
 * ExifTool by Phil Harvey
 * </pre>
 * 
 */
public class PhilHarveyJpegExifReader implements JpegExifReader {

    /**
     * @param args
     */
    public static void main(String[] args) {
        String file = "C:/Users/wtt/Documents/GitHub/spruce/sprucy/upload/0/0/183g7u41h8bjbab0l40g/2.JPG";
        PhilHarveyJpegExifReader w = new PhilHarveyJpegExifReader();
        Map<String, String> exif = w.readExif(file);
        System.out.println(exif);
    }

    @Override
    public Map<String, String> readExif(String srcFile) throws ExifException {
        Map<String, String> exif = new HashMap<>();
        ETOperation op = new ETOperation();
        op.getTags(AllTags);

        op.addImage();

        // setup command and execute it (capture output)
        ArrayListOutputConsumer output = new ArrayListOutputConsumer();
        ExiftoolCmd et = new ExiftoolCmd();
        et.setOutputConsumer(output);

        // et.run(op, "C://Users//wtt//Desktop//3//IMG_3407.JPG");
        // et.run(op, "C://Users//wtt//Desktop//3//");
        // et.run(op, "L://im4java-1.3.2//images.src");
        try {
            et.run(op, srcFile); // dump output
            ArrayList<String> cmdOutput = output.getOutput();
            for (String line : cmdOutput) {
                int index = line.indexOf(':');
                String name = line.substring(0, index).replaceAll(" ", "");
                String value = line.substring(index + 1);
                exif.put(name, value);
                // System.out.println(name + "=" + value);

            }
        } catch (IOException | InterruptedException | IM4JavaException e) {
            e.printStackTrace();
        }

        return exif;

    }

    @Override
    public Map<String, String> readExif(File file) throws ExifException {
        return readExif(file.getAbsolutePath());
    }

}
