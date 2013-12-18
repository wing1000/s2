package fengfei.fir.service;

import java.io.File;
import java.util.Map;

public interface JpegExifReader extends JpegExif {

    Map<String, String> readExif(String srcFile) throws ExifException;

    Map<String, String> readExif(File file) throws ExifException;

}
