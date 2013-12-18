package fengfei.fir.service.impl;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.WriteResult;
import fengfei.fir.model.UploadDone;
import fengfei.fir.mongo.AutoIncrUtils;
import fengfei.fir.service.JpegProcess;
import fengfei.fir.service.LorryStorage;
import fengfei.fir.service.SpruceConstants;
import fengfei.sprucy.Spruce;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static fengfei.sprucy.Spruce.getPreviewDimensions;
import static fengfei.sprucy.Spruce.getPreviewNumber;

public class MongoLorryStorage implements LorryStorage {

    public JpegProcess jpegProcess = Spruce.getJpegProcess();
    public DB db;

    @Override
    public
        UploadDone
        writeFile(Map<String, String> exifs, Map<String, String> contents, File file) {
        List<UploadDone> dones = new ArrayList<>();
        DBCollection coll = db.getCollection(SpruceConstants.PhotoCollectionName);
        UploadDone done = null;

        if (file.exists()) {

            String extName = FilenameUtils.getExtension(file.getAbsolutePath());
            String path = FilenameUtils.getFullPath(file.getAbsolutePath());
            try {
                int num = getPreviewNumber();

                BasicDBObject photo = new BasicDBObject();
                int id = AutoIncrUtils.next(db, SpruceConstants.PhotoCollectionName);
                photo.put("_id", id);
                photo.put("pn", num);
                photo.putAll(exifs);

                Map<Integer, int[]> dimensions = getPreviewDimensions();
                for (int j = 1; j <= num; j++) {
                    int[] wh = dimensions.get(j);
                    String srcImgFileName = file.getAbsolutePath();
                    String newFileName = path + j + extName;
                    jpegProcess.cropAndResize(srcImgFileName, newFileName, wh[0], wh[1]);
                    BasicDBObject subPhoto = new BasicDBObject();
                    byte[] data = getFileByte(file);
                    subPhoto.put("preview_id", j);
                    subPhoto.put("data", data);
                    coll.createIndex(new BasicDBObject("p" + j, 1));
                    photo.put("p" + j, subPhoto);
                }
                WriteResult wr = coll.insert(photo);
                done = new UploadDone(0, "");
            } catch (Exception e) {
                e.printStackTrace();
                done = new UploadDone(0, "");
                done.getExif().put("error", e.getMessage());
            }
        } else {
            done = new UploadDone(0, null);
        }
        return done;
    }

    private byte[] getFileByte(File file) throws Exception {
        int len = (int) file.length();
        FileInputStream in = new FileInputStream(file);
        byte[] bs = new byte[len];
        int s = in.read(bs);
        in.close();
        return bs;
    }

    @Override
    public List<UploadDone> writeFile(InputStream... ins) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void readFile(String fileName, OutputStream out) {
        // TODO Auto-generated method stub

    }

    @Override
    public void readFile(String[] fileNames, OutputStream[] outs) {
        // TODO Auto-generated method stub

    }

}
