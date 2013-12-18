package fengfei.fir.service.impl;

import com.mongodb.DB;
import fengfei.fir.model.UploadDone;
import fengfei.fir.service.JpegExifWriter;
import fengfei.fir.service.JpegProcess;
import fengfei.fir.service.LorryStorage;
import fengfei.fir.utils.Path;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import static fengfei.sprucy.Spruce.getPreviewDimensions;
import static fengfei.sprucy.Spruce.getPreviewNumber;

public class DiskLorryStorage implements LorryStorage {

    static Logger logger = LoggerFactory.getLogger(DiskLorryStorage.class);
    public DB db;
    public JpegProcess jpegProcess = new DefaultJpegProcess();// Spruce.getJpegProcess();
    public JpegExifWriter jpegExifWrite = new SqlJpegExifWriter();// MongoJpegExifWriter();

    @Override
    public
        UploadDone
        writeFile(Map<String, String> exifs, Map<String, String> contents, File file)
            throws Exception {

        UploadDone done = null;
        if (file.exists()) {
            // String id = XUID.newB20String();

            Map<String, Object> rv = jpegExifWrite.writeExif(
                file.getAbsolutePath(),
                exifs,
                contents);
            long id = MapUtils.getLongValue(rv, JpegExifWriter.KeyId);
            // int id = AutoIncrUtils.next(db,
            // SpruceConstants.PhotoCollectionName);
            int index = file.getName().lastIndexOf('.');
            String extName = file.getName().substring(index).toLowerCase();

            StringBuilder sbPath = Path.getPath(Path.UPLOAD_PATH, id);
            String path = sbPath.toString();
            String jpgPath = sbPath.append(0).append(extName).toString();
            // System.out.printf("path=%s id=%d\n", path, id);
            // String jpgPath=Path.getJpegUploadPath(id, 0);
            try {

                File jpegFile = new File(jpgPath);
                FileUtils.moveFile(file, jpegFile);

                String srcImgFileName = jpegFile.getAbsolutePath();
                // jpegExifWrite.writeExif(srcImgFileName, exif);
                // jpegExifReadWrite.writeExif(srcImgFileName, values);
                // String newFileName =
                // FilenameUtils.getFullPath(srcImgFileName) + FilenameUtils
                // .getBaseName(srcImgFileName) + "_" + width + "x" + height
                // +
                // "." + FilenameUtils
                // .getExtension(srcImgFileName);
                int num = getPreviewNumber();
                Map<Integer, int[]> dimensions = getPreviewDimensions();
                for (int j = 1; j <= num; j++) {
                    int[] wh = dimensions.get(j);
                    String newFileName = path + j + extName;
                    jpegProcess.cropAndResize(srcImgFileName, newFileName, wh[0], wh[1]);

                }
                // if (jpegProcess instanceof DefaultJpegProcess) {
                // for (int j = 1; j <= num; j++) {
                // String newFileName = path + j + extName;
                // // jpegExifWrite.writeExif(newFileName, exif);
                // }
                // }

                done = new UploadDone(0, String.valueOf(id));

            } catch (Exception e) {
                e.printStackTrace();
                logger.error(String.format("process file error: %s", file.getName()));
                done = new UploadDone(0, String.valueOf(id));
                done.getExif().put("error", e.getMessage());

            }
        } else {
            done = new UploadDone(0, null);

        }

        return done;
    }

    @Override
    public List<UploadDone> writeFile(InputStream... ins) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void readFile(String fileName, OutputStream out) {

    }

    @Override
    public void readFile(String[] fileNames, OutputStream[] outs) {

    }

}
