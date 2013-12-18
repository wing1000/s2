package fengfei.fir.service.impl;

import fengfei.fir.service.JpegExifWriter;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.sanselan.ImageWriteException;
import org.apache.sanselan.Sanselan;
import org.apache.sanselan.common.IImageMetadata;
import org.apache.sanselan.common.RationalNumber;
import org.apache.sanselan.formats.jpeg.JpegImageMetadata;
import org.apache.sanselan.formats.jpeg.exifRewrite.ExifRewriter;
import org.apache.sanselan.formats.tiff.TiffImageMetadata;
import org.apache.sanselan.formats.tiff.constants.ExifTagConstants;
import org.apache.sanselan.formats.tiff.constants.TagInfo;
import org.apache.sanselan.formats.tiff.constants.TiffDirectoryConstants;
import org.apache.sanselan.formats.tiff.fieldtypes.*;
import org.apache.sanselan.formats.tiff.write.TiffOutputDirectory;
import org.apache.sanselan.formats.tiff.write.TiffOutputField;
import org.apache.sanselan.formats.tiff.write.TiffOutputSet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.Map.Entry;

public class SanselanJpegExifWriter implements JpegExifWriter, SanselanExifTagConstants {

    private TagInfo findTagInfo(String name) {
        for (int i = 0; i < ExifTagConstants.ALL_EXIF_TAGS.length; i++) {
            TagInfo tag = ExifTagConstants.ALL_EXIF_TAGS[i];
            if (tag.name.equalsIgnoreCase(name)) {
                return tag;
            }
        }
        return null;
    }

    public Map<String, Object> writeExif(
        String imageFile,
        Map<String, String> values,
        Map<String, String> contents) throws Exception {
        writeExif(imageFile, true, values);
        return null;
    }

    private void writeExif(String imageFile, boolean isReadOriginTags, Map<String, String> values)
        throws Exception {
        String newFileName = FilenameUtils.getFullPath(imageFile)
                + FilenameUtils.getBaseName(imageFile) + "_temp."
                + FilenameUtils.getExtension(imageFile);

        File srcFile = new File(imageFile);
        File destFile = new File(newFileName);

        OutputStream out = new FileOutputStream(destFile);
        TiffOutputSet outputSet = null;
        if (isReadOriginTags) {
            try {
                IImageMetadata metadata = Sanselan.getMetadata(srcFile);
                JpegImageMetadata jpegMetadata = (JpegImageMetadata) metadata;
                if (null != jpegMetadata) {
                    TiffImageMetadata exif = jpegMetadata.getExif();
                    if (null != exif) {
                        outputSet = exif.getOutputSet();
                    }
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        if (null == outputSet) {
            outputSet = new TiffOutputSet();
        }

        for (Entry<String, String> entry : values.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            TagInfo tagInfo = findTagInfo(key);

            if (tagInfo != null) {
                // TagInfo tag = new TagInfo(
                // tagInfo.name,
                // tagInfo.tag,
                // FieldType.FIELD_TYPE_DESCRIPTION_ASCII,
                // value.length(),
                // tagInfo.directoryType);

                TiffOutputDirectory directory = getOrCreateDirectory(tagInfo, outputSet);
                System.out.printf("%s %s %s \n", directory, tagInfo, outputSet);
                if (directory != null) {
                    byte[] bs = convertValue(tagInfo, outputSet, value);
                    if (bs != null) {
                        TiffOutputField field = new TiffOutputField(
                            tagInfo,
                            tagInfo.dataTypes[0],
                            bs.length,
                            bs);
                        // TiffOutputField field = TiffOutputField.create(tag,
                        // outputSet.byteOrder, value);
                        directory.removeField(tagInfo);
                        directory.add(field);
                    }

                }

            }

        }

        new ExifRewriter().updateExifMetadataLossless(srcFile, out, outputSet);
        // new ExifRewriter().updateExifMetadataLossy(srcFile, out, outputSet);
        out.close();
        out = null;

        // System.out.printf("%d %d ", destFile.length(), srcFile.length());

        if (destFile.length() >= srcFile.length()) {
            if (srcFile.delete()) {
                // destFile.renameTo(new File(imageFile));
                FileUtils.moveFile(destFile, new File(imageFile));
            } else {
                FileUtils.moveFile(srcFile, new File(imageFile + ".bak"));
                FileUtils.moveFile(destFile, new File(imageFile));
            }
        }

    }

    private TiffOutputDirectory getOrCreateDirectory(TagInfo tagInfo, TiffOutputSet outputSet) {
        try {
            if (tagInfo.directoryType.directoryType == TiffDirectoryConstants.DIRECTORY_TYPE_ROOT) {
                return outputSet.getOrCreateRootDirectory();
            } else if (tagInfo.directoryType.directoryType == TiffDirectoryConstants.DIRECTORY_TYPE_EXIF) {
                return outputSet.getOrCreateExifDirectory();
            } else if (tagInfo.directoryType.directoryType == TiffDirectoryConstants.DIRECTORY_TYPE_GPS) {
                return outputSet.getOrCreateGPSDirectory();
            } else {
                return null;
            }
        } catch (ImageWriteException e) {
            e.printStackTrace();
            return null;
        }

    }

    private byte[] convertValue(TagInfo tagInfo, TiffOutputSet outputSet, String value)
        throws ImageWriteException {

        byte[] bs = null;
        FieldType type = tagInfo.dataTypes[0];
        if (type instanceof FieldTypeASCII) {
            bs = tagInfo.encodeValue(type, value, outputSet.byteOrder);

        } else if (type instanceof FieldTypeByte) {
            bs = new byte[] { value.getBytes()[0] };
        } else if (type instanceof FieldTypeShort) {
            int vs = Integer.parseInt(value);
            bs = tagInfo.encodeValue(type, vs, outputSet.byteOrder);
        } else if (type instanceof FieldTypeLong) {
            long vs = Long.parseLong(value);
            bs = tagInfo.encodeValue(type, vs, outputSet.byteOrder);
        } else if (type instanceof FieldTypeRational) {
            String ss[] = value.split("/");
            if (ss.length >= 2) {
                int h = Integer.parseInt(ss[0]);
                int l = Integer.parseInt(ss[0]);
                RationalNumber number = new RationalNumber(h, l);
                bs = tagInfo.encodeValue(type, number, outputSet.byteOrder);
            } else if (ss.length == 1) {
                int h = new Float(Float.parseFloat(value) * 100).intValue();
                int l = 100;
                RationalNumber number = new RationalNumber(h, l);

                bs = tagInfo.encodeValue(type, number, outputSet.byteOrder);
            }

        } else {
            bs = tagInfo.encodeValue(type, value, outputSet.byteOrder);
        }
        return bs;
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
