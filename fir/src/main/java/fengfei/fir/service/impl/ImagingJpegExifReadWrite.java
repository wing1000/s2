package fengfei.fir.service.impl;

//
//import static org.apache.commons.imaging.formats.tiff.constants.ExifTagConstants.EXIF_TAG_EXPOSURE_COMPENSATION;
//import static org.apache.commons.imaging.formats.tiff.constants.ExifTagConstants.EXIF_TAG_EXPOSURE_TIME;
//import static org.apache.commons.imaging.formats.tiff.constants.ExifTagConstants.EXIF_TAG_FNUMBER;
//import static org.apache.commons.imaging.formats.tiff.constants.ExifTagConstants.EXIF_TAG_FOCAL_LENGTH;
//import static org.apache.commons.imaging.formats.tiff.constants.ExifTagConstants.EXIF_TAG_FOCAL_LENGTH_IN_35MM_FORMAT;
//import static org.apache.commons.imaging.formats.tiff.constants.ExifTagConstants.EXIF_TAG_ISO;
//import static org.apache.commons.imaging.formats.tiff.constants.ExifTagConstants.EXIF_TAG_LENS;
//import static org.apache.commons.imaging.formats.tiff.constants.ExifTagConstants.EXIF_TAG_SHUTTER_SPEED_VALUE;
//import static org.apache.commons.imaging.formats.tiff.constants.TiffDirectoryType.EXIF_DIRECTORY_EXIF_IFD;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.OutputStream;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Map.Entry;
//
//import org.apache.commons.imaging.ImageReadException;
//import org.apache.commons.imaging.ImageWriteException;
//import org.apache.commons.imaging.Imaging;
//import org.apache.commons.imaging.common.IImageMetadata;
//import org.apache.commons.imaging.common.RationalNumber;
//import org.apache.commons.imaging.formats.jpeg.JpegImageMetadata;
//import org.apache.commons.imaging.formats.jpeg.exif.ExifRewriter;
//import org.apache.commons.imaging.formats.tiff.TiffField;
//import org.apache.commons.imaging.formats.tiff.TiffImageMetadata;
//import org.apache.commons.imaging.formats.tiff.constants.ExifTagConstants;
//import org.apache.commons.imaging.formats.tiff.constants.TiffConstants;
//import org.apache.commons.imaging.formats.tiff.constants.TiffDirectoryConstants;
//import org.apache.commons.imaging.formats.tiff.constants.TiffTagConstants;
//import org.apache.commons.imaging.formats.tiff.fieldtypes.FieldType;
//import org.apache.commons.imaging.formats.tiff.fieldtypes.FieldTypeAscii;
//import org.apache.commons.imaging.formats.tiff.fieldtypes.FieldTypeByte;
//import org.apache.commons.imaging.formats.tiff.fieldtypes.FieldTypeLong;
//import org.apache.commons.imaging.formats.tiff.fieldtypes.FieldTypeRational;
//import org.apache.commons.imaging.formats.tiff.fieldtypes.FieldTypeShort;
//import org.apache.commons.imaging.formats.tiff.taginfos.TagInfo;
//import org.apache.commons.imaging.formats.tiff.taginfos.TagInfoAscii;
//import org.apache.commons.imaging.formats.tiff.write.TiffOutputDirectory;
//import org.apache.commons.imaging.formats.tiff.write.TiffOutputField;
//import org.apache.commons.imaging.formats.tiff.write.TiffOutputSet;
//import org.apache.commons.io.FileUtils;
//import org.apache.commons.io.FilenameUtils;
//
//import fengfei.sprucy.service.ExifException;
//import fengfei.sprucy.service.JpegExifReadWrite;
//
//public class ImagingJpegExifReadWrite implements JpegExifReadWrite {
//
//	public static final TagInfoAscii EXIF_TAG_LENS_MODEL = new TagInfoAscii(
//			"LensModel",
//			0xA434,
//			-1,
//			EXIF_DIRECTORY_EXIF_IFD);
//	//
//	public static final TagInfoAscii EXIF_TAG_CANON_LENS_MODEL = new TagInfoAscii(
//			"CanonLensModel",
//			0x0095,
//			1,
//			EXIF_DIRECTORY_EXIF_IFD);
//
//	public static final TagInfoAscii EXIF_TAG_CANON_LENS_MAKE = new TagInfoAscii(
//			"CanonLensModel",
//			0xA433,
//			1,
//			EXIF_DIRECTORY_EXIF_IFD);
//	public static final TagInfoAscii EXIF_TAG_LENS_SPECIFICATION = new TagInfoAscii(
//			"CanonLensModel",
//			0xA432,
//			1,
//			EXIF_DIRECTORY_EXIF_IFD);
//
//	final static TagInfo[] AllTagInfos = {//
//	TiffTagConstants.TIFF_TAG_MAKE,//
//	TiffTagConstants.TIFF_TAG_MODEL, //
//	EXIF_TAG_FNUMBER, //
//	EXIF_TAG_SHUTTER_SPEED_VALUE,//
//	EXIF_TAG_EXPOSURE_TIME, //
//	EXIF_TAG_ISO, //
//	EXIF_TAG_LENS, //
//	EXIF_TAG_LENS_MODEL,//
//	EXIF_TAG_FOCAL_LENGTH, //
//	EXIF_TAG_FOCAL_LENGTH_IN_35MM_FORMAT,//
//	EXIF_TAG_EXPOSURE_COMPENSATION,//
//	EXIF_TAG_CANON_LENS_MAKE,//
//	EXIF_TAG_CANON_LENS_MODEL, //
//	};
//
//	/**
//	 * @param args
//	 * @throws Exception
//	 */
//	public static void main(String[] args) throws Exception {
//		ImagingJpegExifReadWrite d = new ImagingJpegExifReadWrite();
//
//		//
//		File files = new File("C:\\Users\\wtt\\Desktop\\3");
//		File[] fs = files.listFiles();
//
//		for (File f : fs) {
//			if (f.isFile() && "jpg".equalsIgnoreCase(FilenameUtils.getExtension(f.getName()))) {
//				System.out.println("===========================" + f.getAbsolutePath());
//				Map<String, String> values = new HashMap<>();
//				values.put(TiffConstants.EXIF_TAG_LENS.name, "New CI F560P");
//				d.writeExif(f.getAbsolutePath(), values);
//
//			}
//		}
//		fs = files.listFiles();
//		for (File f : fs) {
//			if (f.isFile() && "jpg".equalsIgnoreCase(FilenameUtils.getExtension(f.getName()))) {
//				System.out.println("===========================" + f.getAbsolutePath());
//				Map<String, String> exif = d.readExif(f);
//				System.out.println(exif);
//
//			}
//		}
//
//	}
//
//	@Override
//	public Map<String, String> readExif(String srcFile) throws ExifException {
//
//		return readExif(new File(srcFile));
//	}
//
//	/**
//	 * op.getTags("Make", "Model", "FNumber", "ShutterSpeed", "iso", "Lens",
//	 */
//	@Override
//	public Map<String, String> readExif(File file) throws ExifException {
//		Map<String, String> exif = new HashMap<>();
//		IImageMetadata metadata = null;
//		try {
//			metadata = Imaging.getMetadata(file);
//			System.out.println(metadata);
//			if (metadata == null) {
//				return exif;
//			}
//			if (metadata instanceof JpegImageMetadata) {
//				// op.getTags("Make", "Model", "FNumber", "ShutterSpeed", "iso",
//				// "Lens",
//				// "FocalLength");
//
//				JpegImageMetadata jpegMetadata = (JpegImageMetadata) metadata;
//				for (int i = 0; i < AllTagInfos.length; i++) {
//					TagInfo tag = AllTagInfos[i];
//					TiffField f = jpegMetadata.findEXIFValue(tag);
//					if (f != null) {
//						if (f.getValue() instanceof String) {
//							exif.put(tag.name, f.getValue().toString().trim());
//						} else {
//							exif.put(tag.name, f.getValueDescription());
//						}
//
//					}
//				}
//
//				// TiffImageMetadata exifMetadata = jpegMetadata.getExif();
//				//
//				// List<TiffField> fields = exifMetadata.getAllFields();
//				//
//				// for (TiffField field : fields) {
//				//
//				// String name = field.getFieldTypeName();
//				// String tag = field.getTagName();
//				// Object value = field.getValueDescription();
//				// System.out.printf("hah: tag= %s  value=%s \n", tag, value);
//				//
//				// }
//				// exifMetadata.getGPS();
//			} else {
//				throw new ExifException("Input file is not JPEG format.");
//			}
//		} catch (ImageReadException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return exif;
//	}
//
//	private TagInfo findTagInfo(String name) {
//		for (TagInfo tag : ExifTagConstants.ALL_EXIF_TAGS) {
//			if (tag.name.equalsIgnoreCase(name)) {
//				return tag;
//			}
//		}
//
//		return null;
//	}
//
//	public void writeExif(String imageFile, Map<String, String> values) throws Exception {
//		writeExif(imageFile, true, values);
//	}
//
//	private void writeExif(
//			String imageFile,
//			boolean isReadOriginTags,
//			Map<String, String> values) throws Exception {
//		String newFileName = FilenameUtils.getFullPath(imageFile) + FilenameUtils
//				.getBaseName(imageFile) + "_temp." + FilenameUtils.getExtension(imageFile);
//		File srcFile = new File(imageFile);
//		File destFile = new File(newFileName);
//
//		OutputStream out = new FileOutputStream(destFile);
//		TiffOutputSet outputSet = null;
//		if (isReadOriginTags) {
//			try {
//				IImageMetadata metadata = Imaging.getMetadata(srcFile);
//				JpegImageMetadata jpegMetadata = (JpegImageMetadata) metadata;
//				if (null != jpegMetadata) {
//					TiffImageMetadata exif = jpegMetadata.getExif();
//					if (null != exif) {
//						outputSet = exif.getOutputSet();
//					}
//				}
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//
//		if (null == outputSet) {
//			outputSet = new TiffOutputSet();
//		}
//
//		for (Entry<String, String> entry : values.entrySet()) {
//			String key = entry.getKey();
//			String value = entry.getValue();
//			TagInfo tagInfo = findTagInfo(key);
//
//			if (tagInfo != null) {
//				// TagInfo tag = new TagInfo(
//				// tagInfo.name,
//				// tagInfo.tag,
//				// FieldType.FIELD_TYPE_DESCRIPTION_ASCII,
//				// value.length(),
//				// tagInfo.directoryType);
//				TiffOutputDirectory directory = getOrCreateDirectory(tagInfo, outputSet);
//				System.out.printf("%s %s %s \n", directory, tagInfo, outputSet);
//				if (directory != null) {
//					byte[] bs = convertValue(tagInfo, outputSet, value);
//					if (bs != null) {
//						TiffOutputField field = new TiffOutputField(
//								tagInfo,
//								tagInfo.dataTypes.get(0),
//								bs.length,
//								bs);
//						// TiffOutputField field = TiffOutputField.create(tag,
//						// outputSet.byteOrder, value);
//						directory.removeField(tagInfo);
//						directory.add(field);
//					}
//				}
//
//			}
//
//		}
//
//		new ExifRewriter().updateExifMetadataLossless(srcFile, out, outputSet);
//		// new ExifRewriter().updateExifMetadataLossy(srcFile, out, outputSet);
//		out.close();
//		out = null;
//		System.out.printf("%d %d ", destFile.length(), srcFile.length());
//		if (destFile.length() >= srcFile.length()) {
//			if (srcFile.delete()) {
//				// destFile.renameTo(new File(imageFile));
//				FileUtils.moveFile(destFile, new File(imageFile));
//			} else {
//				FileUtils.moveFile(srcFile, new File(imageFile + ".bak"));
//				FileUtils.moveFile(destFile, new File(imageFile));
//			}
//		}
//
//	}
//
//	private
//			TiffOutputDirectory
//			getOrCreateDirectory(TagInfo tagInfo, TiffOutputSet outputSet) {
//		try {
//			if (tagInfo.directoryType.directoryType == TiffDirectoryConstants.DIRECTORY_TYPE_ROOT) {
//				return outputSet.getOrCreateRootDirectory();
//			} else if (tagInfo.directoryType.directoryType == TiffDirectoryConstants.DIRECTORY_TYPE_EXIF) {
//				return outputSet.getOrCreateExifDirectory();
//			} else if (tagInfo.directoryType.directoryType == TiffDirectoryConstants.DIRECTORY_TYPE_GPS) {
//				return outputSet.getOrCreateGPSDirectory();
//			} else {
//				return null;
//			}
//		} catch (ImageWriteException e) {
//			e.printStackTrace();
//			return null;
//		}
//
//	}
//
//	private byte[] convertValue(TagInfo tagInfo, TiffOutputSet outputSet, String value)
//			throws ImageWriteException {
//		byte[] bs = null;
//		FieldType type = tagInfo.dataTypes.get(0);
//		if (type instanceof FieldTypeAscii) {
//			bs = tagInfo.encodeValue(type, value, outputSet.byteOrder);
//
//		} else if (type instanceof FieldTypeByte) {
//			bs = new byte[] { value.getBytes()[0] };
//		} else if (type instanceof FieldTypeShort) {
//			int vs = Integer.parseInt(value);
//			bs = tagInfo.encodeValue(type, vs, outputSet.byteOrder);
//		} else if (type instanceof FieldTypeLong) {
//			long vs = Long.parseLong(value);
//			bs = tagInfo.encodeValue(type, vs, outputSet.byteOrder);
//		} else if (type instanceof FieldTypeRational) {
//			String ss[] = value.split("/");
//			if (ss.length >= 2) {
//				int h = Integer.parseInt(ss[0]);
//				int l = Integer.parseInt(ss[0]);
//				RationalNumber number = new RationalNumber(h, l);
//				bs = tagInfo.encodeValue(type, number, outputSet.byteOrder);
//			} else if (ss.length == 1) {
//				int h = new Float(Float.parseFloat(value) * 100).intValue();
//				int l = 100;
//				RationalNumber number = new RationalNumber(h, l);
//
//				bs = tagInfo.encodeValue(type, number, outputSet.byteOrder);
//			}
//
//		} else {
//			bs = tagInfo.encodeValue(type, value, outputSet.byteOrder);
//		}
//		return bs;
//	}
// }
