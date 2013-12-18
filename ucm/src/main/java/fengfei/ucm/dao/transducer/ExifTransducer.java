package fengfei.ucm.dao.transducer;

import java.sql.ResultSet;
import java.sql.SQLException;

import fengfei.forest.database.dbutils.Transducer;
import fengfei.ucm.profile.entity.ExifModel;

public class ExifTransducer implements Transducer<ExifModel> {

	@Override
	public ExifModel transform(ResultSet rs) throws SQLException {

		long idExif = rs.getLong("id_exif");
		int idUser = rs.getInt("id_user");
		String title = rs.getString("title");
		String description = rs.getString("description");
		String category = rs.getString("category");
		String make = rs.getString("make");
		String model = rs.getString("model");
		String aperture = rs.getString("aperture");
		String shutter = rs.getString("shutter");
		String iso = rs.getString("iso");
		String lens = rs.getString("lens");
		String focus = rs.getString("focus");
		String ev = rs.getString("ev");
		String tags = rs.getString("tags");
		String dateTimeOriginal = rs.getString("original_at");

		ExifModel exifModel = new ExifModel(idExif, idUser, title, description,
				category, make, model, aperture, shutter, iso, lens, focus, ev,
				dateTimeOriginal, tags);
		return exifModel;
	}
}
