package fengfei.fir.service.impl;

import fengfei.fir.service.JpegExifWriter;
import org.im4java.core.ETOperation;
import org.im4java.core.ExiftoolCmd;
import org.im4java.process.ArrayListOutputConsumer;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * <pre>
 * http://owl.phy.queensu.ca/~phil/exiftool/
 * ExifTool by Phil Harvey
 * </pre>
 * 
 */
public class PhilHarveyJpegExifWriter implements JpegExifWriter {

    public static void main(String[] args) throws Exception {
        JpegExifWriter w = new PhilHarveyJpegExifWriter();
        // File files = new File("C:\\Users\\wtt\\Desktop\\3");
        File file = new File(
            "C:/Users/wtt/Documents/GitHub/spruce/sprucy/upload/0/0/183g4mm1h8bgp1-1cb7ofb/0.JPG");
        Map<String, String> values = new HashMap<>();
        values.put(JpegExifWriter.Make, "NewCanon");
        values.put(JpegExifWriter.Model, "New CI F560P");
        // values.put(JpegExifReadWrite.FNumber, "1.7");
        values.put(JpegExifWriter.ExposureTime, "1/2000");
        values.put(JpegExifWriter.ShutterSpeedValue, "1/5000");

        values.put(JpegExifWriter.ISO, "1000");
        values.put(JpegExifWriter.LensModel, "New EFC 55-250MM IS USM");
        values.put(JpegExifWriter.Lens, "New EFC 55-250MM IS USM");
        values.put(JpegExifWriter.FocalLength, "314");

        values.put(JpegExifWriter.ExposureCompensation, "0.1");

        w.writeExif(file.getAbsolutePath(), values, null);
    }

    public Map<String, Object> writeExif(
        String imageFile,
        Map<String, String> exifs,
        Map<String, String> contents) throws Exception {
        ETOperation op = new ETOperation();

        op.overwrite_original_in_place();
        String[] params = new String[exifs.size()];

        int i = 0;
        for (Entry<String, String> entry : exifs.entrySet()) {
            params[i] = "exif:" + entry.getKey() + "=" + entry.getValue();
            i++;
        }

        op.setTags(params);
        op.addImage();

        ArrayListOutputConsumer output = new ArrayListOutputConsumer();
        ExiftoolCmd et = new ExiftoolCmd();
        et.setOutputConsumer(output);

        et.run(op, imageFile);
        // ArrayList<String> cmdOutput = output.getOutput();
        // for (String line : cmdOutput) {
        // System.out.println(line);
        // }
        return null;
    }

    @Override
    public Map<String, Object> writePhoto(
        String imageFile,
        Map<String, String> values,
        Map<String, String> contents) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Object> updateExif(Map<String, String> contents) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

}
