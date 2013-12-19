package controllers;

import cn.bran.play.JapidResult;
import fengfei.fir.model.Done;
import fengfei.fir.model.Done.Status;
import fengfei.fir.model.UploadDone;
import fengfei.fir.service.JpegExifWriter;
import fengfei.fir.service.LorryStorage;
import fengfei.fir.service.impl.SqlJpegExifWriter;
import fengfei.fir.utils.MapUtils;
import fengfei.sprucy.Spruce;
import fengfei.ucm.entity.photo.Photo;
import fengfei.ucm.entity.photo.PhotoSet;
import fengfei.ucm.entity.profile.LicenseType;
import fengfei.ucm.repository.PhotoManageRepository;
import fengfei.ucm.repository.PhotoRepository;
import fengfei.ucm.repository.impl.SqlPhotoManageRepository;
import fengfei.ucm.repository.impl.SqlPhotoRepository;
import japidviews.Application.photo.Upload;
import japidviews.Application.photo.UploadForm;
import japidviews.Application.photo.UploadPS;
import japidviews.Application.photo.UploadSwf;
import japidviews.Application.profile.PhotoEdit;
import play.Logger;
import play.modules.router.Any;
import play.modules.router.Get;
import play.modules.router.Gets;
import play.modules.router.Post;
import play.mvc.With;

import java.io.File;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Authority(role = Role.LoggedIn)
public class LorryAction extends Admin {

    public static LorryStorage lorryService = Spruce.getLorryStorage();
    static PhotoManageRepository photoManage = new SqlPhotoManageRepository();
    static PhotoRepository photoRepository = new SqlPhotoRepository();

    @RequestMapping("/ps/upload")
    public static void psUpload() {
        Integer idUser = currentUserId();
        try {
            List<PhotoSet> photoSets = photoManage.selectUserSets(idUser);
            throw new JapidResult(new UploadPS().render(photoSets));
        } catch (Exception e) {
            e.printStackTrace();
            throw new JapidResult(new UploadPS().render(new ArrayList<PhotoSet>()));

        }
    }

    public static void upload() {
        Integer idUser = currentUserId();
        try {
            List<PhotoSet> photoSets = photoManage.selectUserSets(idUser);
            throw new JapidResult(new Upload().render(photoSets));
        } catch (Exception e) {
            e.printStackTrace();
            throw new JapidResult(new Upload().render(new ArrayList<PhotoSet>()));

        }
    }

    @Gets({@Get("/upload/form/{idPhoto}/?"), @Get("/upload/form/?")})
    public static void uploadForm(long idPhoto) {
        Integer idUser = currentUserId();
        try {
            Photo photo = photoRepository.selectOne(idPhoto, idUser);
            List<PhotoSet> photoSets = photoManage.selectUserSets(idUser);
            throw new JapidResult(new UploadForm().render(photo, photoSets));
        } catch (Exception e) {
            e.printStackTrace();
            throw new JapidResult(new UploadForm().render(null, new ArrayList<PhotoSet>()));

        }
    }

    @Get("/upload/swf")
    public static void uploadIe() {
        throw new JapidResult(new UploadSwf().render());
    }

    public static void uploadDone(File[] files) {
        // System.out.println("files size " + files.length);
        // for (File file : files) {
        // try {
        // BufferedImage src = javax.imageio.ImageIO.read(file);
        // int w = src.getWidth();
        // int h = src.getHeight();
        // System.out.printf("file w/h %d , %d , %d \n", w, h,file.length());
        //
        // } catch (IOException e) {
        // e.printStackTrace();
        // }
        // }
        List<play.data.Upload> uploadFiles = (List<play.data.Upload>) request.args
                .get("__UPLOADS");
        System.out.println("file size:" + uploadFiles.size());
//        String exifAll = params.get("exifs");
//        ObjectMapper mapper = new ObjectMapper();
//        Map<String, String> exifs = mapper.readValue(exifAll, Map.class);
        if (uploadFiles != null && uploadFiles.size() > 0) {
            for (play.data.Upload upload : uploadFiles) {
                System.out.println("size: " + upload.getSize());
                if (upload.getSize() > 0) {
                    File file = upload.asFile();

                    System.out.println("files length " + file.length());

                    // System.out.println(new HashMap<>(params.allSimple()));
//                    System.out.println(params.allSimple());
                    UploadDone done;
                    try {

                        Integer idUser = currentUserId();
                        Map<String, String> contents = new HashMap<>(params.allSimple());
                        contents.put("ip", request.remoteAddress);
                        String username = session.get(SESSION_USER_NAME_KEY);
                        contents.put("username", username);
                        contents.put(JpegExifWriter.KeyIdUser, idUser.toString());
                        String licenseKey = contents.get("license");
                        if (licenseKey != null) {
                            LicenseType licenseType = LicenseType.findByKey(licenseKey);
                            contents.put("license", String.valueOf(licenseType.getValue()));
                        }

                        done = lorryService.writeFile(contents, contents, file);
                        done.setMsg(i18n("success"));
                        renderJSON(done);
                    } catch (Exception e) {
                        Logger.error(e, "upload error.");
                        renderErrorJSON();
                    }

                }
            }
        }

        // throw new JapidResult(new Upload().render());
    }

    public static void savePhoto() {

        String[] title = params.getAll("title");
        String[] description = params.getAll("description");
        String[] category = params.getAll("category");
        String[] tags = params.getAll("tags");
        String[] make = params.getAll("make");
        String[] taken_at = params.getAll("taken_at");
        String[] model = params.getAll("camera");
        String[] lens = params.getAll("lens");
        String[] focus = params.getAll("focus");
        String[] shutter = params.getAll("shutter");
        String[] aperture = params.getAll("aperture");
        String[] iso = params.getAll("iso");
        String[] ev = params.getAll("ev");
        String[] dateTimeOriginal = taken_at;// params.getAll("dateTimeOriginal");
        String[] ids = params.getAll("id");

        final List<Photo> models = new ArrayList<>();
        Integer idUser = currentUserId();
        long currentTime = System.currentTimeMillis();
        int createAt = (int) currentTime / 1000;
        Date createAtGmt = new Date(currentTime);
        long updateAt = currentTime;

        for (int i = 0; i < ids.length; i++) {

            // models.add(new PhotoModel(idUser, title[i], description[i],
            // category[i], make[i], model[i], aperture[i], shutter[i],
            // iso[i], lens[i], focus[i], ev[i], dateTimeOriginal[i],
            // tags[i], createdAt, createdAtGmt, updatedAt, -1));

        }

        // writeExif(values, jpegFile2);
        System.out.println("==================");
        // System.out.println(new HashMap<>(params.allSimple()));
        System.out.println(params.allSimple());

        // List<InsertResultSet<Long>> irs = Transactions
        // .execute(new TransactionCallback<List<InsertResultSet<Long>>>() {
        //
        // @Override
        // public List<ForestRunner.InsertResultSet<Long>> execute(
        // ForestGrower grower) throws SQLException {
        // return ExifDao.save(grower, "", models);
        // }
        // });
        // System.out.println("updated: size=" + irs.size());

    }

    @Get("/edit/{idPhoto}/?")
    public static void photoEdit(long idPhoto) {
        photoEdit(idPhoto, null);
    }

    static void photoEdit(long idPhoto, String msg) {
        Integer idUser = currentUserId();
        try {
            Photo photo = photoRepository.selectOne(idPhoto, idUser);
            List<PhotoSet> photoSets = photoManage.selectUserSets(idUser);
            long photoSetId = photoManage.getPhotoSetId(idPhoto, idUser);
            photo.idSet = photoSetId;
            if (msg != null) {
                flash.put("msg", msg);
            }

            throw new JapidResult(new PhotoEdit().render(photo, photoSets));
        } catch (Exception e) {
            e.printStackTrace();
            throw new JapidResult(new PhotoEdit().render(null, new ArrayList<PhotoSet>()));

        }
    }

    static JpegExifWriter jpegExifWrite = new SqlJpegExifWriter();

    @Post("/edit/done")
    public static void photoEditDone() {
        Map<String, String> contents = new HashMap<>(params.allSimple());
        long idPhoto = MapUtils.getIntValue(contents, JpegExifWriter.KeyIdPhoto);
        Integer idUser = currentUserId();
        try {
            contents.put("ip", request.remoteAddress);
            String username = session.get(SESSION_USER_NAME_KEY);
            contents.put("username", username);
            contents.put(JpegExifWriter.KeyIdUser, idUser.toString());
            jpegExifWrite.updateExif(contents);
            photoEdit(idPhoto, "success");
        } catch (Exception e) {
            e.printStackTrace();
            photoEdit(idPhoto, "fail");
        }

    }
}
