package fengfei.ucm.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fengfei.forest.database.dbutils.ForestGrower;
import fengfei.forest.database.dbutils.StringTransducer;
import fengfei.forest.database.dbutils.impl.ForestRunner.InsertResultSet;
import fengfei.ucm.dao.transducer.ExifTransducer;
import fengfei.ucm.profile.entity.ExifModel;

public class ExifDao {

	public static List<InsertResultSet<Long>> save(ForestGrower grower,
			String suffix, List<ExifModel> models) throws SQLException {

		List<InsertResultSet<Long>> insertResultSets = new ArrayList<>();
		for (ExifModel exifModel : models) {
			InsertResultSet<Long> u = saveOne(grower, suffix, exifModel);
			insertResultSets.add(u);
		}

		return insertResultSets;
	}

	public static InsertResultSet<Long> saveOne(ForestGrower grower,
			String suffix, ExifModel m) throws SQLException {
		String sql = "SELECT  id_photo    FROM c_exif" + suffix
				+ " WHERE id_photo=?";
		String id = grower.selectOne(sql, new StringTransducer(), m.idExif);

		if (id == null || "".equals(id)) {
			String insert = "insert into c_exif"
					+ suffix
					+ "(id_photo,original_at,make,model,aperture,shutter,iso,lens,focus,tags,ev,title,description,category) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			InsertResultSet<Long> u = grower.insert(insert, m.idExif,
					m.dateTimeOriginal, m.make, m.model, m.aperture, m.shutter,
					m.iso, m.lens, m.focus, m.tags, m.ev, m.title,
					m.description, m.category);
			return u;
		} else {
			String update = "update c_exif"
					+ suffix
					+ " set original_at=?,make=?,model=?,aperture=?,shutter=?,iso=?,lens=?,focus=?,tags=?,ev=?,title=?,description=?,category=?  where id_photo=?";
			int u = grower.update(update, m.dateTimeOriginal, m.make, m.model,
					m.aperture, m.shutter, m.iso, m.lens, m.focus, m.tags,
					m.ev, m.title, m.description, m.category, m.idExif);
			return new InsertResultSet<Long>(u, null);
		}

	}

	public static ExifModel selectOne(ForestGrower grower, String suffix,
			long idExif) throws SQLException {
		String sql = "SELECT id_photo, original_at, make, model, aperture, shutter, iso, lens, focus, tags, ev, title, description, category FROM c_exif"
				+ suffix + " WHERE id_photo=?";
		ExifModel exifModel = grower.selectOne(sql, new ExifTransducer(), idExif);
		return exifModel;

	}

}
