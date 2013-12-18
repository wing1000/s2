package fengfei.fir.service.impl;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.CanonMakernoteDirectory;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import fengfei.fir.service.ExifException;
import fengfei.fir.service.JpegExifReader;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * http://www.drewnoakes.com/code/exif/
 * http://code.google.com/p/metadata-extractor/
 *  base metadata-extractor
 * </pre>
 * 
 * @author
 * 
 */
public class DrewNoakesJpegExifReader implements JpegExifReader {

    final static String Agfa = "Agfa";
    final static String Canon = "Canon";
    final static String Casio = "Casio";
    final static String Epson = "Epson";
    final static String Fujifilm = "Fujifilm";
    final static String Kodak = "Kodak";
    final static String Kyocera = "Kyocera ";
    final static String Minolta = "Minolta ";
    final static String Nikon = "Nikon";
    final static String Olympus = "Olympus ";
    final static String Pentax = "Pentax  ";
    final static String Panasonic = "Panasonic";
    final static String Sigma = "Sigma";
    final static String Foveon = "Foveon";
    final static String Sony = "Sony";
    final static Map<Integer, String> nameMap = new HashMap<>();
    static {
        nameMap.put(ExifIFD0Directory.TAG_MAKE, Make);
        nameMap.put(ExifIFD0Directory.TAG_MODEL, Model);
        nameMap.put(ExifSubIFDDirectory.TAG_EXPOSURE_TIME, ExposureTime);
        nameMap.put(ExifSubIFDDirectory.TAG_SHUTTER_SPEED, ShutterSpeedValue);
        nameMap.put(ExifSubIFDDirectory.TAG_FNUMBER, FNumber);
        nameMap.put(ExifSubIFDDirectory.TAG_ISO_EQUIVALENT, ISO);

        nameMap.put(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL, DateTimeOriginal);
        nameMap.put(ExifSubIFDDirectory.TAG_FOCAL_LENGTH, FocalLength);
        // nameMap.put(ExifSubIFDDirectory.TAG_COLOR_SPACE);
        nameMap.put(ExifSubIFDDirectory.TAG_LENS_MODEL, LensModel);
        nameMap.put(ExifSubIFDDirectory.TAG_LENS, Lens);
        nameMap.put(CanonMakernoteDirectory.TAG_LENS_MODEL, LensModel);
    }

    @Override
    public Map<String, String> readExif(String srcFile) throws ExifException {
        return readExif(new File(srcFile));
    }

    @Override
    public Map<String, String> readExif(File file) throws ExifException {
        Map<String, String> exif = new HashMap<String, String>();
        Metadata metadata = null;
        try {
            metadata = ImageMetadataReader.readMetadata(file);
        } catch (ImageProcessingException | IOException e) {
            return exif;
        }

        Directory directory = metadata.getDirectory(ExifIFD0Directory.class);
        if (directory == null) {
            return exif;
        }
        exif.put(
            nameMap.get(ExifIFD0Directory.TAG_MAKE),
            directory.getString(ExifIFD0Directory.TAG_MAKE));
        exif.put(
            nameMap.get(ExifIFD0Directory.TAG_MODEL),
            directory.getString(ExifIFD0Directory.TAG_MODEL));

        // for (Tag tag : directory.getTags()) {
        // System.out.println(tag);
        // }
        String manufacturer = exif.get(directory.getTagName(ExifIFD0Directory.TAG_MAKE));

        directory = metadata.getDirectory(ExifSubIFDDirectory.class);
        exif.put(
            nameMap.get(ExifSubIFDDirectory.TAG_EXPOSURE_TIME),
            directory.getString(ExifSubIFDDirectory.TAG_EXPOSURE_TIME));
        exif.put(
            nameMap.get(ExifSubIFDDirectory.TAG_SHUTTER_SPEED),
            directory.getString(ExifSubIFDDirectory.TAG_SHUTTER_SPEED));
        exif.put(
            nameMap.get(ExifSubIFDDirectory.TAG_FNUMBER),
            directory.getString(ExifSubIFDDirectory.TAG_FNUMBER));
        // exif.put(nameMap.get(ExifSubIFDDirectory.TAG_APERTURE),
        // directory.getString(ExifSubIFDDirectory.TAG_APERTURE));
        exif.put(
            nameMap.get(ExifSubIFDDirectory.TAG_ISO_EQUIVALENT),
            directory.getString(ExifSubIFDDirectory.TAG_ISO_EQUIVALENT));
        exif.put(
            nameMap.get(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL),
            directory.getString(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL));
        // exif.put(nameMap.get(ExifSubIFDDirectory.TAG_EXPOSURE_BIAS),
        // directory.getString(ExifSubIFDDirectory.TAG_EXPOSURE_BIAS));
        exif.put(
            nameMap.get(ExifSubIFDDirectory.TAG_FOCAL_LENGTH),
            directory.getString(ExifSubIFDDirectory.TAG_FOCAL_LENGTH));
        // exif.put(nameMap.get(ExifSubIFDDirectory.TAG_COLOR_SPACE),
        // directory.getString(ExifSubIFDDirectory.TAG_COLOR_SPACE));
        exif.put(
            nameMap.get(ExifSubIFDDirectory.TAG_LENS_MODEL),
            directory.getString(ExifSubIFDDirectory.TAG_LENS_MODEL));
        exif.put(
            nameMap.get(ExifSubIFDDirectory.TAG_LENS),
            directory.getString(ExifSubIFDDirectory.TAG_LENS));

        if (Canon.equalsIgnoreCase(manufacturer)) {
            directory = metadata.getDirectory(CanonMakernoteDirectory.class);
            if (directory != null)
                exif.put(
                    nameMap.get(CanonMakernoteDirectory.TAG_LENS_MODEL),
                    directory.getString(CanonMakernoteDirectory.TAG_LENS_MODEL));
        }
        // } else if (Nikon.equalsIgnoreCase(manufacturer)) {
        // directory = metadata
        // .getDirectory(NikonType2MakernoteDirectory.class);
        //
        // exif.put(directory
        // .getTagName(NikonType2MakernoteDirectory.TAG_LENS_MODEL),
        // directory.getString(CanonMakernoteDirectory.TAG_LENS_MODEL));
        // } else if (Sony.equalsIgnoreCase(manufacturer)) {
        // directory = metadata
        // .getDirectory(SonyType6MakernoteDirectory.class);
        //
        // exif.put(directory
        // .getTagName(CanonMakernoteDirectory.TAG_LENS_MODEL),
        // directory.getString(CanonMakernoteDirectory.TAG_LENS_MODEL));
        //
        // } else if (Sigma.equalsIgnoreCase(manufacturer)
        // || Foveon.equalsIgnoreCase(manufacturer)) {
        // directory = metadata.getDirectory(SigmaMakernoteDirectory.class);
        //
        // exif.put(directory
        // .getTagName(CanonMakernoteDirectory.TAG_LENS_MODEL),
        // directory.getString(CanonMakernoteDirectory.TAG_LENS_MODEL));
        //
        // } else if (Pentax.equalsIgnoreCase(manufacturer)) {
        // directory = metadata.getDirectory(PentaxMakernoteDirectory.class);
        //
        // exif.put(directory
        // .getTagName(CanonMakernoteDirectory.TAG_LENS_MODEL),
        // directory.getString(CanonMakernoteDirectory.TAG_LENS_MODEL));
        //
        // }

        // for (Tag tag : directory.getTags()) {
        // System.out.println(tag);
        // }
        // directory = metadata.getDirectory(ExifThumbnailDirectory.class);
        // for (Tag tag : directory.getTags()) {
        // System.out.println(tag);
        // }
        // directory = metadata.getDirectory(ExifInteropDirectory.class);
        // for (Tag tag : directory.getTags()) {
        // System.out.println(tag);
        // }
        // for (Tag tag : directory.getTags()) {
        // exif.put(tag.getTagName(), tag.getDescription());
        // }
        // for (Directory directory : metadata.getDirectories()) {
        // for (Tag tag : directory.getTags()) {
        // System.out.println(tag);
        // }
        // }
        return exif;
    }

    public static void main(String[] args) throws Exception {
        DrewNoakesJpegExifReader r = new DrewNoakesJpegExifReader();
        // File jpegFile = new
        // File("C:\\Users\\tietang\\Desktop\\New folder\\IMG_2692.jpg");
        // File jpegFile = new
        // File("C:\\Users\\wtt\\Desktop\\3\\IMG_33382.jpg");
        // System.out.println(getExif(jpegFile));
        String file = "C:/Users/wtt/Desktop/3/IMG_3810.JPG";
        System.out.println(r.readExif((new File(file))));
        // Map<String, String> values = new HashMap<>();
        // values.put("Make", "New CN");
        // values.put("Model", "New CN S2356X-OP");
        // values.put("FNumber", "0.1");
        // values.put("Lens", "8-1200 F1.0");
        // writeExif(values, jpegFile2);

    }
}
