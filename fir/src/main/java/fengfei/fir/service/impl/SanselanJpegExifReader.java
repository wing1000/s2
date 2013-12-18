package fengfei.fir.service.impl;

import fengfei.fir.service.ExifException;
import fengfei.fir.service.JpegExifReader;
import org.apache.sanselan.ImageReadException;
import org.apache.sanselan.Sanselan;
import org.apache.sanselan.common.IImageMetadata;
import org.apache.sanselan.formats.jpeg.JpegImageMetadata;
import org.apache.sanselan.formats.tiff.TiffField;
import org.apache.sanselan.formats.tiff.constants.ExifTagConstants;
import org.apache.sanselan.formats.tiff.constants.TagInfo;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SanselanJpegExifReader implements JpegExifReader, SanselanExifTagConstants {

    @Override
    public Map<String, String> readExif(String srcFile) throws ExifException {
        return readExif(new File(srcFile));
    }

    /**
     * op.getTags("Make", "Model", "FNumber", "ShutterSpeed", "iso", "Lens",
     */
    @Override
    public Map<String, String> readExif(File file) throws ExifException {
        Map<String, String> exif = new HashMap<>();
        IImageMetadata metadata = null;
        try {
            metadata = Sanselan.getMetadata(file);
            System.out.println(metadata);
            if (metadata == null) {
                return exif;
            }
            if (metadata instanceof JpegImageMetadata) {
                // op.getTags("Make", "Model", "FNumber", "ShutterSpeed", "iso",
                // "Lens",
                // "FocalLength");

                JpegImageMetadata jpegMetadata = (JpegImageMetadata) metadata;
                for (int i = 0; i < AllTagInfos.length; i++) {
                    TagInfo tag = AllTagInfos[i];
                    TiffField f = jpegMetadata.findEXIFValue(tag);
                    if (f != null) {
                        if (f.getValue() instanceof String) {

                            exif.put(tag.name, f.getValue().toString().trim());
                        } else {
                            exif.put(tag.name, f.getValueDescription());
                        }

                    }
                }

                // TiffImageMetadata exifMetadata = jpegMetadata.getExif();
                //
                // List<TiffField> fields = exifMetadata.getAllFields();
                //
                // for (TiffField field : fields) {
                //
                // String name = field.getFieldTypeName();
                // String tag = field.getTagName();
                // Object value = field.getValueDescription();
                // System.out.printf("hah: tag= %s  value=%s \n", tag, value);
                //
                // }
                // exifMetadata.getGPS();
            } else {
                throw new ExifException("Input file is not JPEG format.");
            }
        } catch (ImageReadException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return exif;
    }

    private TagInfo findTagInfo(String name) {
        for (int i = 0; i < ExifTagConstants.ALL_EXIF_TAGS.length; i++) {
            TagInfo tag = ExifTagConstants.ALL_EXIF_TAGS[i];
            if (tag.name.equalsIgnoreCase(name)) {
                return tag;
            }
        }
        return null;
    }

}
