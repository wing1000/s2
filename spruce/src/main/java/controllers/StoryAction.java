package controllers;

import cn.bran.play.JapidResult;
import edu.emory.mathcs.backport.java.util.Arrays;
import fengfei.fir.model.Done;
import fengfei.fir.model.Done.Status;
import fengfei.fir.model.UploadDone;
import fengfei.fir.service.JpegExifWriter;
import fengfei.fir.service.LorryStorage;
import fengfei.sprucy.Spruce;
import fengfei.ucm.entity.photo.Photo;
import japidviews.Application.photo.Add;
import japidviews.Application.photo.Blink;
import japidviews.Application.photo.Story;
import japidviews.Application.photo.Upload;
import play.Logger;
import play.modules.router.Any;
import play.modules.router.Post;
import play.mvc.With;

import java.io.File;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@With(Secure.class)
public class StoryAction extends Admin {

	public static LorryStorage lorryService = Spruce.getLorryStorage();

	@Any("/blink")
	public static void blink() {
		throw new JapidResult(new Blink().render());
	}

	@Any("/add")
	public static void add() {
		throw new JapidResult(new Add().render());
	}

	@Any("/story")
	public static void story() {
		throw new JapidResult(new Story().render());
	}

	@Post("/story/done")
	public static void storyDone(File[] files) {
		// System.out.println(params.allSimple());
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
		System.out.println(Arrays.asList(title));

		List<play.data.Upload> uploadFiles = (List<play.data.Upload>) request.args
				.get("__UPLOADS");

		if (uploadFiles != null && uploadFiles.size() > 0) {
			int i = 0;
			System.out.println(uploadFiles.size());
			for (play.data.Upload upload : uploadFiles) {
				System.out
						.printf(i
								+ "  title=%s ,size= %d , type=%s  fieldName=%s fileName=%s\n ",
								title[i], upload.getSize(),
								upload.getContentType(), upload.getFieldName(),
								upload.getFileName());

				if (upload.getSize() > 0) {
					File file = upload.asFile();
					System.out.println(file);
				}
				i++;
			}
		}

		throw new JapidResult(new Story().render());
	}

	// @Post("/story/done")
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

		if (uploadFiles != null && uploadFiles.size() > 0) {
			for (play.data.Upload upload : uploadFiles) {
				System.out.println("size: " + upload.getSize());
				if (upload.getSize() > 0) {
					File file = upload.asFile();

					System.out.println("files length " + file.length());

					// System.out.println(new HashMap<>(params.allSimple()));
					System.out.println(params.allSimple());
					UploadDone done;
					try {
						Integer idUser = currentUserId();
						Map<String, String> contents = new HashMap<>(
								params.allSimple());
						String username = session.get(SESSION_USER_NAME_KEY);
						contents.put("username", username);
						contents.put(JpegExifWriter.KeyIdUser,
								idUser.toString());
						done = lorryService.writeFile(contents, contents, file);
						renderJSON(done);
					} catch (Exception e) {
						Logger.error(e, "upload error.");
                        renderErrorJSON();
					}

				}
			}
		}

		throw new JapidResult(new Upload().render());
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
}
