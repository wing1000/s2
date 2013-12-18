package fengfei.fir.service.impl;

import org.apache.sanselan.formats.tiff.constants.ExifTagConstants;
import org.apache.sanselan.formats.tiff.constants.TagInfo;
import org.apache.sanselan.formats.tiff.constants.TiffConstants;

public interface SanselanExifTagConstants extends ExifTagConstants {

    public static final TagInfo EXIF_TAG_LENS_MODEL = new TagInfo(
        "LensModel",
        0xA434,
        FIELD_TYPE_ASCII,
        1,
        EXIF_DIRECTORY_IFD0);
    public static final TagInfo EXIF_TAG_CANON_LENS_MODEL = new TagInfo(
        "CanonLensModel",
        0x0095,
        FIELD_TYPE_ASCII,
        1,
        EXIF_DIRECTORY_IFD0);
    public static final TagInfo EXIF_TAG_CANON_LENS_MAKE = new TagInfo(
        "CanonLensModel",
        0xA433,
        FIELD_TYPE_ASCII,
        1,
        EXIF_DIRECTORY_IFD0);
    public static final TagInfo EXIF_TAG_LENS_SPECIFICATION = new TagInfo(
        "CanonLensModel",
        0xA432,
        FIELD_TYPE_ASCII,
        1,
        EXIF_DIRECTORY_IFD0);

    public final static TagInfo[] AllTagInfos = { TiffConstants.EXIF_TAG_MAKE,
            TiffConstants.EXIF_TAG_MODEL, TiffConstants.EXIF_TAG_FNUMBER,
            TiffConstants.EXIF_TAG_SHUTTER_SPEED_VALUE, TiffConstants.EXIF_TAG_EXPOSURE_TIME,
            TiffConstants.EXIF_TAG_ISO, EXIF_TAG_LENS, EXIF_TAG_LENS_MODEL, EXIF_TAG_FOCAL_LENGTH,
            EXIF_TAG_FOCAL_LENGTH_IN_35MM_FORMAT, EXIF_TAG_EXPOSURE_COMPENSATION };
}
